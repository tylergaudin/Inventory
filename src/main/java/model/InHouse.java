package model;

/**Defines the In-House part class. In-house inherits
 * the provided Part class.
 * FUTURE ENHANCEMENTS. If the organization was
 * interested you could make machineID an observable
 * list of machines objects, so you could store machine
 * information.
 */
public class InHouse extends Part{

    int machineId;
    /**
     * Constructor for InHouse objects.
     * @param id the part ID.
     * @param name the part name.
     * @param price the part price.
     * @param stock the amount of the part in stock.
     * @param min the minimum amount of stock required.
     * @param max the maximum amount of stock allowed.
     * @param machineId the ID of the machine that makes the part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    /**Gets the MachineID for the In-House Part object.
     * @return the machineID.
     */
    public int getMachineId() {
        return machineId;
    }
    /**Sets MachineID for the Part object.
     * @param machineId the machineID to set.
     * RUNTIME ERROR. I tried to use setMachineID
     * to change the screens for the Modify Part form
     * originally but this was not a complete way to
     * handle this problem, and it created an error.
     * I wrote a function to create a new InHouse
     * object and a new Outsourced object, so I
     * implemented this one instead.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
