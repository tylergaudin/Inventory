package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**This class runs the Modify Product Form.
 */
public class ModifyPartFormController implements Initializable {

    Stage stage;
    Parent scene;

    private static InHouse inHouseMainPart = null;
    private static Outsourced outsourcedMainPart = null;
    private static Part mainPart = null;

    /**
     * Sets inHouseMainPart.
     * @param part the part to set
     */
    public static void setInHouseMainPart(InHouse part)
    {
        inHouseMainPart = part;
    }
    /**
     * Sets outsourcedMainPart.
     * @param part the part to set
     */
    public static void setOutsourcedMainPart (Outsourced part)
    {
        outsourcedMainPart = part;
    }
    /**
     * Sets mainPart.
     * @param part the part to set
     */
    public static void setMainPart (Part part)
    {
        mainPart = part;
    }

    @FXML
    private RadioButton modPartInHouseRBtn;

    @FXML
    private RadioButton modPartOutsourcedRBtn;

    @FXML
    private TextField modifyCompanyNameTxt;

    @FXML
    private TextField modifyMachineIdTxt;

    @FXML
    private TextField modifyPartIdTxt;

    @FXML
    private TextField modifyPartInvTxt;

    @FXML
    private TextField modifyPartMaxTxt;

    @FXML
    private TextField modifyPartMinTxt;

    @FXML
    private TextField modifyPartNameTxt;

    @FXML
    private TextField modifyPartPriceTxt;

