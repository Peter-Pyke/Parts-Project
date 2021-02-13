package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Inventory;
import model.InHouse;
import model.OutSourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 FXML add part controller class.
 This class is the controller for our AddPartMenu FXML.
 @author Peter
 */
public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Label companyNameOrMachineId;

    @FXML
    private TextField partIdTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partPriceCostTxt;

    @FXML
    private TextField partMinTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField companyNameOrMachineIdTxt;

    @FXML
    private ToggleGroup AddPart;

    @FXML
    private RadioButton outSourceRBtn;

    @FXML
    private RadioButton inHouseRBtn;


    /**
     Action Event method. Sets the scene to Main Menu when the cancel button is clicked.
     @param event Cancel button is clicked.
     */
    @FXML
    void onActionAddPartCancelBtn (ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     Action event method. This method changes the Company Name label to MachineID.
     @param event Radio Button is selected.
     */
    @FXML
    void onActionInHouseRBtn(ActionEvent event) {
        companyNameOrMachineId.setText("MachineID");
    }
    /**
     Action Event method. This method changes the MachineID label to Company Name when the Out Sourced RBtn is clicked.
     @param event Radio Button is selected.
    */
    @FXML
    void onActionOutSourcedPart(ActionEvent event) throws IOException
    {
        companyNameOrMachineId.setText("Company Name");
    }
    /**
     Action Event method. This method will save the information placed inside the add part Menu.
     @param event Save Button clicked.
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException
    {
        //Checks to see if the part is going to be an in house part or out sourced part.
        if(inHouseRBtn.isSelected()) {
            try {
                int id = Integer.parseInt(partIdTxt.getText());
                String name = partNameTxt.getText();
                int stock = Integer.parseInt(partInvTxt.getText());
                double price = Double.parseDouble(partPriceCostTxt.getText());
                int min = Integer.parseInt(partMinTxt.getText());
                int max = Integer.parseInt(partMaxTxt.getText());
                int machineID = Integer.parseInt(companyNameOrMachineIdTxt.getText());
                //Checks to see if the inventory level is between the min and max. Also catches mistake where min is larger then max or string fields are empty.

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
                else if(partNameTxt.getText().isEmpty()) {
                    Alert error = new Alert(Alert.AlertType.WARNING);
                    error.setTitle("Warning Dialog");
                    error.setContentText("Fields cannot be left blank.");
                    error.showAndWait();
                }
                else{
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
            //Catches error caused by entering doubles instead of integers and vise versa.
            catch(NumberFormatException e){
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Please enter a valid value for each text field.");
                error.showAndWait();
            }
        }
        else if (outSourceRBtn.isSelected()) {
            try {
                int id = Integer.parseInt(partIdTxt.getText());
                String name = partNameTxt.getText();
                System.out.println(name);
                int stock = Integer.parseInt(partInvTxt.getText());
                double price = Double.parseDouble(partPriceCostTxt.getText());
                int min = Integer.parseInt(partMinTxt.getText());
                int max = Integer.parseInt(partMaxTxt.getText());
                String CompanyName = companyNameOrMachineIdTxt.getText();
                //Checks to see if the inventory level is between the min and max. Also catches mistake where min is larger then max.
                //Checks for null values in the name or company name test fields.
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
                else if(partNameTxt.getText().isEmpty() || companyNameOrMachineIdTxt.getText().isEmpty()) {
                    Alert error = new Alert(Alert.AlertType.WARNING);
                    error.setTitle("Warning Dialog");
                    error.setContentText("Fields cannot be left blank.");
                    error.showAndWait();
                }
                else {
                    Inventory.addPart(new OutSourced(id, name, price, stock, min, max, CompanyName));

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
            //Catches error caused by entering doubles instead of integers and vise versa.
            catch(NumberFormatException e){

                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Please enter a valid value for each text field.");
                error.showAndWait();

            }
        }
    }

/**
 Initialize the controller class.
 @param rb
 @param url
 */
    @Override
    public void initialize (URL url, ResourceBundle rb){
    partIdTxt.setText(String.valueOf(Inventory.getAllParts().size()+1));
    }


}
