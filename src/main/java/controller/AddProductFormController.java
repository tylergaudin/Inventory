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

/**This class runs the Add Product Form.
 * FUTURE ENHANCEMENTS. I recommend adding the feature to select
 * multiple parts and add them to the product at one time.
 */
public class AddProductFormController implements Initializable {

    Stage stage;
    Parent scene;
    private static ObservableList<Part> associatedParts = Inventory.getAssocParts();

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
     * RUNTIME ERROR. I tried adding parts to
     * assocPartsTableView by ID using a for loop.
     * When I ran it I got a runtime error, so I
     * changed to using the Observable list.
     */
    @FXML
    public void onActionAddPartToProduct(ActionEvent event)
    {
        associatedParts.add(partBankTableView.getSelectionModel().getSelectedItem());

        assocPartsTableView.setItems(associatedParts);

        assocPartsPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartsPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    /**Displays the Main Form. The user clicks the cancel
     * button and the method displays the MainForm scene.
     * @param event takes a button click ActionEvent.
     */
    @FXML
    public void onActionExitAddProduct(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
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
    /**Removes Part from associatedParts. Prompts the user,
     * and removes the selected part from associatedParts and the
     * assocPartsTableView table.
     * @param event takes a button click ActionEvent.
     */
    @FXML
    public void onActionRemovePartFromProduct(ActionEvent event)
    {
        Alert alert = new Alert
                (Alert.AlertType.CONFIRMATION,"This will remove the selected part.\n\t\tAre you sure?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK
                && assocPartsTableView.getSelectionModel().getSelectedItem() != null)
        {
            associatedParts.remove(assocPartsTableView.getSelectionModel().getSelectedItem());

            assocPartsTableView.setItems(associatedParts);

            assocPartsPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            assocPartsPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            assocPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            assocPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
        else if (result.isPresent() && result.get() == ButtonType.OK
                && assocPartsTableView.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Cannot Proceed");
            alert1.setContentText("There is nothing selected to delete.");
            alert1.showAndWait();
        }
        else if (result.isPresent() && result.get() == ButtonType.CANCEL)
            return;
    }
    /**Saves user inputted Product. Prompts the user,
     * creates a new Product object,
     * and changes the scene to the Main form.
     * @param event takes a button click ActionEvent.
     */
    @FXML
    public void onActionSaveProduct(ActionEvent event) throws IOException
    {
        int id = maxId() + 1;
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
           Inventory.addProduct(new Product(associatedParts, id, name, price, stock, min, max));
           stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
           scene = FXMLLoader.load(getClass().getResource("/gaudin/view/MainForm.fxml"));
           stage.setScene(new Scene(scene));
           stage.show();
       }
    }
    /**Method returns the maximum Product Id value.
     * @return the Max.
     */
    public int maxId()
    {
        int Max = 999;

        for (Product product : Inventory.getAllProducts())
        {
            if(Inventory.getAllProducts().size() > 1)
            {
                int n = Inventory.getAllProducts().indexOf(product) + 1;
                Product product2 = Inventory.getAllProducts().get(n);

                if (product.getId() > product2.getId())
                {
                    Max = product.getId();
                }
                else
                    continue;
                }
            else
                Max = 1000;
        }
        return Max;
    }
    /**AddProductFormController initialization interface.
     * The method initializes when the AddProductForm scene is loaded. It sets the
     * partBankTableView table and displays the Add Product Form.
     * @param url scene path.
     * @param rb  the resource bundle/XML file used.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        partBankTableView.setItems(Inventory.getAllParts());

        partBankIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partBankNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partBankInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partBankPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
