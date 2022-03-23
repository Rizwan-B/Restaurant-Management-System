package uk.ac.rhul.rms;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

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
            }
        }catch (SQLException e){
            e.printStackTrace();
        }




    }
}
