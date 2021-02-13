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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 FXML Modify Product controller. This class is the controller for our modify product menu FXML.
 @author Peter
 */
public class ModProductController implements Initializable
{
    Stage stage;
    Parent scene;
    Product myProduct = new Product(0, "box", 10.22, 10, 5, 20);

    @FXML
    private TableView<Part> modProductTopTable;

    @FXML
    private TableColumn<Part, Integer> topPartIDCol;

    @FXML
    private TableColumn<Part, String> topPartNameCol;

    @FXML
    private TableColumn<Part, Integer> topInvCol;

    @FXML
    private TableColumn<Part, Double> topPriceCol;

    @FXML
    private TableView<Part> modProductBotTable;

    @FXML
    private TableColumn<Part, Integer> botPartIDCol;

    @FXML
    private TableColumn<Part, String> botPartNameCol;

    @FXML
    private TableColumn<Part, Integer> botInvCol;

    @FXML
    private TableColumn<Part, Double> botPriceCol;

    @FXML
    private TextField modProductSearchTxt;

    @FXML
    private TextField modProductIDTxt;

    @FXML
    private TextField modProductNameTxt;

    @FXML
    private TextField modProductInvTxt;

    @FXML
    private TextField modProductPriceTxt;

    @FXML
    private TextField modProductMinTxt;

    @FXML
    private TextField modProductMaxTxt;

    /**
     This method is used to add the part selected in the top table of the modify product menu, to the bottom table.
     */
    public void addPartToModProduct() {

    if(modProductTopTable.getSelectionModel().isEmpty()){
        Alert error = new Alert(Alert.AlertType.WARNING);
        error.setTitle("Warning Dialog");
        error.setContentText("Please select a Part.");
        error.showAndWait();
    }
    else {
        Part thePart = modProductTopTable.getSelectionModel().getSelectedItem();
        myProduct.addAssociatedParts(thePart);
        modProductBotTable.setItems(myProduct.getAssociatedParts());
    }
}
/**
 This method is used to remove a part from the bottom table of the modify product menu.
 */
    public void modDeleteSelectedPart (){
        if(modProductBotTable.getSelectionModel().isEmpty()){
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Warning Dialog");
            error.setContentText("Please select a Part.");
            error.showAndWait();
        }
        else {
            Alert error = new Alert(Alert.AlertType.CONFIRMATION);
            error.setTitle("Confirmation Dialog");
            error.setContentText(modProductBotTable.getSelectionModel().getSelectedItem().getName() + " has been removed.");
            error.showAndWait();
            Part partToRemove = modProductBotTable.getSelectionModel().getSelectedItem();
            modProductBotTable.getItems().remove(partToRemove);
        }
    }

    private static Product theProduct = null;
    private static ObservableList<Part> myPartList = null;
/**
 This method is called in the main menu to pass the selected product information.
 ERROR DESCRIPTION AND EXPLANATION OF CORRECTION.
 I got a lot of null pointer exception errors, as well as issues with trying to reference non static members inside
 of static methods while trying to create this method/pass information between controllers. I was able to correct these
 issues by creating local variables/objects and methods. I was able to pass information to the local object variables
 in order to pull the desired formation from them instead of trying to point to object or variables in other class.
 @param selectedProduct Product selected in main menu product table.
 */
    public void passProduct(Product selectedProduct){

        theProduct = selectedProduct;
        myPartList = FXCollections.observableArrayList(theProduct.getAssociatedParts());
        modProductIDTxt.setText(Integer.toString(theProduct.getId()));
        modProductNameTxt.setText(theProduct.getName());
        modProductInvTxt.setText(Integer.toString(theProduct.getStock()));
        modProductPriceTxt.setText(Double.toString(theProduct.getPrice()));
        modProductMinTxt.setText(Integer.toString(theProduct.getMin()));
        modProductMaxTxt.setText(Integer.toString(theProduct.getMax()));
        modProductBotTable.setItems(myPartList);
        //This adds the associated parts from my selected product to the product that i use to set the bottom table.
        //Without this if you add a new part in the modify menu it clears all the previous associated parts in the bot table.
        int index = 0;
        while(index < modProductBotTable.getItems().size()){
            myProduct.addAssociatedParts(modProductBotTable.getItems().get(index));
            index++;
        }

    }
/**
 Action event method. This method calls the add part to product method and adds the selected part to that product.
 @param event Add button is clicked.
 */
    @FXML
    void onActionModProductAddBtn(ActionEvent event) {
        addPartToModProduct();
    }
/**
 Action event method. This method calls the delete part method to remove a part from the bottom table of the modify product menu.
 @param event Remove button is clicked.
 */
    @FXML
    void onActionModProductRemoveBtn(ActionEvent event) {
        modDeleteSelectedPart();
    }
/**
 Action event method. This method uses the update method from inventory to update the product with the new information.
 @param event Save button is clicked.
 */
    @FXML
    void onActionModProductSaveBtn(ActionEvent event) throws IOException{
        try {
            int index = 0;
            int id = Integer.parseInt(modProductIDTxt.getText());
            String name = modProductNameTxt.getText();
            int stock = Integer.parseInt(modProductInvTxt.getText());
            double price = Double.parseDouble(modProductPriceTxt.getText());
            int min = Integer.parseInt(modProductMinTxt.getText());
            int max = Integer.parseInt(modProductMaxTxt.getText());
            Product createdProduct = new Product(id, name, price, stock, min, max);
            while (index < modProductBotTable.getItems().size()) {
                Part partToAdd = modProductBotTable.getItems().get(index);
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
            else if(modProductNameTxt.getText().isEmpty()) {
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Fields cannot be left blank.");
                error.showAndWait();
            }
            else {
                Inventory.updateProduct(Inventory.getAllProducts().indexOf(theProduct), createdProduct);

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
     Action event method. This method provide search functionality for the top table(part inventory).
     @param event Search box is used.
     */
    @FXML
    void onActionModProductSearchBox(ActionEvent event) {
        String nameOrId = modProductSearchTxt.getText();
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
        modProductTopTable.setItems(parts);
    }

    /**
     Action event method. This method changes scene to Main Menu when the Cancel button is click.
     @param event Cancel button is clicked.
     */
    @FXML
    void onActionModProductCancelBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     Initializes the controller class.
     @param url
     @param rb
     */
    @Override
    public void initialize (URL url, ResourceBundle rb){

        modProductTopTable.setItems(Inventory.getAllParts());
        topPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        topPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        topInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        topPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        botPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        botPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        botInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        botPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
