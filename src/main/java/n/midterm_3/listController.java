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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    ObservableList<product> productSearchModelObservableList = FXCollections.observableArrayList();

    // Instance of the main application
    HelloApplication h = new HelloApplication();

    public void productLocations(ActionEvent event) throws Exception {
        h.changeScene("productLocations.fxml");
    }

    public void logOut(ActionEvent event) throws Exception {
        h.changeScene("hello-view.fxml");
    }

    public void addButton(ActionEvent event) throws Exception {
        h.changeScene("addButton.fxml");
    }

    public void buysellButton(ActionEvent event) throws Exception {
        h.changeScene("buyButton.fxml");
    }

    public void batchesButton(ActionEvent event) throws Exception {
        h.changeScene("batches.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Fetch product data from REST API
            URL apiUrl = new URL("http://localhost:8080/api/products");
            HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            conn.disconnect();

            String jsonResponse = sb.toString();

            // Parse JSON response to list of products
            ObjectMapper objectMapper = new ObjectMapper();
            List<product> products = objectMapper.readValue(jsonResponse, new TypeReference<List<product>>() {});

            // Add products to observable list
            productSearchModelObservableList.addAll(products);

            // Setting up table columns
            ProductID.setCellValueFactory(new PropertyValueFactory<>("productId"));
            Name.setCellValueFactory(new PropertyValueFactory<>("name"));
            Description.setCellValueFactory(new PropertyValueFactory<>("description"));
            Price.setCellValueFactory(new PropertyValueFactory<>("price"));
            QuantityInStock.setCellValueFactory(new PropertyValueFactory<>("quantityInStock"));

            // Binding filtered data to the table view
            tableViewProduct.setItems(productSearchModelObservableList);

            // Setting up filtering for search functionality
            FilteredList<product> filteredData = new FilteredList<>(productSearchModelObservableList, b -> true);

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

        } catch (IOException e) {
            // Logging and handling IO exceptions
            Logger.getLogger(listController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }
}
