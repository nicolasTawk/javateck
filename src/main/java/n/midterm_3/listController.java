package n.midterm_3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class for the main product list view.
 */
public class listController implements Initializable {

    @FXML
    private TableView<product> tableViewProduct;
    @FXML
    private TableColumn<product, Integer> ProductID;
    @FXML
    private TableColumn<product, String> Name;
    @FXML
    private TableColumn<product, String> Description;
    @FXML
    private TableColumn<product, Double> Price;
    @FXML
    private TableColumn<product, Integer> QuantityInStock;
    @FXML
    private TextField SearchBar;
    @FXML
    private Button add;

    // Observable list to hold products for searching
    ObservableList<product> productSearchModel0bservableList = FXCollections.observableArrayList();

    // Instance of the main application
    HelloApplication h = new HelloApplication();

    /**
     * Method to switch to the product locations view.
     * @param event Action event
     * @throws Exception If switching scene fails
     */
    public void productLocations(ActionEvent event) throws Exception {
        h.changeScene("productLocations.fxml");
    }

    /**
     * Method to log out from the application.
     * @param event Action event
     * @throws Exception If switching scene fails
     */
    public void logOut(ActionEvent event) throws Exception {
        h.changeScene("hello-view.fxml");
    }

    /**
     * Method to switch to the add product view.
     * @param event Action event
     * @throws Exception If switching scene fails
     */
    public void addButton(ActionEvent event) throws Exception {
        h.changeScene("addButton.fxml");
    }

    /**
     * Method to switch to the buy/sell product view.
     * @param event Action event
     * @throws Exception If switching scene fails
     */
    public void buysellButton(ActionEvent event) throws Exception {
        h.changeScene("buyButton.fxml");
    }

    /**
     * Method to switch to the batches view.
     * @param event Action event
     * @throws Exception If switching scene fails
     */
    public void batchesButton(ActionEvent event) throws Exception {
        h.changeScene("batches.fxml");
    }

    /**
     * Initializes the controller class.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Establishing database connection
        ConnectionManager connection = new ConnectionManager();
        String query = "SELECT ProductID, Name, Description, Price, QuantityInStock FROM Product";

        try {
            // Executing query to fetch product data
            ResultSet queryOutput = connection.executeQuery(query);

            // Populating product list with query results
            while (queryOutput.next()) {
                Integer queryProductID = queryOutput.getInt("ProductID");
                String queryName = queryOutput.getString("Name");
                String queryDescription = queryOutput.getString("Description");
                Double queryPrice = queryOutput.getDouble("Price");
                Integer queryQuantityInStock = queryOutput.getInt("QuantityInStock");

                productSearchModel0bservableList.add(new product(queryProductID, queryName, queryDescription, queryPrice, queryQuantityInStock));
            }

            // Setting up table columns
            ProductID.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
            Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
            Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
            Price.setCellValueFactory(new PropertyValueFactory<>("Price"));
            QuantityInStock.setCellValueFactory(new PropertyValueFactory<>("QuantityInStock"));

            // Binding filtered data to the table view
            tableViewProduct.setItems(productSearchModel0bservableList);

            // Setting up filtering for search functionality
            FilteredList<product> filteredData = new FilteredList<>(productSearchModel0bservableList, b -> true);

            // Listening for changes in the search bar and updating the filtered data accordingly
            SearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(product -> {
                    if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                        return true; // No filter if search text is empty
                    }

                    String searchKeyword = newValue.toLowerCase();
                    // Filtering based on name or description containing the search text
                    return product.getName().toLowerCase().contains(searchKeyword) || product.getDescription().toLowerCase().contains(searchKeyword);
                });
            });

            // Wrapping the filtered data in a sorted list
            SortedList<product> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableViewProduct.comparatorProperty());

            // Setting the sorted data to the table view
            tableViewProduct.setItems(sortedData);

        } catch (SQLException e) {
            // Logging and handling SQL exceptions
            Logger.getLogger(listController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }
}
