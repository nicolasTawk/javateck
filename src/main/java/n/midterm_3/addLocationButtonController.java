package n.midterm_3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * Controller class for handling addition of new locations.
 */
public class addLocationButtonController {

    // Injecting JavaFX components from the FXML file
    @FXML
    private TextField AisleTextField;
    @FXML
    private TextField ShelfTextField;
    @FXML
    private TextField BinTextField;

    // Creating an instance of HelloApplication
    HelloApplication h = new HelloApplication();

    // Action handler for the add button
    public void addButton(ActionEvent event) throws Exception{

        try {
            // Retrieving input values from text fields
            String aisle = AisleTextField.getText();
            String shelf = ShelfTextField.getText();
            String bin = BinTextField.getText().toUpperCase();

            // Establishing a database connection
            ConnectionManager connection = new ConnectionManager();

            // Executing SQL INSERT query
            int rowsAffected = connection.executeUpdate(
                    "INSERT INTO ProductLocation (Aisle, Shelf, Bin) VALUES (?, ?, ?)",
                    aisle,
                    shelf,
                    bin
            );

            // Checking if insertion was successful
            if (rowsAffected > 0) {
                // Showing success message
                showAlert(Alert.AlertType.INFORMATION, "Success", "The location has been added successfully.");

            } else {
                // Showing error message if insertion failed
                showAlert(Alert.AlertType.ERROR, "Error", "No location was added. Please try again.");
            }
        } catch (Exception e) {
            // Handling database errors
            showAlert(Alert.AlertType.ERROR, "Database Error", "There was an issue adding the location: " + e.getMessage());
        }
    }

    // Action handler for the back button
    public void backButton(ActionEvent event) throws Exception {
        // Changing scene
        h.changeScene("productLocations.fxml");
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
