package model;

/**Defines the Outsourced part class.
 * FUTURE ENHANCEMENTS. The organization could add an observable list and functions to keep
 * track of company objects and allow the organization to track the companies that
 * make their outsourced parts.
 */
public class Outsourced extends Part{

    String companyName;
    /**
     * Constructor for Outsourced objects.
     * @param id the part ID.
     * @param name the part name.
     * @param price the part price.
     * @param stock the amount of the part in stock.
     * @param min the minimum amount of stock required.
     * @param max the maximum amount of stock allowed.
     * @param companyName the name of the company that makes the part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /**Gets the MachineID for the Outsourced Part object.
     * @return the machineID.
     */
    public String getCompanyName() {
        return companyName;
    }
    /**Sets the companyName for the Part object.c
     * @param companyName the companyName to set.
     * RUNTIME ERROR. I tried to use setCompanyName
     * to change the screens for the Modify Part form
     * originally but this was not a complete way to
     * handle this problem, and it created an error.
     * I wrote a function to create a new InHouse
     * object and a new Outsourced object, so I
     * implemented it instead.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
