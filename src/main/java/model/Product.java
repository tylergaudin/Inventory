package model;

import javafx.collections.ObservableList;

/**Defines the Product class. The Product class has a dependency on the Part class and
 * the Inventory class depends on it.
 * FUTURE ENHANCEMENTS. The organization could
 * delete the addAssociatedPart and
 * deleteAssociatedPart function as they are not
 * needed.
 */
public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor for Product objects.
     * @param associatedParts the Product's associated Parts.
     * @param id the Product ID.
     * @param name the Product name.
     * @param price the Product price.
     * @param stock the amount of the Product in stock.
     * @param min the minimum amount of stock required.
     * @param max the maximum amount of stock allowed.
     */
    public Product(ObservableList<Part> associatedParts, int id, String name,
                   double price, int stock, int min, int max) {
        this.associatedParts = associatedParts;
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    /**Adds a part to assocParts. The method loops
     * through the observable list, allParts, and if the
     * Part argument's Part ID matches the ID of a listed
     * Part it gets added to assocParts.
     * @param part the Part object to add.
     */
    public void addAssociatedPart(Part part)
    {
        for (Part defaultPart : Inventory.getAllParts())
        {
            if(defaultPart.getId() == part.getId())
                Inventory.getAssocParts().add(part);
        }
    }
    /**Removes a selected Part from associatedParts. The method checks
     * if the Part argument is not null. If it is not null
     * then it removes the Part from the observable list
     * associatedParts and returns True. If it is null the
     * method returns False.
     * @param selectedAssociatedPart the Part object to remove.
     * @return True or False.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        if(selectedAssociatedPart != null)
        {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        return false;
    }
    /**Gets the observable list associatedParts.
     * @return associatedParts.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
    /**Gets the Product's ID.
     * @return id.
     */
    public int getId() {
        return id;
    }
    /**Sets the Product's ID.
     * @param id the ID to set to.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**Gets the Product's name.
     * @return name.
     */
    public String getName() {
        return name;
    }
    /**Sets the Product's name.
     * @param name the name to set to.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**Gets the Product's price.
     * @return price.
     */
    public double getPrice() {
        return price;
    }
    /**Sets the Product's price.
     * @param price the price to set to.
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**Gets the amount of Products in stock.
     * @return stock.
     */
    public int getStock() {
        return stock;
    }
    /**Sets the Product's price.
     * @param stock the amount of stock to set to.
     * RUNTIME ERROR. I used this function
     * to update inventory in one of my tables,
     * but I found it more efficient to create
     * a new object and use an update function
     * to fix the entire record.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**Gets the Product's minimum allowed stock amount.
     * @return min.
     */
    public int getMin() {
        return min;
    }
    /**Sets the Product's minimum allowed stock amount.
     * @param min the min to set.
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**Gets the Product's maximum allowed stock amount.
     * @return min.
     */
    public int getMax() {
        return max;
    }
    /**Sets the Product's maximum allowed stock amount.
     * @param max the max to set.
     */
    public void setMax(int max) {
        this.max = max;
    }
}
