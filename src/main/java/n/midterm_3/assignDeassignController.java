package n.midterm_3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class assignDeassignController {

    // Injecting JavaFX components from the FXML file
    @FXML
    private TextField locationIDTextField;
    @FXML
    private TextField productIDTextField;

    // Creating an instance of HelloApplication
    HelloApplication h = new HelloApplication();

    // Creating an instance of ConnectionManager
    ConnectionManager connection = new ConnectionManager();

    // Action handler for the back button
    public void backButton(ActionEvent event) throws Exception {
        // Changing scene
        h.changeScene("productLocationMapping.fxml");
    }

    // Action handler for the allocate button
    public void allocateButton(ActionEvent event) throws SQLException {
        try {
            // Parsing input values to integers
            int locationID = Integer.parseInt(locationIDTextField.getText());
            int productID = Integer.parseInt(productIDTextField.getText());

            // Executing SQL INSERT query
            int rowsAffected = connection.executeUpdate(
                    "INSERT INTO ProductLocationMapping (LocationID, ProductID) VALUES (?, ?)",
                    locationID,
                    productID
            );

            // Checking if allocation was successful
            if (rowsAffected > 0) {
                // Showing success message
                showAlert(Alert.AlertType.INFORMATION, "Success", "Product has been allocated to the location.");
            } else {
                // Showing error message if allocation failed
                showAlert(Alert.AlertType.ERROR, "Error", "Product could not be allocated to the location.");
            }

        } catch (NumberFormatException e) {
            // Handling input format errors
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid integers for Location ID and Product ID.");
        }
    }

    // Action handler for the deallocate button
    public void deallocateButton(ActionEvent event) {
        try {
            // Parsing input values to integers
            int locationID = Integer.parseInt(locationIDTextField.getText());
            int productID = Integer.parseInt(productIDTextField.getText());

            // Executing SQL DELETE query
            int rowsAffected = connection.executeUpdate(
                    "DELETE FROM ProductLocationMapping WHERE LocationID = ? AND ProductID = ?",
                    locationID,
                    productID
            );

            // Checking if deallocation was successful
            if (rowsAffected > 0) {
                // Showing success message
                showAlert(Alert.AlertType.INFORMATION, "Success", "Product has been deallocated from the location.");
            } else {
                // Showing error message if deallocation failed
                showAlert(Alert.AlertType.ERROR, "Error", "Product could not be deallocated from the location.");
            }

        } catch (NumberFormatException e) {
            // Handling input format errors
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid integers for Location ID and Product ID.");
        }
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
