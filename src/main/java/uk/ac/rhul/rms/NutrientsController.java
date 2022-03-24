package uk.ac.rhul.rms;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *
 * @author Muqdas
 */
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
    private Label itemCalories;

    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemName;

    @FXML
    private Pane pane;

    @FXML
    private Label diet;

    @FXML
    private Label itemDescription;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ResultSet Item = DatabaseController.executeQuery(DatabaseConnection.getInstance(),
                "SELECT * FROM menu WHERE item_name = '" + MenuScreenController.itemName + "';");
        try {
            while (Item.next()) {
                String Item_name = Item.getString(2);
                String calories = Item.getString(3);
                String dietType = Item.getString(5);
                String description = Item.getString(6);
                String locate = Item.getString(7);
                itemName.setText(Item_name);
                itemCalories.setText(calories);
                diet.setText(dietType);
                itemDescription.setText(description);

                File file = new File(locate);
                Image image = new Image(file.toURI().toString());
                itemImage.setImage(image);

                File file2 = new File("src/main/resources/uk/ac/rhul/rms/media/nutrients screen.png");
                Image image2 = new Image(file2.toURI().toString());
                BackgroundImage backgroundImage = new BackgroundImage(image2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1,1, true, true, false,false));
                Background background = new Background(backgroundImage);
                pane.setBackground(background);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
