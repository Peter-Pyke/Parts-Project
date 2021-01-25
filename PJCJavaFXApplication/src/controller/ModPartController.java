package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 FXML Modify part controller. This class is the controller for our ModPartMenu FXML.
 @author Peter Chouinard.
 */
public class ModPartController implements Initializable{

    Stage stage;
    Parent scene;


    @FXML
    private TextField modPartIDTxt;

    @FXML
    private TextField modPartNameTxt;

    @FXML
    private TextField modPartInvTxt;

    @FXML
    private TextField modPartPriceCostTxt;

    @FXML
    private TextField modPartMinTxt;

    @FXML
    private TextField modPartMaxTxt;

    @FXML
    private Label machineIdOrCompanyName;

    @FXML
    private TextField modPartMachineIDTxt;

    @FXML
    private RadioButton modPartInRBtn;

    @FXML
    private RadioButton modPartOutRBtn;

    private static Part thePart = null;

    /**
     This method is called in the MainMenu to pass the part information.
     @param selectedPart The part selected in the main menu parts table.
     */
    public void passPart(Part selectedPart)
    {
        thePart = selectedPart;
        modPartIDTxt.setText(Integer.toString(thePart.getId()));
        modPartNameTxt.setText(thePart.getName());
        modPartInvTxt.setText(Integer.toString(thePart.getStock()));
        modPartPriceCostTxt.setText(Double.toString(thePart.getPrice()));
        modPartMinTxt.setText(Integer.toString(thePart.getMin()));
        modPartMaxTxt.setText(Integer.toString(thePart.getMax()));

        if(thePart instanceof InHouse){
            modPartInRBtn.setSelected(true);
            machineIdOrCompanyName.setText("Machine ID");
            modPartMachineIDTxt.setText(String.valueOf(((InHouse)thePart).getMachineID()));
        }
        else if(thePart instanceof OutSourced){
            modPartOutRBtn.setSelected(true);
            machineIdOrCompanyName.setText("Company Name");
            modPartMachineIDTxt.setText(((OutSourced)thePart).getCompanyName());
        }

    }
    /**
     Action event method. This method will update the information of the selected part.
     @param event Save button is clicked.
     */
    @FXML
    void onActionUpdatePart(ActionEvent event) throws IOException{
        try {
            if (modPartInRBtn.isSelected()) {
                int id = Integer.parseInt(modPartIDTxt.getText());
                String name = modPartNameTxt.getText();
                int stock = Integer.parseInt(modPartInvTxt.getText());
                double price = Double.parseDouble(modPartPriceCostTxt.getText());
                int min = Integer.parseInt(modPartMinTxt.getText());
                int max = Integer.parseInt(modPartMaxTxt.getText());
                int machineID = Integer.parseInt(modPartMachineIDTxt.getText());
                InHouse updatedPart = new InHouse(id, name, price, stock, min, max, machineID);
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
                else if(modPartNameTxt.getText().isEmpty()) {
                    Alert error = new Alert(Alert.AlertType.WARNING);
                    error.setTitle("Warning Dialog");
                    error.setContentText("Fields cannot be left blank.");
                    error.showAndWait();
                }
                else {
                    Inventory.updatePart(Inventory.getAllParts().indexOf(thePart), updatedPart);

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            } else if (modPartOutRBtn.isSelected()) {
                int id = Integer.parseInt(modPartIDTxt.getText());
                String name = modPartNameTxt.getText();
                int stock = Integer.parseInt(modPartInvTxt.getText());
                double price = Double.parseDouble(modPartPriceCostTxt.getText());
                int min = Integer.parseInt(modPartMinTxt.getText());
                int max = Integer.parseInt(modPartMaxTxt.getText());
                String CompanyName = modPartMachineIDTxt.getText();
                OutSourced updatedPart = (new OutSourced(id, name, price, stock, min, max, CompanyName));
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
                else if(modPartNameTxt.getText().isEmpty()) {
                    Alert error = new Alert(Alert.AlertType.WARNING);
                    error.setTitle("Warning Dialog");
                    error.setContentText("Fields cannot be left blank.");
                    error.showAndWait();
                }
                else {
                    Inventory.updatePart(Inventory.getAllParts().indexOf(thePart), updatedPart);

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        }
        catch (NumberFormatException e){
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Warning Dialog");
            error.setContentText("Please enter a valid value in each text field.");
            error.showAndWait();
        }
    }
    /**
     Action event method. This method changes scene to Main Menu when the Cancel Btn is clicked.
     @param event Cancel button is clicked.
     */
    @FXML
    void onActionCancelBtn(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     Action event method. This method changes the MachineID label to Company Name when the Out Sourced Radio button is selected.
     @param event Radio Button is selected.
     */
    @FXML
    void onActionModPartOutRBtn(ActionEvent event) throws IOException
    {
        machineIdOrCompanyName.setText("Company Name");
    }
    /**
     Action event method. This method changes the Company Name label to MachineID when the In House Radio button is selected.
     */
    @FXML
    void onActionModPartInRBtn(ActionEvent event)
    {
        machineIdOrCompanyName.setText("Machine ID");
    }

    /**
     Initializes the controller class.
     @param url
     @param rb
     */
    @Override
    public void initialize (URL url, ResourceBundle rb)
    {


    }

}
