package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Product;

import java.io.IOException;

public class AddCustomerMenu {
        Stage stage;
        Parent scene;
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
        private TextField cusMenuProductSearchTxt;

        @FXML
        private TextField addCustomerIDTxt;

        @FXML
        private TextField addCustomerNameTxt;

        @FXML
        void onActionAddProductToCustomer(ActionEvent event) {

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

        }

        @FXML
        void onActionSaveCustomer(ActionEvent event) {

        }

        @FXML
        void onActionSearchBox(ActionEvent event) {

        }

    }

