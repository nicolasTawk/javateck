package n.midterm_3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class buyButtonController {

    @FXML
    private TextField ProductIDText;
    @FXML
    private TextField quantityText;
    @FXML
    private TextField experationDateText;

    HelloApplication h = new HelloApplication();


    // Inside your buyButtonController.java (consider renaming to ProductController)

    public void sell(ActionEvent event) throws Exception {
        int productID = Integer.parseInt(ProductIDText.getText());
        int quantityToSell = Integer.parseInt(quantityText.getText());
        ConnectionManager connection = new ConnectionManager();

        try {
            connection.startTransaction(); // Begin transaction

            while (quantityToSell > 0) {
                PreparedStatement ps = connection.getConnection().prepareStatement(
                        "SELECT ExpirationID, Quantity FROM ProductExpiration WHERE ProductID = ? AND Quantity > 0 ORDER BY ExpirationDate ASC LIMIT 1");
                ps.setInt(1, productID);
                ResultSet resultSet = ps.executeQuery();

                if (!resultSet.next()) {
                    connection.rollbackTransaction();
                    showAlert("Insufficient stock", "There is not enough stock to sell the desired quantity.");
                    return;
                }

                int expirationID = resultSet.getInt("ExpirationID");
                int batchQuantity = resultSet.getInt("Quantity");
                int quantityToDeduct = Math.min(batchQuantity, quantityToSell);

                if (batchQuantity == quantityToDeduct) {
                    // If the batch will be depleted, delete it
                    PreparedStatement deleteBatch = connection.getConnection().prepareStatement(
                            "DELETE FROM ProductExpiration WHERE ExpirationID = ?");
                    deleteBatch.setInt(1, expirationID);
                    deleteBatch.executeUpdate();
                } else {
                    // Otherwise, update the batch quantity
                    updateBatchQuantity(connection, expirationID, batchQuantity - quantityToDeduct);
                }

                // Update the product stock in Product
                PreparedStatement updateProductStock = connection.getConnection().prepareStatement(
                        "UPDATE Product SET QuantityInStock = GREATEST(QuantityInStock - ?, 0) WHERE ProductID = ?");
                updateProductStock.setInt(1, quantityToDeduct);
                updateProductStock.setInt(2, productID);
                updateProductStock.executeUpdate();

                quantityToSell -= quantityToDeduct;
            }

            // Optionally, check if product has no more batches left and handle accordingly
            // This could include removing the product from any lists displayed in the UI

            connection.commitTransaction(); // Commit all changes if successful
            showAlert("Success", "Products sold successfully.");
        } catch (SQLException ex) {
            connection.rollbackTransaction(); // Rollback on error
            showAlert("Error", "An error occurred during the sale: " + ex.getMessage());
        } finally {
            connection.closeConnection(); // Close connection in a finally block
            // Refresh the table view to reflect changes
        }
    }


    private void updateBatchQuantity(ConnectionManager connection, int expirationID, int newQuantity) throws SQLException {
        PreparedStatement updateStatement = connection.getConnection().prepareStatement(
                "UPDATE ProductExpiration SET Quantity = ? WHERE ExpirationID = ?");
        updateStatement.setInt(1, newQuantity);
        updateStatement.setInt(2, expirationID);
        updateStatement.executeUpdate();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void buy(ActionEvent event) throws Exception {
        int productID = Integer.parseInt(ProductIDText.getText());
        int quantity = Integer.parseInt(quantityText.getText());
        String expirationDateString = experationDateText.getText();
        ConnectionManager connection = new ConnectionManager();

        try {
            connection.startTransaction(); // Begin transaction

            LocalDate expirationDate = parseDate(expirationDateString);

            // Insert into ProductExpiration table
            PreparedStatement insertStmt = connection.getConnection().prepareStatement(
                    "INSERT INTO ProductExpiration (ProductID, ExpirationDate, Quantity) VALUES (?, ?, ?)");
            insertStmt.setInt(1, productID);
            insertStmt.setDate(2, java.sql.Date.valueOf(expirationDate));
            insertStmt.setInt(3, quantity);
            insertStmt.executeUpdate();

            // Update QuantityInStock in Product table
            PreparedStatement updateStmt = connection.getConnection().prepareStatement(
                    "UPDATE Product SET QuantityInStock = QuantityInStock + ? WHERE ProductID = ?");
            updateStmt.setInt(1, quantity);
            updateStmt.setInt(2, productID);
            updateStmt.executeUpdate();

            connection.commitTransaction(); // Commit transaction
            showAlert("Success", "Product bought successfully.");
        } catch (DateTimeParseException e) {
            showAlert("Date Error", "The expiration date is in an incorrect format. Please use YYYY-MM-DD.");
        } catch (SQLException e) {
            connection.rollbackTransaction(); // Rollback on error
            showAlert("SQL Error", "An error occurred during the purchase: " + e.getMessage());
        } finally {
            connection.closeConnection(); // Always close the connection
        }
    }

    private LocalDate parseDate(String dateString) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return LocalDate.parse(dateString, formatter);
    }

    public void back(ActionEvent event) throws Exception{

        h.changeScene("list.fxml");

    }
}
