package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Customer Class.
 * This class with be used to create our Customers.
 * @author Peter.
 */
public class Customer {
    private ObservableList<Product> associatedProducts = FXCollections.observableArrayList();
    private int id;
    private String name;

    public Customer (int id, String name){
        this.id = id;
        this.name = name;

    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
public void addAssociatedProduct (Product product){ associatedProducts.add(product);}

public ObservableList<Product> getAssociatedProduct (){return associatedProducts;}

}
