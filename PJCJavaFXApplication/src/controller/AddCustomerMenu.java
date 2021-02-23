package controller;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;
import model.Customer;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerMenu implements Initializable{
        Stage stage;
        Parent scene;
        Customer myCustomer = new Customer(1, "Bob");
        @FXML
        private TableView<Product> addProductTopTable;

        @FXML
        private TableColumn<Product, Integer> productIDCol;

        @FXML
        private TableColumn<Product, String> productNameCol;

        @FXML
        private TableColumn<Product, Integer> invLevelCol;

        @FXML
        private TableColumn<Product, Double> priceCostPerUnitCol;

        @FXML
        private TableView<Product> addProductBotTable;

        @FXML
        private TableColumn<Product, Integer> colProductID;

        @FXML
        private TableColumn<Product, String> colProductName;

        @FXML
        private TableColumn<Product, Integer> invCol;

        @FXML
        private TableColumn<Product, Double> colPrice;

        @FXML
        private TableView<Customer> customerViewTable;

        @FXML
        private TableColumn<Customer, Integer> cusIDCol;

        @FXML
        private TableColumn<Customer, String> cusNameCol;

        @FXML
        private TextField cusMenuProductSearchTxt;

        @FXML
        private TextField addCustomerIDTxt;

        @FXML
        private TextField addCustomerNameTxt;

        public void addSelectedProduct() {
                Product myProduct = addProductTopTable.getSelectionModel().getSelectedItem();

                        if (!addProductBotTable.getItems().contains(myProduct)){
                                myCustomer.addAssociatedProduct(myProduct);
                                addProductBotTable.setItems(myCustomer.getAssociatedProduct());
                         }
                        else {
                                Alert error = new Alert(Alert.AlertType.WARNING);
                                error.setTitle("Warning Dialog");
                                error.setContentText("Duplicate products are not allowed.");
                                error.showAndWait();
                        }
        }
        @FXML
        void onActionAddProductToCustomer(ActionEvent event) {
        addSelectedProduct();
        }

        @FXML
        void onActionCancel(ActionEvent event) throws IOException {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
        }

        @FXML
        void onActionRemoveAssociatedProduct(ActionEvent event) {
                Product selectedProduct = addProductBotTable.getSelectionModel().getSelectedItem();
                addProductBotTable.getItems().remove(selectedProduct);
        }

        @FXML
        void onActionSaveCustomer(ActionEvent event) throws IOException {
                int id = Integer.parseInt(addCustomerIDTxt.getText());
                String name = addCustomerNameTxt.getText();
                Customer newCustomer = new Customer(id, name);
                int index = 0;
                while(index < addProductBotTable.getItems().size()){
                        Product productToAdd = addProductBotTable.getItems().get(index);
                        newCustomer.addAssociatedProduct(productToAdd);
                        index++;
                }
                Inventory.addCustomer(newCustomer);
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
        }

        @FXML
        void onActionSearchBox(ActionEvent event) {
                String nameOrId = cusMenuProductSearchTxt.getText();
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
                addProductTopTable.setItems(products);
        }
        @FXML
        void onActionModCus(ActionEvent event) throws IOException {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/ModCustomerMenu.fxml"));
                Parent modCustomerScene = loader.load();
                Scene scene = new Scene(modCustomerScene);
                ModCustomerController pass = loader.getController();
                pass.passCustomer(customerViewTable.getSelectionModel().getSelectedItem());
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
        }
        @FXML
        void onActionRemoveCus(ActionEvent event) {
        Inventory.deleteCustomer(customerViewTable.getSelectionModel().getSelectedItem());
        }

@Override
        public void initialize(URL url, ResourceBundle rb) {

                customerViewTable.setItems(Inventory.getAllCustomers());
                cusIDCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
                cusNameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));

                addCustomerIDTxt.setText(String.valueOf(Inventory.getAllCustomers().size()+1));
                addProductTopTable.setItems(Inventory.getAllProducts());
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