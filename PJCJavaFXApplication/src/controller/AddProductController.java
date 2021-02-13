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
 FXML add product controller class. This class is the controller for our AddProductMenu FXML.
 @author Peter
 */
public class AddProductController implements Initializable{
    Stage stage;
    Parent scene;
    Product myProduct = new Product(0, "box", 10.22, 10, 5, 20);

    @FXML
    private TableView<Part> addProductTopTable;

    @FXML
    private TableColumn<Part, Integer> partIDCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> invLevelCol;

    @FXML
    private TableColumn<Part, Double> priceCostPerUnitCol;

    @FXML
    private TableView<Part> addProductBotTable;

    @FXML
    private TableColumn<Part, Integer> colPartID;

    @FXML
    private TableColumn<Part, String> colPartName;

    @FXML
    private TableColumn<Part, Integer> invCol;

    @FXML
    private TableColumn<Part, Double> colPrice;

    @FXML
    private TextField addProductSearchTxt;

    @FXML
    private TextField addProductIDTxt;

    @FXML
    private TextField addProductNameTxt;

    @FXML
    private TextField addProductInvTxt;

    @FXML
    private TextField addProductPriceTxt;

    @FXML
    private TextField addProductMinTxt;

    @FXML
    private TextField addProductMaxTxt;
    /**
     Action event method. This method changes the scene to Main Menu when the cancel button is clicked.
     @param event cancel button is clicked.
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     This method adds the selected part from the top table to the bottom table.
     */
    public void addSelectedPart() {

            Part thePart = addProductTopTable.getSelectionModel().getSelectedItem();
            if(thePart == null)
            {
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Warning Dialog");
            error.setContentText("Please select a part.");
            error.showAndWait();
            }
            else
            {
                myProduct.addAssociatedParts(thePart);
                addProductBotTable.setItems(myProduct.getAssociatedParts());
            }
    }
    /**
     This method deletes the selected part form the bottom table.
     */
    public void deleteSelectedPart (){
        if(addProductBotTable.getSelectionModel().isEmpty()){
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Warning Dialog");
            error.setContentText("Please select a Part.");
            error.showAndWait();
        }
        else {
            Alert error = new Alert(Alert.AlertType.CONFIRMATION);
            error.setTitle("Confirmation Dialog");
            error.setContentText(addProductBotTable.getSelectionModel().getSelectedItem().getName() + " has been removed.");
            error.showAndWait();
            Part partToRemove = addProductBotTable.getSelectionModel().getSelectedItem();
            addProductBotTable.getItems().remove(partToRemove);
        }
    }
    /**
     Action event method. This method uses the addSelectedPart method when the add button is clicked.
     @param event Add part button is clicked.
     */
    @FXML
    void onActionAddPartToProduct(ActionEvent event) {

        addSelectedPart();

    }
    /**
     Action even method. This method uses the delete method to remove a part from the bottom table.
     @param event Remove Associated Part button is clicked.
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {

        deleteSelectedPart();

    }
    /**
     Action event method. This method uses the addProduct method to add our product to inventory when the save button is clicked.
     @param event Save button is clicked.
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        try {
            int index = 0;
            int id = Integer.parseInt(addProductIDTxt.getText());
            String name = addProductNameTxt.getText();
            int stock = Integer.parseInt(addProductInvTxt.getText());
            double price = Double.parseDouble(addProductPriceTxt.getText());
            int min = Integer.parseInt(addProductMinTxt.getText());
            int max = Integer.parseInt(addProductMaxTxt.getText());
            Product createdProduct = new Product(id, name, price, stock, min, max);
            while (index < addProductBotTable.getItems().size()) {
                Part partToAdd = addProductBotTable.getItems().get(index);
                createdProduct.addAssociatedParts(partToAdd);
                index++;
            }
            if((min >= max)) {
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Min must be less than Max.");
                error.showAndWait();
            }
            else if(stock > max || stock < min){
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Inventory must be between Min and Max.");
                error.showAndWait();
            }
            else if(addProductNameTxt.getText().isEmpty()) {
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Fields cannot be left blank.");
                error.showAndWait();
            }
            else {
                Inventory.addProduct(createdProduct);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch(NumberFormatException e){
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Warning Dialog");
            error.setContentText("Please enter a valid value in each text field.");
            error.showAndWait();
        }
    }
    /**
     Action event method. This method provides search functionality for the top table(parts inventory).
     @param event search box is used.
     */
    @FXML
    void onActionSearchBox(ActionEvent event){
        String nameOrId = addProductSearchTxt.getText();
        ObservableList<Part> parts = Inventory.lookupPart(nameOrId);
        //This checks to see if the person entered a product id instead of the name and returns the product whose id matches.
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
        addProductTopTable.setItems(parts);
    }
    /**
     Initialized the controller class.
     @param url
     @param rb
     */
    @Override
    public void initialize (URL url, ResourceBundle rb){

        addProductIDTxt.setText(String.valueOf(Inventory.getAllProducts().size()+1));
        addProductTopTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCostPerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        colPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


}
