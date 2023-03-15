package controller;

import javafx.collections.FXCollections;
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

/**This class runs the Modify Product Form.
 * FUTURE ENHANCEMENTS. I recommend adding
 * the ability to update the product ID,
 * so you can group certain products together.
 * This will make the main table more organized.
 */
public class ModifyProductFormController implements Initializable {

    Stage stage;
    Parent scene;


    private static Product mainProduct = null;
    private ObservableList<Part> modifyAssociatedParts = FXCollections.observableArrayList();
    /**
     * Sets mainProduct.
     * @param product the product to set.
     */
    public static void setMainProduct (Product product)
    {
        mainProduct = product;
        System.out.println("main method");
    }

    @FXML
    private Label TheLabel;

    @FXML
    private TableColumn<Part, Integer> assocPartsInvCol;

    @FXML
    private TableColumn<Part, Integer> assocPartsPartIdCol;

    @FXML
    private TableColumn<Part, String> assocPartsPartNameCol;

    @FXML
    private TableColumn<Part, Double> assocPartsPriceCol;

    @FXML
    private TableView<Part> assocPartsTableView;

    @FXML
    private TableColumn<Part, Integer> partBankIdCol;

    @FXML
    private TableColumn<Part, Integer> partBankInvCol;

    @FXML
    private TableColumn<Part, String> partBankNameCol;

    @FXML
    private TableColumn<Part, Double> partBankPriceCol;

    @FXML
    private TableView<Part> partBankTableView;

    @FXML
    private TextField partSearchTxt;

    @FXML
    private TextField productIdTxt;

    @FXML
    private TextField productInvTxt;

    @FXML
    private TextField productMaxTxt;

    @FXML
    private TextField productMinTxt;

    @FXML
    private TextField productNameTxt;

    @FXML
    private TextField productPriceTxt;

    /**Adds Part to associatedParts. Adds the selected
     * part to associatedParts from partBankTableView and the
     * assocPartsTableView table.
     * @param event takes a button click ActionEvent.
     */
    @FXML
    public void onActionAddPartToProduct(ActionEvent event)
    {
        modifyAssociatedParts.add(partBankTableView.getSelectionModel().getSelectedItem());

        assocPartsTableView.setItems(modifyAssociatedParts);

        assocPartsPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartsPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    /**Displays the Main Form. The user clicks the cancel
     * button and the method displays the MainForm scene.
     * @param event takes a button click ActionEvent.*/
    @FXML
    public void onActionExitModifyProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/gaudin/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**Searches allParts. The user types in the id
     * number or partial/full name of a part and the
     * table updates to only containing Parts that
     * match the search criteria.
     * @param event takes a text input ActionEvent.
     */
    @FXML
    public void onActionPartSearch(ActionEvent event)
    {
        String q = partSearchTxt.getText();

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
            partBankTableView.setItems(Inventory.getAllParts());
        }
        else
        {
            partBankTableView.setItems(parts);
            if (parts.size() == 1) {
                partBankTableView.getSelectionModel().select(0);
            }
        }
    }
    /**Removes Part from Product's associatedParts. Prompts the user,
     * and removes the selected part from the Product object's associatedParts
     * and the assocPartsTableView table.
     * @param event takes a button click ActionEvent.
     */
    @FXML
    public void onActionRemovePartFromProduct(ActionEvent event)
    {
        Alert alert = new Alert
                (Alert.AlertType.CONFIRMATION,"This will remove the selected part.\n\t\tAre you sure?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            if(modifyAssociatedParts.remove(assocPartsTableView.getSelectionModel().getSelectedItem()))
            {
                assocPartsTableView.setItems(modifyAssociatedParts);

                assocPartsPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                assocPartsPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                assocPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                assocPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            }
            else
            {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Cannot Proceed");
                alert1.setContentText("There is nothing selected to delete.");
                alert1.showAndWait();
            }
        }
        else if (result.isPresent() && result.get() == ButtonType.CANCEL)
            return;
    }
    /**Saves user inputted Product. Prompts the user,
     * creates a new Product object and uses it to update
     * the prior Product object. It then changes the scene to the Main form.
     * @param event takes a button click ActionEvent.
     * RUNTIME ERROR. I originally wrote this code without the
     * "try""catch" statements and when I tested the form by entering
     * letters in the productInvTxt field it had a runtime error.
     * I then implemented the "try" "catch" statements and this
     * fixed the issue.
     */
    @FXML
    public void onActionSaveProduct(ActionEvent event) throws IOException
    {
        int id = Integer.parseInt(productIdTxt.getText());
        String name = productNameTxt.getText();
        double price = 0;
        try
        {
            price = Double.parseDouble(productPriceTxt.getText());
        }
        catch (NumberFormatException e)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Cannot Proceed");
            alert1.setContentText("Price must be a decimal number.");
            alert1.showAndWait();
            return;
        }
        int stock = 0;
        try
        {
            stock = Integer.parseInt(productInvTxt.getText());
        }
        catch (NumberFormatException e)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Cannot Proceed");
            alert1.setContentText("Inventory must be a number.");
            alert1.showAndWait();
            return;
        }
        int min = 0;
        try
        {
            min = Integer.parseInt(productMinTxt.getText());
        }
        catch (NumberFormatException e)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Cannot Proceed");
            alert1.setContentText("Minimum must be a number.");
            alert1.showAndWait();
            return;
        }
        int max = 0;
        try
        {
            max = Integer.parseInt(productMaxTxt.getText());
        }
        catch (NumberFormatException e)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Cannot Proceed");
            alert1.setContentText("Maximum must be a number.");
            alert1.showAndWait();
            return;
        }

        if (stock > max || stock < min)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Cannot Proceed");
            alert1.setContentText("Inventory cannot be more than max or less than min, please retry.");
            alert1.showAndWait();
            return;
        }
        else if (name.isBlank())
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Cannot Proceed");
            alert1.setContentText("Name cannot be blank.");
            alert1.showAndWait();
            return;
        }
        else
        {
            Product p = (new Product(id, name, price, stock, min, max));
            int l = Inventory.getAllProducts().indexOf(mainProduct);

            Inventory.updateProduct(l, p);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/gaudin/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    /**Displays and loads the Modify Product Form and its tables.
     * The method initializes when the ModifyProductForm scene is loaded. It sets the
     * partBankTableView table and displays the Add Product Form.
     * @param url scene path.
     * @param rb  the resource bundle/XML file used.
     */
    public void initialize(URL url, ResourceBundle rb)
    {
        Inventory.setModifyAssocParts(mainProduct.getAllAssociatedParts());
        partBankTableView.setItems(Inventory.getAllParts());

        partBankIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partBankNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partBankInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partBankPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIdTxt.setText(String.valueOf(mainProduct.getId()));
        productNameTxt.setText(String.valueOf(mainProduct.getName()));
        productInvTxt.setText(String.valueOf(mainProduct.getStock()));
        productPriceTxt.setText(String.valueOf(mainProduct.getPrice()));
        productMaxTxt.setText(String.valueOf(mainProduct.getMax()));
        productMinTxt.setText(String.valueOf(mainProduct.getMin()));

        assocPartsTableView.setItems(mainProduct.getAllAssociatedParts());
        assocPartsPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartsPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        System.out.println("Initialized");
    }
}
