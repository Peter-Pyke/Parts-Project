package controller;

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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 FXML MainMenu controller. This is the main scene and will be loaded first.
 @author Peter Chouinard.
 */
public class MainMenuController implements Initializable
{

    Stage stage;
    Parent scene;
    @FXML
    private TextField searchBoxPart;

    @FXML
    private TextField productSearchBox;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> productInvCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;


    /**
     Action even method. This method changes scene to AddPartMenu.
     @param event Add button is clicked.
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     Action event method. This method will call the delete method from inventory to delete the selected part.
     @param event delete button clicked.
     */
    @FXML
    void onActionDeletePart(ActionEvent event)
    {
        if(partsTable.getSelectionModel().isEmpty()){
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Warning Dialog");
            error.setContentText("Please select a Part.");
            error.showAndWait();
        }
        else {
            Alert error = new Alert(Alert.AlertType.CONFIRMATION);
            error.setTitle("Confirmation Dialog");
            error.setContentText(partsTable.getSelectionModel().getSelectedItem().getName() + " has been deleted.");
            error.showAndWait();
            Inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());
        }
    }

    /**
     Action event method. This method changes scene to ModPartMenu.
     @param event modify part button is clicked.
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModPartMenu.fxml"));
            Parent modPartScene = loader.load();
            Scene scene4 = new Scene(modPartScene);
            ModPartController pass = loader.getController();
            pass.passPart(partsTable.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene4);
            stage.show();
        }
        catch(NullPointerException e){
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Warning Dialog");
            error.setContentText("Please select a Part.");
            error.showAndWait();
        }
    }
    /**
     Action event method. This method changes the scene to AddProduct.
     @param event Add product button is clicked.
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     Action event method. This deletes a selected product.
     @param event Delete button is clicked.
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event)
    {
        if(productTable.getSelectionModel().isEmpty()){
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Warning Dialog");
            error.setContentText("Please select a Product.");
            error.showAndWait();
        }
        if(!productTable.getSelectionModel().getSelectedItem().getAssociatedParts().isEmpty()){
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Warning Dialog");
            error.setContentText("Product has associated parts.");
            error.showAndWait();
        }
        else {
            Alert error = new Alert(Alert.AlertType.CONFIRMATION);
            error.setTitle("Confirmation Dialog");
            error.setContentText(productTable.getSelectionModel().getSelectedItem().getName() + " has been deleted.");
            error.showAndWait();
            Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
        }
    }
    /**
     Action event method. This method changes the scene to ModProductMenu.
     @param event Modify Product button is clicked.
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModProductMenu.fxml"));
            Parent modProductScene = loader.load();
            Scene scene = new Scene(modProductScene);
            ModProductController pass = loader.getController();
            pass.passProduct(productTable.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        catch(NullPointerException e){
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Warning Dialog");
            error.setContentText("Please select a Product.");
            error.showAndWait();
        }

    }
    /**
     Action event method. This method closes the application when the Exit button is clicked.
     @param event Exit button is clicked.
     */
    @FXML
    void onActionExit(ActionEvent event)
    {
               System.exit(0);
    }
    /**
     Action event method. This method will be called when user types data into the parts search text field.
     @param event Search Parts box is used.
     */
    @FXML
    void onActionFilterParts(ActionEvent event)
    {
        String nameOrId = searchBoxPart.getText();
        ObservableList<Part> parts = Inventory.lookupPart(nameOrId);
        //This checks to see if the person entered a part id instead of the name and returns the part whose id matches.
        if (parts.size() == 0)
        {
            try {
                    int partId = Integer.parseInt(nameOrId);
                Part np = Inventory.lookupPart(partId);

                    if (np != null)
                        parts.add(np);

                } catch (NumberFormatException e)
                    {
                        //ignore
                    }
            }
        partsTable.setItems(parts);
    }
    /**
     Action event method. This method provides the search functionality for the Product table in the main menu.
     @param event Search Product box is used.
     */
    @FXML
    void onActionProductSearchBox(ActionEvent event) {
        String nameOrId = productSearchBox.getText();
        ObservableList<Product> products = Inventory.lookupProduct(nameOrId);
        //This checks to see if the person entered a part id instead of the name and returns the part whose id matches.
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
        productTable.setItems(products);
    }
    /**
     Initializes the Main Menu controller class.
     @param rb
     @param url
     */
    @Override
    public void initialize (URL url, ResourceBundle rb) {

        //Sets up the info inside of the part table in the Main Menu.
        partsTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        //Sets up the info inside of the product table in the Main Menu.
        productTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
