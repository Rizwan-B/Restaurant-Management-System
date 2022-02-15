package uk.ac.rhul.rms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChangeMenuScreenController implements ControlledScreen, Initializable {
    private ScreensController screensController;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screensController = screenParent;
    }

    @FXML
    private Text changeMenu;

    @FXML
    private Text dessert;

    @FXML
    private ListView<String> dessertListView;

    @FXML
    private Text main;

    @FXML
    private ListView<String> mainListView;

    @FXML
    private Text starter;

    @FXML
    private ListView<String> starterListView;

    /**
     * This method shows the whole menu.
     */
    public void fullMenu() {
        try {
            ArrayList<MenuItem> nonVegStarter = DatabaseController.getMenuItems(DatabaseConnection.getInstance(), "Starters");
            for (MenuItem v : nonVegStarter) {
                System.out.println(v.getName());
                this.starterListView.getItems().add(v.getName());
            }
            ArrayList<MenuItem> nonVegMain = DatabaseController.getMenuItems(DatabaseConnection.getInstance(), "Main");
            for (MenuItem v : nonVegMain) {
                System.out.println(v.getName());
                this.mainListView.getItems().add(v.getName());
            }
            ArrayList<MenuItem> nonVegDessert = DatabaseController.getMenuItems(DatabaseConnection.getInstance(), "Dessert");
            for (MenuItem v : nonVegDessert) {
                System.out.println(v.getName());
                this.dessertListView.getItems().add(v.getName());
            }
        } catch(Exception e) {
            System.out.println("oops");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fullMenu();

    }

}
