package PJCJavaFXApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;


/**
FXML Main Class. This is the starting point of the application. COMPATIBLE FEATURE FOR NEXT VERSION.
 I think for the next version the addition of projects would be useful. While I was working as a manufacturing engineer
 we had various projects each building different products. A new class could be created for project and products could
 be associated with different projects.
 @author Peter.
 */
public class Main extends Application
{
    /**
     Initial method. This method prints out Starting when the application starts up.
     */
    @Override
    public void init()
    {
        System.out.println("Starting !!");
    }
    // Field for the Label control
    private Label myLabel;
    /**
    Start method. This method starts the application, loads the Main menu.
     @param primaryStage This is the main stage for our application.
    */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     Main method. This method launches the application.
     @param args
     */
    public static void main(String[] args)
    {
        Product product = new Product(1, "Laser",12.22, 16, 15, 20);
        InHouse part = new InHouse(2, "Wheel", 12.22, 12, 10, 15, 100);
        Product product2 = new Product(3, "Car",12.22, 18, 15, 20);
        InHouse part2 = new InHouse(4, "HeadLight", 12.22, 12, 10, 15, 101);
        Product product3 = new Product(5, "TV",12.22, 19, 15, 20);
        InHouse part4 = new InHouse(6, "PCB", 12.22, 12, 10, 15, 102);
        Inventory.addPart(part);
        Inventory.addPart(part2);
        Inventory.addPart(part4);
        Inventory.addProduct(product);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        // Launches the application.
        launch(args);
    }

}