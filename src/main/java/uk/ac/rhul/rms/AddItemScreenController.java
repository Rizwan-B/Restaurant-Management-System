package uk.ac.rhul.rms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

public class AddItemScreenController implements ControlledScreen {
    private ScreensController screensController;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screensController = screenParent;
    }

    @FXML
    private Button addItem;

    @FXML
    private Text ChooseDiet;

    @FXML
    private Button backButton;

    @FXML
    private TextField calories;

    @FXML
    private Text chooseOne;

    @FXML
    private CheckBox dessert;

    @FXML
    private Text headLine;

    @FXML
    private TextField itemDescription;

    @FXML
    private TextField itemID;

    @FXML
    private TextField itemImage;

    @FXML
    private TextField itemName;

    @FXML
    private CheckBox main;

    @FXML
    private CheckBox nonVeg;

    @FXML
    private TextField price;

    @FXML
    private CheckBox starter;

    @FXML
    private CheckBox vegan;

    @FXML
    private CheckBox vegetarian;

    @FXML
    void backBtnPressed(ActionEvent event) {
        this.screensController.loadScreen(Main.changeMenuScreenID, Main.changeMenuScreenFile);
        this.screensController.setScreen(Main.changeMenuScreenID);
    }

    @FXML
    void getText(ActionEvent event) {
        String getItemID = itemID.getText();
        int item_id = Integer.parseInt(getItemID);
        String getItemName = itemName.getText();
        String getCalories = calories.getText();
        String getPrice = price.getText();
        String getItemDescription = itemDescription.getText();
        String getImageLocation = itemImage.getText();
        String getCourseType = " ";
        String getDietType = " ";

        if(starter.isSelected()){
            getCourseType= "Starters";
        }
        if(main.isSelected()){
            getCourseType= "Main";
        }
        if(dessert.isSelected()){
            getCourseType= "Dessert";
        }

        if(vegan.isSelected()){
            getDietType= "Ve";
        }
        if(vegetarian.isSelected()){
            getDietType= "V";
        }
        if(nonVeg.isSelected()){
            getDietType= "N-V";
        }


        Alert alert = new Alert(Alert.AlertType.NONE, "Are you sure you want to add the new item?", ButtonType.YES,ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            try {
                DatabaseConnection.getInstance().createStatement().execute("INSERT INTO menu VALUES('" +
                        item_id + "','" + getItemName + "','" + getCalories + "','" + getCourseType + "','" +
                        getDietType + "','" + getItemDescription + "','" + getImageLocation + "','" + getPrice + "');");
            } catch (Exception e) {
                System.out.println("ERROR: SQL connection error, or you did not add an item to menu.");
            }
        }


    }

    @FXML
    void handleCoursetype(){
        if (starter.isSelected()){
            main.setSelected(false);
            dessert.setSelected(false);
        }
        if (main.isSelected()){
            starter.setSelected(false);
            dessert.setSelected(false);
        }
        if (dessert.isSelected()){
            main.setSelected(false);
            starter.setSelected(false);
        }

    }


    @FXML
    void handleDietType(){
        if (vegan.isSelected()){
            nonVeg.setSelected(false);
            vegetarian.setSelected(false);
        }
        if (nonVeg.isSelected()){
            vegetarian.setSelected(false);
            vegan.setSelected(false);
        }
        if (vegetarian.isSelected()){
            nonVeg.setSelected(false);
            vegan.setSelected(false);
        }

    }


}