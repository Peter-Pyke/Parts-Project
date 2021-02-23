package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
  Inventory class. Holds data for parts and products, has methods to retrieve and modify data.
 @author Peter.
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    /**
     This method adds a part to the allParts list.
     This method will be called when the save button is clicked inside the add part menu.
     @param part part to be added.
     */
    public static void addPart(Part part)
    {

    allParts.add(part);

    }
    /**
     This method adds a Product to the allProducts list.
     This method will be called when the save button is clicked inside the add product menu.
     @param product The product to be added.
     */
    public static void addProduct(Product product)
    {
        allProducts.add(product);
    }
    /**
     This method searches through a table to find the desired part.
     This method uses the parts ID to search.
     @param partId integer parse of string entered into search box in main menu.
     @return np matched part

     */
    /**
     * This method will add a Customer to the allCustomer list.
     * @param customer The Customer to be added.
     */
    public static void addCustomer(Customer customer){ allCustomers.add(customer);}

    public static Part lookupPart(int partId)
    {
        ObservableList<Part> allParts = Inventory.getAllParts();

        for(int i = 0; i < allParts.size(); i++)
        {
            Part np = allParts.get(i);
            if(np.getId() == partId)
                return np;
        }
        return null;
    }
    /**
     This method searches a table to find the desired Product.
     This method uses the Product ID to search.
     @param productId Product ID that is entered into the search box.
     @return np matched product.
     */
    public static Product lookupProduct(int productId)
    {
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(int i = 0; i < allProducts.size(); i++)
        {
            Product np = allProducts.get(i);
            if(np.getId() == productId)
                return np;
        }
        return null;
    }
    /**
     This method searches a table to find the desired Part.
     This method uses the part name to search.
     @param partName string that was entered into search box.
     @return namedParts an observable list.
     */
    public static ObservableList<Part> lookupPart(String partName)
    {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part np : allParts)
        {
            if(np.getName().contains(partName))
            {
                namedParts.add(np);
            }
        }
        return namedParts;
    }
    /**
     This method searches a table to find the desired Product.
     This method uses the Product name to search.
     @param productName the Product name entered into the search box.
     @return namedProduct product matching the name entered into the search box.
     */
    public static ObservableList<Product> lookupProduct(String productName)
    {
        ObservableList<Product> namedProduct = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product np : allProducts)
        {
            if(np.getName().contains(productName))
            {
                namedProduct.add(np);
            }
        }
        return namedProduct;
    }
    /**
     This method replaces the old part with the updated part.
     @param selectedPart updated part.
     @param index index of old part.
     */
    public static void updatePart(int index, Part selectedPart)
    {
        allParts.set(index, selectedPart);
    }
    /**
    This method is used to replace an old product with the updated product.
     @param selectedProduct updated product.
     @param index index of old product.
     */
    public static void updateProduct(int index, Product selectedProduct)
    {
        allProducts.set(index, selectedProduct);
    }
    /**
     This method is used to replace an old Customer with an updated Customer.
     @param selectedCustomer updated Customer.
     @param index index of old Customer.
     */
    public static void updateCustomer(int index, Customer selectedCustomer){allCustomers.set(index, selectedCustomer);}
    /**
     This method will be called when deleting parts.
     @param part part to be deleted.
     @return updated inventory.
     */
     public static boolean deletePart(Part part)
        {
            return Inventory.getAllParts().remove(part);
        }
    /**
     This method will be called when deleting products.
     @param product the product to be deleted.
     @return updated inventory.
     */
    public static boolean deleteProduct(Product product){ return Inventory.getAllProducts().remove(product);}
    /**
     * This method will be called when deleting customer.
     * @param customer the selected customer.
     * @return updated inventory.
     */
    public static boolean deleteCustomer(Customer customer) {return Inventory.getAllCustomers().remove(customer);}
    /**
     This method will return all Parts.
     @return returns allParts list.
     */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }
    /**
     This method will return all Products.
     @return allProducts list.
     */
    public static ObservableList<Product> getAllProducts(){return allProducts;}
    /**
     * This method will return all Customers.
     * @return allCustomers list.
     */
    public static ObservableList<Customer> getAllCustomers(){return allCustomers;}
}
