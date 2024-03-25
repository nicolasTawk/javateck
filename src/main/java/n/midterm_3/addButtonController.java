package n.midterm_3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller class for handling addition of new items.
 */
public class addButtonController {

    // Injecting JavaFX components from the FXML file
    @FXML
    private TextField nameTextLable;
    @FXML
    private TextField descriptionTextLable;
    @FXML
    private TextField priceTextLable;
    @FXML
    private Button add;
    @FXML
    private Button back;

    // Action handler for the add button
    public void addButton(ActionEvent event) {
        // Creating an instance of HelloApplication
        HelloApplication h = new HelloApplication();

        try {
            // Retrieving input values from text fields
            String name = nameTextLable.getText();
            String description = descriptionTextLable.getText();
            double price = Double.parseDouble(priceTextLable.getText());

            // Establishing a database connection
            ConnectionManager connection = new ConnectionManager();

            // Executing SQL INSERT query
            int rowsAffected = connection.executeUpdate("INSERT INTO Product (Name, Description, Price) VALUES (?,?,?)", name, description, price);

            // Checking if insertion was successful
            if (rowsAffected > 0) {
                // Showing success message and changing scene
                showAlert(Alert.AlertType.INFORMATION, "Success", "The item has been added successfully.");
                h.changeScene("list.fxml");
            } else {
                // Showing error message if insertion failed
                showAlert(Alert.AlertType.ERROR, "Error", "No item was added. Please try again.");
            }
        } catch (NumberFormatException e) {
            // Handling input format errors
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter a valid price.");
        } catch (Exception e) {
            // Handling other database errors
            showAlert(Alert.AlertType.ERROR, "Database Error", "There was an issue adding the item: " + e.getMessage());
        }
    }

    // Action handler for the back button
    public void backButton(ActionEvent event) throws Exception {
        // Creating an instance of HelloApplication and changing scene
        HelloApplication h = new HelloApplication();
        h.changeScene("list.fxml");
    }

    // Method to show an alert dialog
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
