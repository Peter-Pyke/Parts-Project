package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import model.Inventory;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModCustomerController implements Initializable {

    Stage stage;
    Parent scene;
    Customer myCustomer = new Customer(1000, "Toyota");

    @FXML
    private TableView<Product> modProductTopTable;

    @FXML
    private TableColumn<Product, Integer> productIDCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> invLevelCol;

    @FXML
    private TableColumn<Product, Double> priceCostPerUnitCol;

    @FXML
    private TableView<Product> modProductBotTable;

    @FXML
    private TableColumn<Product, Integer> colProductID;

    @FXML
    private TableColumn<Product, String> colProductName;

    @FXML
    private TableColumn<Product, Integer> invCol;

    @FXML
    private TableColumn<Product, Double> colPrice;

    @FXML
    private TextField cusModMenuProductSearchTxt;

    @FXML
    private TextField modCustomerIDTxt;

    @FXML
    private TextField modCustomerNameTxt;

    // Local static customer object will be use in our passCustomer method.
    private static Customer theCustomer = null;
    private static ObservableList<Product> myProductList = null;
    /**
     * This method will be use to pass the selected Customer information from the AddCustomerMenu to the ModCustomerMenu.
     * @param selectedCustomer Customer selected in Current Customer Table.
     */
    public void passCustomer(Customer selectedCustomer){
        theCustomer = selectedCustomer;
        myProductList = FXCollections.observableArrayList(theCustomer.getAssociatedProduct());
        modCustomerIDTxt.setText(Integer.toString(theCustomer.getId()));
        modCustomerNameTxt.setText(theCustomer.getName());
        modProductBotTable.setItems(myProductList);
        int index = 0;
        while (index < modProductBotTable.getItems().size()){
            myCustomer.addAssociatedProduct(modProductBotTable.getItems().get(index));
            index++;
        }
    }
    @FXML
    void onActionModAddProductToCustomer(ActionEvent event) {
        Product myProduct = modProductTopTable.getSelectionModel().getSelectedItem();

        if (!modProductBotTable.getItems().contains(myProduct)){
            myCustomer.addAssociatedProduct(myProduct);
            modProductBotTable.setItems(myCustomer.getAssociatedProduct());
        }
        else {
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Warning Dialog");
            error.setContentText("Duplicate products are not allowed.");
            error.showAndWait();
        }
    }

    @FXML
    void onActionModCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionModRemoveAssociatedProduct(ActionEvent event) {
        Product selectedProduct = modProductBotTable.getSelectionModel().getSelectedItem();
        modProductBotTable.getItems().remove(selectedProduct);
    }

    @FXML
    void onActionModSaveCustomer(ActionEvent event) throws IOException{
        int index = 0;
        int id = Integer.parseInt(modCustomerIDTxt.getText());
        String name = modCustomerNameTxt.getText();
        Customer createdCustomer = new Customer(id, name);
        while (index < modProductBotTable.getItems().size()) {
            Product productToAdd = modProductBotTable.getItems().get(index);
            createdCustomer.addAssociatedProduct(productToAdd);
            index++;
        }
        Inventory.updateCustomer(Inventory.getAllCustomers().indexOf(theCustomer), createdCustomer);
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionSearchBox(ActionEvent event) {
        String nameOrId = cusModMenuProductSearchTxt.getText();
        ObservableList<Product> products = Inventory.lookupProduct(nameOrId);
        //This checks to see if the person entered a product id instead of the name and returns the product whose id matches.
        if (products.size() == 0)
        {
            try {
                int productId = Integer.parseInt(nameOrId);
                Product np = Inventory.lookupProduct(productId);

                if (np != null)
                    products.add(np);

            } catch (NumberFormatException e)
            {
                //ignore
            }
        }
        modProductTopTable.setItems(products);
    }

    @Override
    public void initialize (URL url, ResourceBundle rb){

        modProductTopTable.setItems(Inventory.getAllProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCostPerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        colProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
