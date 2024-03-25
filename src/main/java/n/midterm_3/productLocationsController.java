package n.midterm_3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller class for the product locations view.
 */
public class productLocationsController implements Initializable {

    @FXML
    private TableView<ProductLocation> locationsTableView;
    @FXML
    private TableColumn<ProductLocation, Integer> LocationID;
    @FXML
    private TableColumn<ProductLocation, String> Aisle;
    @FXML
    private TableColumn<ProductLocation, String> Shelf;
    @FXML
    private TableColumn<ProductLocation, String> Bin;
    @FXML
    private TextField searchTextFeald;

    // Instance of the main application
    HelloApplication h = new HelloApplication();

    // Observable list to hold product locations
    ObservableList<ProductLocation> productLocationObservableList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Establishing database connection
        ConnectionManager connection = new ConnectionManager();
        String query = "SELECT * FROM ProductLocation";

        try {
            // Executing query to fetch product location data
            ResultSet queryOutput = connection.executeQuery(query);

            // Populating product location list with query results
            while (queryOutput.next()) {
                int locationID = queryOutput.getInt("LocationID");
                String aisle = queryOutput.getString("Aisle");
                String shelf = queryOutput.getString("Shelf");
                String bin = queryOutput.getString("Bin");

                productLocationObservableList.add(new ProductLocation(locationID, aisle, shelf, bin));
            }

            // Setting up table columns
            LocationID.setCellValueFactory(new PropertyValueFactory<>("locationID"));
            Aisle.setCellValueFactory(new PropertyValueFactory<>("aisle"));
            Shelf.setCellValueFactory(new PropertyValueFactory<>("shelf"));
            Bin.setCellValueFactory(new PropertyValueFactory<>("bin"));

            // Implementing search feature
            FilteredList<ProductLocation> filteredData = new FilteredList<>(productLocationObservableList, p -> true);

            searchTextFeald.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(productLocation -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (productLocation.getAisle().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches aisle
                    } else if (productLocation.getShelf().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches shelf
                    } else {
                        return productLocation.getBin().toLowerCase().contains(lowerCaseFilter); // Filter matches bin
                    }
                });
            });

            SortedList<ProductLocation> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(locationsTableView.comparatorProperty());

            // Setting the sorted data to the table view
            locationsTableView.setItems(sortedData);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, show an alert, or log the error
        }
    }

    /**
     * Method to navigate to the add location view.
     *
     * @param event Action event
     * @throws Exception If switching scene fails
     */
    public void addButton(ActionEvent event) throws Exception {
        h.changeScene("addLocationButton.fxml");
    }

    /**
     * Method to navigate back to the product list view.
     *
     * @param event Action event
     * @throws Exception If switching scene fails
     */
    public void back(ActionEvent event) throws Exception {
        h.changeScene("list.fxml");
    }

    /**
     * Method to navigate to the product location mapping view.
     *
     * @param event Action event
     * @throws Exception If switching scene fails
     */
    public void productLocationMapping(ActionEvent event) throws Exception {
        h.changeScene("productLocationMapping.fxml");
    }
}
