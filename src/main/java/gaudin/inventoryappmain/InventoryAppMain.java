package gaudin.inventoryappmain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
/**This defines the InventoryAppMain class.
 * FUTURE ENHANCEMENTS.
 * I recommend building a mobile
 * extension of the application.
 */
public class InventoryAppMain extends Application {
    /**This method loads the Main Form
     * and sets the stage.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryAppMain.class.getResource("/gaudin/view/MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }
    /**The Main method that launches the program.
     * @param args the args.
     * RUNTIME ERROR. I had an error because
     * the start method throws an IO Exception,
     * so I had to add "throws IOException" to
     * the top line and this fixed the issue.
     */


    public static void main(String[] args)
    {
        Part part1 = new InHouse(1,"tire", 10.99, 7, 1, 10,2323);
        Part part2 = new InHouse(2,"axel", 10.99, 7, 1, 10,2311);

        Product product1 = new Product(1000, "car", 2000.00,5, 1, 10);
        Product product2 = new Product(1001, "truck", 2001.00,5, 1, 10);

        Inventory.addPart(part1);
        Inventory.addPart(part2);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        launch();
    }
}