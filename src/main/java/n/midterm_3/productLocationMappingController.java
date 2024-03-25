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
 * Controller class for the product location mapping view.
 */
public class productLocationMappingController implements Initializable {

    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<productLocationMapping> mappingTableView;
    @FXML
    private TableColumn<productLocationMapping, Integer> MappingID;
    @FXML
    private TableColumn<productLocationMapping, Integer> ProductID;
    @FXML
    private TableColumn<productLocationMapping, String> nameID;
    @FXML
    private TableColumn<productLocationMapping, Integer> LocationID;

    // Observable list to hold product location mappings
    private ObservableList<productLocationMapping> mappingList = FXCollections.observableArrayList();

    // Instance of the main application
    HelloApplication h = new HelloApplication();

    /**
     * Initializes the controller class.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Establishing database connection
        ConnectionManager connection = new ConnectionManager();
        String query = "SELECT plm.MappingID, plm.ProductID, p.Name as ProductName, plm.LocationID " +
                "FROM ProductLocationMapping plm " +
                "JOIN Product p ON plm.ProductID = p.ProductID";

        try {
            // Executing query to fetch product location mapping data
            ResultSet resultSet = connection.executeQuery(query);

            // Populating mapping list with query results
            while (resultSet.next()) {
                mappingList.add(new productLocationMapping(
                        resultSet.getInt("MappingID"),
                        resultSet.getInt("ProductID"),
                        resultSet.getString("ProductName"),
                        resultSet.getInt("LocationID")
                ));
            }

            // Setting up table columns
            MappingID.setCellValueFactory(new PropertyValueFactory<>("mappingID"));
            ProductID.setCellValueFactory(new PropertyValueFactory<>("productID"));
            nameID.setCellValueFactory(new PropertyValueFactory<>("productName"));
            LocationID.setCellValueFactory(new PropertyValueFactory<>("locationID"));

            // Implementing search feature
            FilteredList<productLocationMapping> filteredData = new FilteredList<>(mappingList, b -> true);

            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(mapping -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf(mapping.getMappingID()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches mapping ID
                } else if (String.valueOf(mapping.getProductID()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches product ID
                } else if (mapping.getProductName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches product name
                } else {
                    return String.valueOf(mapping.getLocationID()).toLowerCase().contains(lowerCaseFilter); // Filter matches location ID
                }
            }));

            SortedList<productLocationMapping> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(mappingTableView.comparatorProperty());

            // Setting the sorted data to the table view
            mappingTableView.setItems(sortedData);

        } catch (SQLException e) {
            e.printStackTrace(); // Logging SQL exceptions
        }
    }

    /**
     * Method to navigate back to the product locations view.
     * @param event Action event
     * @throws Exception If switching scene fails
     */
    public void backButton(ActionEvent event) throws Exception {
        h.changeScene("productLocations.fxml");
    }

    /**
     * Method to navigate to the assign/deassign product view.
     * @param event Action event
     * @throws Exception If switching scene fails
     */
    public void AssigningButton(ActionEvent event) throws Exception {
        h.changeScene("assignDeassign.fxml");
    }
}
