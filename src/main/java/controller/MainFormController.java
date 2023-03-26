package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**This class runs the Main Form. Allows the user to
 * search Part and Product objects, navigate
 * to add and modify screens, and delete Part
 * and Product objects.
 * FUTURE ENHANCEMENTS. I recommend making the table larger for
 * parts, so you can add a column that keeps track of which parts
 * are In-House versus outsourced.
 * My javadoc folder is located inside my src file at:
 * /Users/tylergaudin/Desktop/c482/FirstScreen/src/main/java
 */
public class MainFormController implements Initializable {

    private Stage stage;
    private Parent scene;
    public Label TheLabel;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, Integer> partInvLevelCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, Integer> productInvLevelCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TextField searchPartTxt;

    @FXML
    private TextField searchProductTxt;
    /**Deletes Part from partTableView. Deletes the selected
     * Part object and removes it from partTableView.
     * @param event takes a button click ActionEvent.
     * RUNTIME ERROR. The first time I ran this
     * method I did not set up the if statement to
     * check if anything was selected so when I
     * tested this function it created a runtime
     * error. I then built the if statement to
     * solve the problem.
     */
    @FXML
    public void onActionDeletePart(ActionEvent event)
    {
        Alert alert = new Alert
                (Alert.AlertType.CONFIRMATION,"This will delete the selected item.\n\t\tAre you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK &&
                partTableView.getSelectionModel().getSelectedItem() != null)
        {
            Inventory.deletePart(partTableView.getSelectionModel().getSelectedItem());
        }
        else if (result.isPresent() && result.get() == ButtonType.OK
                && partTableView.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Cannot Proceed");
            alert1.setContentText("There is nothing selected to delete.");
            alert1.showAndWait();
        }
        else if (result.isPresent() && result.get() == ButtonType.CANCEL)
            return;
    }
    /**Deletes Product from productTableView. Deletes the selected
     * Product object and removes it from productTableView.
     * @param event takes a button click ActionEvent.
     */
    @FXML
    public void onActionDeleteProduct(ActionEvent event)
    {
        Alert alert = new Alert
                (Alert.AlertType.CONFIRMATION,"This will delete the selected item.\n\t\tAre you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK
                && productTableView.getSelectionModel().getSelectedItem() != null)
        {
            if(productTableView.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty())
            {
                Inventory.deleteProduct(productTableView.getSelectionModel().getSelectedItem());
            }
            else
            {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Cannot Proceed");
                alert1.setContentText("Please remove associated parts before deleting.");
                alert1.showAndWait();
            }
        }
        else if (result.isPresent() && result.get() == ButtonType.OK
                && productTableView.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Cannot Proceed");
            alert1.setContentText("There is nothing selected to delete.");
            alert1.showAndWait();
        }
        else if (result.isPresent() && result.get() == ButtonType.CANCEL)
            return;
    }
    /**Displays AddPartForm Scene. Displays the AddPartForm
     * Scene that allows the user to create part objects.
     * @param event takes a button click ActionEvent.
     */
    @FXML
    public void onActionDisplayAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/gaudin/view/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**Displays AddProductForm Scene. Displays the AddProductForm
     * Scene that allows the user to create product objects.
     * @param event takes a button click ActionEvent.
     */
    @FXML
    public void onActionDisplayAddProduct(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/gaudin/view/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**Displays ModifyPartForm Scene. Displays the ModifyPartForm
     * Scene that allows the user to change part objects.
     * @param event takes a button click ActionEvent.
     */
    @FXML
    public void onActionDisplayModifyPart(ActionEvent event) throws IOException
    {
        if(partTableView.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Cannot Proceed");
            alert1.setContentText("There is no part selected to modify.");
            alert1.showAndWait();
        }
        else if (partTableView.getSelectionModel().getSelectedItem().getClass() == InHouse.class)
        {
            ModifyPartFormController.setInHouseMainPart
                    ((InHouse) partTableView.getSelectionModel().getSelectedItem());
            ModifyPartFormController.setMainPart(partTableView.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/gaudin/view/ModifyPartForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else if(partTableView.getSelectionModel().getSelectedItem().getClass() == Outsourced.class)
        {
            ModifyPartFormController.setOutsourcedMainPart
                    ((Outsourced) partTableView.getSelectionModel().getSelectedItem());
            ModifyPartFormController.setMainPart(partTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/gaudin/view/ModifyPartFormScene2.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    /**Displays ModifyProductForm Scene. Displays the ModifyProductForm
     * Scene that allows the user to change product objects.
     * @param event takes a button click ActionEvent.
     */
    @FXML
    public void onActionDisplayModifyProduct(ActionEvent event) throws IOException
    {
        if(productTableView.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Cannot Proceed");
            alert1.setContentText("There is no product selected to modify.");
            alert1.showAndWait();
        }
        else
        {
            ModifyProductFormController.setMainProduct(productTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/gaudin/view/ModifyProductForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    /**Exits the program. This method prompts the user
     * then exits the program.
     * @param event takes a button click ActionEvent.
     */
    @FXML
    public void onActionExitProgram(ActionEvent event)
    {
        Alert alert = new Alert
                (Alert.AlertType.CONFIRMATION,"This will exit the program.\n\t Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            System.exit(0);
        }
        else if (result.isPresent() && result.get() == ButtonType.CANCEL)
            return;
        else
            return;
    }
    /**Searches allParts. The user types in the id
     * number or partial/full name of a part and the
     * table updates to only containing Parts that
     * match the search criteria.
     * @param event takes a text input ActionEvent.
     */
    @FXML
    public void onActionSearchForPart(ActionEvent event)
    {
        String q = searchPartTxt.getText();

        ObservableList<Part> parts = Inventory.lookupPart(q);

        if(parts.size() == 0)
        {
            try
            {
                int partId = Integer.parseInt(q);
                Part part = Inventory.lookupPart(partId);
                if (part != null)
                    parts.add(part);
            }
            catch (NumberFormatException e)
            {
                //ignore exception
            }
        }
        if(parts.size() == 0)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Cannot Proceed");
            alert1.setContentText("No results found.");
            alert1.show();
            partTableView.setItems(Inventory.getAllParts());
        }
        else
            partTableView.setItems(parts);
    }
    /**Searches allProducts. The user types in the id
     * number or partial/full name of a product and the
     * table updates to only containing Products that
     * match the search criteria.
     * @param event takes a text input ActionEvent.
     */
    @FXML
    public void onActionSearchForProduct(ActionEvent event)
    {
        String q = searchProductTxt.getText();

        ObservableList<Product> products = Inventory.lookupProduct(q);

        if(products.size() == 0)
        {
            try
            {
                int productId = Integer.parseInt(q);
                Product product = Inventory.lookupProduct(productId);
                if (product != null)
                    products.add(product);
            }
            catch (NumberFormatException e)
            {
                //ignore exception
            }
        }
        if(products.size() == 0)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Cannot Proceed");
            alert1.setContentText("No results found.");
            alert1.show();
            productTableView.setItems(Inventory.getAllProducts());
        }
        else
            productTableView.setItems(products);
    }
    /**Displays and loads the main form and its tables.
     * The method displays the main form and sets the
     * values in the partTableView and productTableView.
     * @param url scene path.
     * @param resourceBundle  the resource bundle/XML file used.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        partTableView.setItems(Inventory.getAllParts());
        productTableView.setItems(Inventory.getAllProducts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
}