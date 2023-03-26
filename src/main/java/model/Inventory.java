package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**Defines the Inventory class. The Inventory class has a dependency on the Part and Product
 * classes. It houses methods and Observable Lists that access Parts and Products.
 * FUTURE ENHANCEMENTS. The organization could add an observable list and functions to keep
 * track of company objects and allow the organization to track the companies that
 * make their outsourced parts.
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    //My created observable lists.
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Part> assocParts = FXCollections.observableArrayList();
    private static ObservableList<Part> modifyAssocParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    /**Adds Part object to allParts. The Part
     * object is added to allParts the Observable
     * list of all the parts.
     * @param newPart the newPart to add.
     */
    public static void addPart(Part newPart)
    {
        allParts.add(newPart);
    }

    /**Adds Product object to allProducts. The Product
     * object is added to allProducts the Observable
     * list of all the products.
     * @param newProduct the newProduct to add.
     */
    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }

    /**Searches allParts by ID.
     * Searches the observable list allParts
     * by parameter partId.
     * @param partId the partId to search for.
     * @return the queried Part or null.
     */
    public static Part lookupPart(int partId)
    {
        for(Part p : Inventory.getAllParts())
        {
            if(p.getId() == partId)
                return p;
        }
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Cannot Proceed");
        alert1.setContentText("No results found.");
        alert1.show();
        return null;
    }
    /**Searches allProducts by ID.
     * Searches the observable list allProducts
     * by parameter productId.
     * @param productId the productId to search for.
     * @return the queried Product or null.
     */
    public static Product lookupProduct(int productId)
    {
        for(Product product : Inventory.getAllProducts())
        {
            if(product.getId() == productId)
                return product;
        }
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Cannot Proceed");
        alert1.setContentText("No results found.");
        alert1.show();
        return null;
    }
    /**Loads and returns filteredParts. This method adds parts
     * to the Observable list filteredParts if they contain
     * characters that match characters in the string partName.
     * It also clears filteredParts if there are already Part objects
     * in it.
     * @param partName the string value to search for.
     * @return an observable list of parts called filteredParts.
     * RUNTIME ERROR. I wrote this without the first if statement
     * originally and this created a logical error returning too many
     * objects. I added the if clause and this fixed the issue.
     */
    public static ObservableList<Part> lookupPart(String partName)
    {
        if(!(Inventory.getFilteredParts().isEmpty()))
        {
            Inventory.getFilteredParts().clear();
        }

        for (Part inHouse : Inventory.getAllParts())
        {
            if(inHouse.getName().toUpperCase().contains(partName.toUpperCase()))
                Inventory.getFilteredParts().add(inHouse);
        }
        return Inventory.getFilteredParts();
    }
    /**Loads and returns filteredProducts. This method adds products
     * to the Observable list filteredProducts if they contain
     * characters that match characters in the string productName.
     * It also clears filteredProducts if there are already Product objects
     * in it.
     * @param productName the string value to search for.
     * @return an observable list of parts called filteredProducts.
     */
    public static ObservableList<Product> lookupProduct(String productName)
    {

        if (!(Inventory.getFilteredProducts().isEmpty())) {
            Inventory.getFilteredProducts().clear();
        }

        for (Product p : Inventory.getAllProducts()) {
            if (p.getName().toUpperCase().contains(productName.toUpperCase()))
                Inventory.getFilteredProducts().add(p);
        }
        return Inventory.getFilteredProducts();
    }
    /**Finds Part in allParts and updates it. This method uses
     * the index argument and updates the value in the observable list
     * allParts to the input selectedPart argument.
     * @param index the location of the row that needs to be updated.
     * @param selectedPart the object to update the row to.
     */
    public static void updatePart(int index, Part selectedPart)
    {
        for (Part part : Inventory.getAllParts())
        {
            if (part.getId() == selectedPart.getId())
            {
                Inventory.getAllParts().set(index, selectedPart);
            }
        }
    }
    /**Finds Product in allProducts and updates it. This method uses
     * the index argument and updates the value in the observable list
     * allProducts to the input newProduct argument.
     * @param index the location of the row that needs to be updated.
     * @param newProduct the object to update the row to.
     */
    public static void updateProduct(int index, Product newProduct)
    {
        allProducts.set(index, newProduct);
    }
    /**Finds Part in allParts and deletes it. This method loops through
     * the observable list allParts until it finds a matching Part Id
     * then it removes the part in allParts and returns True. If it does not
     * find a matching Id it returns false.
     * @param selectedPart the object to search for and remove.
     * @return True or False.
     */
    public static boolean deletePart(Part selectedPart)
    {
        for (Part part : Inventory.getAllParts())
        {
            if(part.getId() == selectedPart.getId())
                return Inventory.getAllParts().remove(part);
        }
        return false;
    }
    /**Finds Product in allProducts and deletes it. This method loops through
     * the observable list allProducts until it finds a matching Product Id
     * then it removes the Product in allProducts and returns True. If it does not
     * find a matching Id it returns false.
     * @param selectedProduct the object to search for and remove.
     * @return True or False.
     */
    public static boolean deleteProduct(Product selectedProduct)
    {
        for (Product p : Inventory.getAllProducts())
        {
            if(p.getId() == selectedProduct.getId())
                return Inventory.getAllProducts().remove(selectedProduct);
        }
        return false;
    }
    /**Gets the observable list allParts.
     * @return allParts.
     */
    public static ObservableList<Part> getAllParts(){return allParts;}
    /**Gets the observable list allProducts.
     * @return allProducts.
     */
    public static ObservableList<Product> getAllProducts(){return allProducts;}
    /**Gets the observable list filteredParts.
     * @return filteredParts.
     */
    public static ObservableList<Part> getFilteredParts(){return filteredParts;}
    /**Gets the observable list assocParts.
     * @return assocParts.
     */
    public static ObservableList<Part> getAssocParts(){return assocParts;}
    public static void setModifyAssocParts(ObservableList<Part> assocParts) {Inventory.assocParts = assocParts;}
    /**Gets the observable list assocParts.
     * @return assocParts.
     */
    public static ObservableList<Part> getModifyAssocParts(){return modifyAssocParts;}
    /**Gets the observable list filteredProducts.
     * @return filteredProducts.
     */
    public static ObservableList<Product> getFilteredProducts(){return filteredProducts;}
}
