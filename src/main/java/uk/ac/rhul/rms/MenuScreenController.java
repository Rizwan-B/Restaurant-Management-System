package uk.ac.rhul.rms;

import java.net.URL;
import java.sql.Connection;
//import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

//import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

/**
 * The screen controller for the menu screen implementing the ControlledScreen Interface.
 *
 * @author Mohamed Yusuf, RIzwan Bagdadi
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
    private ComboBox<String> filterBox;

    @FXML
    private CheckBox dairy;

    @FXML
    private CheckBox peanut;

    @FXML
    private CheckBox shellfish;

    @FXML
    private CheckBox wheat;

    @FXML
    void backBtnPressed(ActionEvent event) {
        this.connection = null; // Garbage collector should remove this value but just to be safe.
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
        this.starterList.getItems().clear();
        this.mainList.getItems().clear();
        this.dessertList.getItems().clear();
        String s = filterBox.getSelectionModel().getSelectedItem();
        if (s.equals("Vegan")) {
            veganMenu();
        }
        else if (s.equals("Vegetarian")) {
            vegetarianMenu();
            veganMenu(); //Vegetarian menu includes vegan options.
        } else { //Non-Vegetarian menu includes vegetarian and vegan options.
            nonVegMenu();
        }
    }

    /**
     * This filters the menu to show the Non-Vegetarian menu.
     */
    public void nonVegMenu() {
        try {
            ArrayList<MenuItem> nonVegStarter = DatabaseController.getMenuItems(this.connection, "Starters");
            for (MenuItem v : nonVegStarter) {
                System.out.println(v.getName());
                this.starterList.getItems().add(v.getName());
            }
            ArrayList<MenuItem> nonVegMain = DatabaseController.getMenuItems(this.connection, "Main");
            for (MenuItem v : nonVegMain) {
                System.out.println(v.getName());
                this.mainList.getItems().add(v.getName());
            }
            ArrayList<MenuItem> nonVegDessert = DatabaseController.getMenuItems(this.connection, "Dessert");
            for (MenuItem v : nonVegDessert) {
                System.out.println(v.getName());
                this.dessertList.getItems().add(v.getName());
            }
        } catch(Exception e) {
            System.out.println("oops");
        }
    }

    /**
     * This filters the menu to show the suitable for vegetarian menu.
     */
    public void vegetarianMenu() {
        try {
            ArrayList<MenuItem> vegStarters = DatabaseController.getDietType(this.connection, Diet.VEGETARIAN, "Starters");
            this.starterList.getItems().clear();
            for (MenuItem v : vegStarters) {
                System.out.println(v.getName());
                this.starterList.getItems().add(v.getName());
            }
            ArrayList<MenuItem> vegMain = DatabaseController.getDietType(this.connection, Diet.VEGETARIAN, "Main");
            this.mainList.getItems().clear();
            for (MenuItem v : vegMain) {
                System.out.println(v.getName());
                this.mainList.getItems().add(v.getName());
            }
            ArrayList<MenuItem> vegDessert = DatabaseController.getDietType(this.connection, Diet.VEGETARIAN, "Dessert");
            this.dessertList.getItems().clear();
            for (MenuItem v : vegDessert) {
                System.out.println(v.getName());
                this.dessertList.getItems().add(v.getName());
            }
        } catch(Exception e) {
            System.out.println("oops");
        }
    }

    /**
     * This filters the menu to show only vegan options.
     */
    public void veganMenu() {
        try {
            ArrayList<MenuItem> veganStarter = DatabaseController.getDietType(this.connection, Diet.VEGAN, "Starters");
            for (MenuItem v : veganStarter) {
                System.out.println(v.getName());
                this.starterList.getItems().add(v.getName());
            }
            ArrayList<MenuItem> veganMain = DatabaseController.getDietType(this.connection, Diet.VEGAN, "Main");
            for (MenuItem v : veganMain) {
                System.out.println(v.getName());
                this.mainList.getItems().add(v.getName());
            }
            ArrayList<MenuItem> veganDessert = DatabaseController.getDietType(this.connection, Diet.VEGAN, "Dessert");
            for (MenuItem v : veganDessert) {
                System.out.println(v.getName());
                this.dessertList.getItems().add(v.getName());
            }
        } catch(Exception e) {
            System.out.println("oops");
        }
    }

    // Action methods below need to be completed once allergies are
    // added to the database.
    @FXML
    void dairyFilter(ActionEvent event) {
        if (dairy.isSelected()) {

        }
    }

    @FXML
    void peanutFilter(ActionEvent event) {
        if (peanut.isSelected()) {

        }
    }

    @FXML
    void shellfishFilter(ActionEvent event) {
        if (shellfish.isSelected()) {

        }
    }

    @FXML
    void wheatFilter(ActionEvent event) {
        if (wheat.isSelected()) {

        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.connection = DatabaseConnection.getInstance();
        nonVegMenu();
        ObservableList<String> list = FXCollections.observableArrayList("Non-Vegetarian", "Vegetarian", "Vegan");
        filterBox.setItems(list); //This initialises the drop-down menu with 3 diet options.
    }
}