    /**Changes to Main Form scene. A cancel button that changes the scene back to the main form.
     * @param event takes a button click ActionEvent.
     */
    @FXML
    public void onActionDisplayMainMenu(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/gaudin/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**Changes to In-House scene. Uses a radio button
     * to change the scene to the In-House Modify Part form.
     * It also changes an Outsourced object to an In-House
     * object.
     * @param event takes a button click ActionEvent.
     */
    @FXML
    public void onActionDisplayModifyPartInHouse(ActionEvent event) throws IOException
    {
        changeOutsourcedMainPart(outsourcedMainPart);

        stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/gaudin/view/ModifyPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**Changes to Outsourced scene. Uses a radio button
     * to change the scene to the Outsourced Modify Part form.
     * It also changes an In-House object to an Outsourced
     * object.
     * @param event takes a button click ActionEvent.
     * RUNTIME ERROR. The first time I ran this
     * method I did not set up the changeInhouseMainPart
     * method and I got an error because of the switch
     * from Machine ID to Company Name. So I created and
     * implemented changeInHouseMainPart to solve the error.
     */
    @FXML
    public void onActionDisplayModifyPartOutsourced(ActionEvent event) throws IOException
    {
        changeInHouseMainPart(inHouseMainPart);

        stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/gaudin/view/ModifyPartFormScene2.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**Saves user inputted Part. Prompts the user,
     * creates a new In-House part or Outsourced Part object,
     * and uses it to update the selected Part object.
     * It also changes the scene to the Main form.
     * @param event takes a button click ActionEvent.
     */
    @FXML
    public void onActionSaveModifyPart(ActionEvent event) throws IOException {
        if(modPartInHouseRBtn.isSelected())
        {
            int id = Integer.parseInt(modifyPartIdTxt.getText());
            String name = modifyPartNameTxt.getText();
            double price = 0;
            try
            {
                price = Double.parseDouble(modifyPartPriceTxt.getText());
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
                stock = Integer.parseInt(modifyPartInvTxt.getText());
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
                min = Integer.parseInt(modifyPartMinTxt.getText());
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
                max = Integer.parseInt(modifyPartMaxTxt.getText());
            }
            catch (NumberFormatException e)
            {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Cannot Proceed");
                alert1.setContentText("Maximum must be a number.");
                alert1.showAndWait();
                return;
            }
            int machineID = 0;
            try
            {
                machineID = Integer.parseInt(modifyMachineIdTxt.getText());
            }
            catch (NumberFormatException e)
            {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Cannot Proceed");
                alert1.setContentText("Machine ID must be a number.");
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
                int index = Inventory.getAllParts().indexOf(mainPart);
                InHouse p = new InHouse(id, name, price, stock, min, max, machineID);

                Inventory.updatePart(index, p);
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/gaudin/view/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        else if(modPartOutsourcedRBtn.isSelected())
        {
            int id = Integer.parseInt(modifyPartIdTxt.getText());
            String name = modifyPartNameTxt.getText();
            double price = 0;
            try
            {
                price = Double.parseDouble(modifyPartPriceTxt.getText());
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
                stock = Integer.parseInt(modifyPartInvTxt.getText());
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
                min = Integer.parseInt(modifyPartMinTxt.getText());
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
                max = Integer.parseInt(modifyPartMaxTxt.getText());
            }
            catch (NumberFormatException e)
            {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Cannot Proceed");
                alert1.setContentText("Maximum must be a number.");
                alert1.showAndWait();
                return;
            }
            String companyName = modifyCompanyNameTxt.getText();

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
            else if (companyName.isBlank())
            {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Cannot Proceed");
                alert1.setContentText("Company Name cannot be blank.");
                alert1.showAndWait();
                return;
            }
            else
            {
                int index = Inventory.getAllParts().indexOf(mainPart);
                Outsourced p = new Outsourced(id, name, price, stock, min, max, companyName);

                Inventory.updatePart(index, p);
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/gaudin/view/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }
    /**
     * Changes an In-House Part to an Outsourced Part.
     * @param part the part to change.
     */
    public static void changeInHouseMainPart (InHouse part)
    {
        Outsourced p = new Outsourced(part.getId(), part.getName(), part.getPrice(),
                part.getStock(), part.getMin(), part.getMax(), "");

        setOutsourcedMainPart(p);
    }
    /**
     * Changes an Outsourced Part to an In-House Part.
     * @param part the part to change.
     */
    public static void changeOutsourcedMainPart (Outsourced part)
    {
        InHouse p = new InHouse(part.getId(), part.getName(), part.getPrice(),
                part.getStock(), part.getMin(), part.getMax(), 0);

        setInHouseMainPart(p);
    }
    /**Displays and loads the Modify Part Form and its text values.
     * @param url scene path.
     * @param rb  the resource bundle/XML file used.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        if (modPartInHouseRBtn.isSelected())
        {
            modifyPartIdTxt.setText(String.valueOf(inHouseMainPart.getId()));
            modifyPartNameTxt.setText(String.valueOf(inHouseMainPart.getName()));
            modifyPartInvTxt.setText(String.valueOf(inHouseMainPart.getStock()));
            modifyPartPriceTxt.setText(String.valueOf(inHouseMainPart.getPrice()));
            modifyPartMaxTxt.setText(String.valueOf(inHouseMainPart.getMax()));
            modifyPartMinTxt.setText(String.valueOf(inHouseMainPart.getMin()));
            modifyMachineIdTxt.setText(String.valueOf(inHouseMainPart.getMachineId()));
        }
        else if (modPartOutsourcedRBtn.isSelected())
        {
            modifyPartIdTxt.setText(String.valueOf(outsourcedMainPart.getId()));
            modifyPartNameTxt.setText(String.valueOf(outsourcedMainPart.getName()));
            modifyPartInvTxt.setText(String.valueOf(outsourcedMainPart.getStock()));
            modifyPartPriceTxt.setText(String.valueOf(outsourcedMainPart.getPrice()));
            modifyPartMaxTxt.setText(String.valueOf(outsourcedMainPart.getMax()));
            modifyPartMinTxt.setText(String.valueOf(outsourcedMainPart.getMin()));
            modifyCompanyNameTxt.setText(String.valueOf(outsourcedMainPart.getCompanyName()));
        }
    }
}
