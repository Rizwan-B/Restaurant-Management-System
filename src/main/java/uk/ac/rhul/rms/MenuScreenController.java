package uk.ac.rhul.rms;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ComboBox;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

/**
 * The screen controller for the menu screen implementing the ControlledScreen Interface.
 *
 * @author Mohamed Yusuf
 *
 */
public class MenuScreenController implements ControlledScreen, Initializable {

    private ScreensController screensController;
    private Connection connection;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screensController = screenParent;
    }

    @FXML
    private ListView<String> starterList;

    @FXML
    private ListView<String> mainList;

    @FXML
    private ListView<String> dessertList;

    @FXML
    private Button backBtn;

    @FXML
    private Button callWaiterBtn;

    @FXML
    private ComboBox filterBox;

    @FXML
    void backBtnPressed(ActionEvent event) {
        this.connection = null; // Garbage collector should remove this value but idk just to be safe.
        this.screensController.setScreen(Main.startScreenID);
    }

    @FXML
    void basketBtnPressed(ActionEvent event) {
        this.screensController.loadScreen(Main.basketScreenID, Main.basketScreenFile);
        this.screensController.setScreen(Main.basketScreenID);
    }

    @FXML
    void callWaiterBtnPressed(ActionEvent event) {
        this.screensController.loadScreen(Main.callWaiterScreenID, Main.callWaiterScreenFile);
        this.screensController.setScreen(Main.callWaiterScreenID);
    }

    @FXML
    void filterSelect(ActionEvent event) {
        String s = filterBox.getSelectionModel().getSelectedItem().toString();
        if (s.equals("Vegan")) {
            veganStarter();
        }
    }


    private void addStarters() {
        try {
            ArrayList<MenuItem> starters = DatabaseController.getMenuItems(this.connection, "Starters");
            for (MenuItem starter : starters) {
                System.out.println(starter.getName());
                this.starterList.getItems().add(starter.getName());
            }
        } catch(Exception e) {
            System.out.println("oops");
        }
    }

    private void addMainCourse() {
        try {
            ArrayList<MenuItem> mains = DatabaseController.getMenuItems(this.connection, "Main");
            for (MenuItem main : mains) {
                System.out.println(main.getName());
                this.mainList.getItems().add(main.getName());
            }
        } catch(Exception e) {
            System.out.println("oops");
        }
    }

    private void addDessert() {
        try {
            ArrayList<MenuItem> desserts = DatabaseController.getMenuItems(this.connection, "Dessert");
            for (MenuItem dessert : desserts) {
                System.out.println(dessert.getName());
                this.dessertList.getItems().add(dessert.getName());
            }
        } catch(Exception e) {
            System.out.println("oops");
        }
    }

    public void veganStarter() {
        try {
            ArrayList<MenuItem> vegan = DatabaseController.getDietType(this.connection, Diet.VEGAN);
            this.starterList.getItems().clear();
            for (MenuItem v : vegan) {
                System.out.println(v.getName());
                this.starterList.getItems().add(v.getName());
            }
        } catch(Exception e) {
            System.out.println("oops");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.connection = DatabaseConnection.getInstance();
        this.addStarters();
        this.addMainCourse();
        this.addDessert();
        ObservableList<String> list = FXCollections.observableArrayList("Vegan", "Vegetarian", "Non Vegetarian");
        filterBox.setItems(list);
    }
}