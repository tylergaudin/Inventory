package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;


import java.io.IOException;
/**This class runs the Add Part Form.
 * FUTURE ENHANCEMENTS. I recommend adding
 * a list of possible companies when you add
 * an outsourced product that would also allow
 * you to add new companies.
 */
public class AddPartFormController {

    Stage stage;
    Parent scene;

    @FXML
    private RadioButton addPartInHouseRBtn;

    @FXML
    private RadioButton addPartOutsourcedRBtn;

    @FXML
    private TextField machineIdTxt;

    @FXML
    private TextField companyIdTxt;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partMinTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField partPriceTxt;

    /**Changes to Main Form scene. A cancel button that changes the scene back to the main form.
     * @param event takes a button click ActionEvent.
     */
    @FXML
    public void onActionCancelPart(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/gaudin/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**Changes to In-House scene. Uses a radio button to change the scene to the In-House Add Part form.
     * @param event takes a button click ActionEvent.
     RUNTIME ERROR. When I first set it up if you clicked the radio button
     to change back to In-House I did not have an action to cover this, so it
     selected both radio buttons and did not change screens properly. I then
     added the onActionDisplayInHouse method and fixed this problem.*/
    @FXML
    public void onActionDisplayInHouse(ActionEvent event) throws IOException
    {
        stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/gaudin/view/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**Changes to Outsourced scene. Uses a radio button
     * to change the scene to the Outsourced Add Part form.
     * @param event takes a button click ActionEvent.
     */
    @FXML
    public void onActionOutsourcedScreen(ActionEvent event) throws IOException
    {
        stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/gaudin/view/AddPartFormScene2.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**Saves user inputted Part. Prompts the user,
     * creates a new In-House part or Outsourced Part object,
     * and changes the scene to the Main form.
     * @param event takes a button click ActionEvent.
     */
    @FXML
    public void onActionSavePart(ActionEvent event) throws IOException
    {
        if(addPartInHouseRBtn.isSelected())
        {
            int id = maxId() + 1;
            String name = partNameTxt.getText();
            double price = 0;
            try
            {
                price = Double.parseDouble(partPriceTxt.getText());
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
                stock = Integer.parseInt(partInvTxt.getText());
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
                min = Integer.parseInt(partMinTxt.getText());
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
                max = Integer.parseInt(partMaxTxt.getText());
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
                machineID = Integer.parseInt(machineIdTxt.getText());
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
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/gaudin/view/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        else if(addPartOutsourcedRBtn.isSelected())
        {
            int id = maxId() + 1;
            String name = partNameTxt.getText();
            double price = 0;
            try
            {
                price = Double.parseDouble(partPriceTxt.getText());
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
                stock = Integer.parseInt(partInvTxt.getText());
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
                min = Integer.parseInt(partMinTxt.getText());
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
                max = Integer.parseInt(partMaxTxt.getText());
            }
            catch (NumberFormatException e)
            {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Cannot Proceed");
                alert1.setContentText("Maximum must be a number.");
                alert1.showAndWait();
                return;
            }
            String companyName = companyIdTxt.getText();

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
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/gaudin/view/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }
    /**Method returns the maximum Part Id value.
     * @return the Max.
     */
    public int maxId()
    {
        int Max = 0;

        for (Part part : Inventory.getAllParts())
        {
            if (Max < part.getId())
            {
                Max = part.getId();
            }
            else
                continue;
        }
        return Max;
    }
}
