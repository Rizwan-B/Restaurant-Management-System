package uk.ac.rhul.rms;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class NutrientsController implements ControlledScreen, Initializable {

    ScreensController screensController;
    private Connection connection;

    /**
     *
     * @param screenParent The parent ScreensController of 'this' screen.
     */
    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screensController = screenParent;
    }



    @FXML
    private Text itemCalories;

    @FXML
    private ImageView itemImage;

    @FXML
    private Text itemName;

    @FXML
    private Pane pane;



    void getItemName(String item_name){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
