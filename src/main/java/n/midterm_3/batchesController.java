package n.midterm_3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;

public class batchesController implements Initializable {

    // Injecting JavaFX components from the FXML file
    @FXML
    private TableView<Batch> tableView;
    @FXML
    private TableColumn<Batch, Integer> ExperationID;
    @FXML
    private TableColumn<Batch, Integer> ProductID;
    @FXML
    private TableColumn<Batch, LocalDate> ExpirationDate;
    @FXML
    private TableColumn<Batch, Integer> Quantity;

    // Creating an ObservableList for storing batches
    private ObservableList<Batch> batchObservableList = FXCollections.observableArrayList();

    // Creating an instance of HelloApplication
    HelloApplication h = new HelloApplication();

    // Action handler for removing expired products
    public void remove(ActionEvent event) throws Exception {

        // Creating a ConnectionManager instance
        ConnectionManager cm = new ConnectionManager();
        StringBuilder removedProductsInfo = new StringBuilder();

        try {
            cm.startTransaction();

            // Query to select expired products
            ResultSet rs = cm.executeQuery(
                    "SELECT ProductID, SUM(Quantity) as TotalQuantity FROM ProductExpiration WHERE ExpirationDate < CURDATE() GROUP BY ProductID"
            );

            while (rs.next()) {
                int productID = rs.getInt("ProductID");
                int totalExpiredQuantity = rs.getInt("TotalQuantity");

                // Updating product quantity in stock and removing expired entries
                cm.executeUpdate(
                        "UPDATE Product SET QuantityInStock = GREATEST(QuantityInStock - ?, 0) WHERE ProductID = ?",
                        totalExpiredQuantity,
                        productID
                );

                cm.executeUpdate(
                        "DELETE FROM ProductExpiration WHERE ProductID = ? AND ExpirationDate < CURDATE()",
                        productID
                );

                // Storing the information for the alert
                removedProductsInfo.append(String.format("Product ID: %d, Quantity Removed: %d\n", productID, totalExpiredQuantity));
            }

            cm.commitTransaction();

            // Showing the alert if any products were removed
            if (removedProductsInfo.length() > 0) {
                showAlertWithScrollableText("Removed Expired Products", "The following expired products have been removed:", removedProductsInfo.toString());
            } else {
                showAlert("No Action Needed", "No expired products were found.");
            }
        } catch (SQLException ex) {
            // Rolling back transaction if there's an error
            cm.rollbackTransaction();
            ex.printStackTrace();
        } finally {
            // Refreshing batches and closing the connection
            refreshBatches();
            cm.closeConnection();
        }
    }

    // Method to show an alert dialog with scrollable text
    private void showAlertWithScrollableText(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);

        TextArea textArea = new TextArea(content);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(textArea, Priority.ALWAYS);

        VBox vBox = new VBox(textArea);
        alert.getDialogPane().setExpandableContent(vBox);

        alert.showAndWait();
    }

    // Method to show a simple alert dialog
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Action handler to fetch batches expiring within one month
    public void get(ActionEvent event) {
        // Clearing current list of batches
        batchObservableList.clear();
        ConnectionManager connectionManager = new ConnectionManager();

        try {
            // Query to select products expiring in less than one month
            String query = "SELECT * FROM ProductExpiration WHERE ExpirationDate BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 1 MONTH)";
            ResultSet rs = connectionManager.executeQuery(query);

            while (rs.next()) {
                // Creating Batch objects and adding them to the list
                Batch batch = new Batch(
                        rs.getInt("ExpirationID"),
                        rs.getInt("ProductID"),
                        rs.getDate("ExpirationDate").toLocalDate(), // Make sure your Batch class expects a LocalDate for this field
                        rs.getInt("Quantity")
                );
                batchObservableList.add(batch);
            }

            // Setting the items in the table view
            tableView.setItems(batchObservableList);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions
        } finally {
            // Closing the connection
            connectionManager.closeConnection();
        }
    }

    // Action handler for the back button
    public void back(ActionEvent event) throws Exception {
        // Changing scene
        h.changeScene("list.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initializing the view
        loadBatches();
    }

    // Method to load batches into the table view
    public void loadBatches() {
        // Clearing the current list of batches
        batchObservableList.clear();
        ConnectionManager connectionManager = new ConnectionManager();

        try {
            // Query to select all batches
            String query = "SELECT * FROM ProductExpiration";
            ResultSet rs = connectionManager.executeQuery(query);

            while (rs.next()) {
                // Creating Batch objects and adding them to the list
                Batch batch = new Batch(
                        rs.getInt("ExpirationID"),
                        rs.getInt("ProductID"),
                        rs.getDate("ExpirationDate").toLocalDate(),
                        rs.getInt("Quantity")
                );
                batchObservableList.add(batch);
            }

            // Setting up cell value factories for the table columns
            ExperationID.setCellValueFactory(new PropertyValueFactory<>("expirationID"));
            ProductID.setCellValueFactory(new PropertyValueFactory<>("productID"));
            ExpirationDate.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
            Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            // Setting other columns as necessary

            // Setting the items in the table view
            tableView.setItems(batchObservableList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Closing the connection
            connectionManager.closeConnection();
        }
    }

    // Method to refresh batches in the table view
    public void refreshBatches() {
        // Reloading batches
        loadBatches();
    }

    // Action handler to remove any applied filter
    public void removeFilter(ActionEvent event) throws Exception {
        // Removing any applied filter
        loadBatches();
    }
}
