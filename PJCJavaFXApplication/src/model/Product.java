package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 Product class.
 This class is used to create our Products.
 @author Peter Chouinard.
 */
public class Product {

    private ObservableList<Part> associatedPart = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    /**
     This is our setter for the product id parameter.
     @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     This is our setter for the product name parameter.
     @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     This is our setter for the product price parameter.
     @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     This is our setter for our product stock/inv parameter.
     @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     This is our setter for the product min parameter.
     @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     This is our setter for the product max parameter.
     @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    /**
     This is our getter for the product id parameter.
     @return the product id.
     */
    public int getId() {
        return id;
    }
    /**
     This is our getter for the product name parameter.
     @return the name
     */
    public String getName() {
        return name;
    }
    /**
     This is our getter for the product price parameter.
     @return the price
     */
    public double getPrice() {
        return price;
    }
    /**
     This is our getter for the product stock/inv parameter.
     @return the stock
     */
    public int getStock() {
        return stock;
    }
    /**
     This is our getter for the product min parameter.
     @return the min
     */
    public int getMin() {
        return min;
    }
    /**
     This is the getter for the product max parameter.
     @return the max
     */
    public int getMax() {
        return max;
    }
    /**
     This method will add parts to our associated parts list.
     @param part part to be added.
     */
    public void addAssociatedParts(Part part){
        associatedPart.add(part);
    }
    /**
     This method will remove an associated part.
     @param selectedAssociatedPart the part to be removed.
     @return updated associated part list.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){

      return associatedPart.remove(selectedAssociatedPart);

    }
    /**
     This method returns all associatedParts.
     @return parts associated with the selected product.
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedPart;
    }

}
