package uk.ac.rhul.rms;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
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
    private ListView<String> basketList;

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
    private ComboBox<Integer> quantity;

    @FXML
    private CheckBox shellfish;

    @FXML
    private CheckBox wheat;

    @FXML
    void backBtnPressed(ActionEvent event) {
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
            veganMenu(); // Vegetarian menu includes vegan options.
        } else { // Non-Vegetarian menu includes vegetarian and vegan options.
            nonVegMenu();
        }
    }

    /**
     * This filters the menu to show the Non-Vegetarian menu.
     */
    public void nonVegMenu() {
        try {
            ArrayList<MenuItem> nonVegStarter = DatabaseController.getMenuItems(DatabaseConnection.getInstance(), "Starters");
            for (MenuItem v : nonVegStarter) {
                System.out.println(v.getName());
                this.starterList.getItems().add(v.getName());
            }
            ArrayList<MenuItem> nonVegMain = DatabaseController.getMenuItems(DatabaseConnection.getInstance(), "Main");
            for (MenuItem v : nonVegMain) {
                System.out.println(v.getName());
                this.mainList.getItems().add(v.getName());
            }
            ArrayList<MenuItem> nonVegDessert = DatabaseController.getMenuItems(DatabaseConnection.getInstance(), "Dessert");
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
            ArrayList<MenuItem> vegStarters = DatabaseController.getDietType(DatabaseConnection.getInstance(), Diet.VEGETARIAN, "Starters");
            this.starterList.getItems().clear();
            for (MenuItem v : vegStarters) {
                System.out.println(v.getName());
                this.starterList.getItems().add(v.getName());
            }
            ArrayList<MenuItem> vegMain = DatabaseController.getDietType(DatabaseConnection.getInstance(), Diet.VEGETARIAN, "Main");
            this.mainList.getItems().clear();
            for (MenuItem v : vegMain) {
                System.out.println(v.getName());
                this.mainList.getItems().add(v.getName());
            }
            ArrayList<MenuItem> vegDessert = DatabaseController.getDietType(DatabaseConnection.getInstance(), Diet.VEGETARIAN, "Dessert");
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
            ArrayList<MenuItem> veganStarter = DatabaseController.getDietType(DatabaseConnection.getInstance(), Diet.VEGAN, "Starters");
            for (MenuItem v : veganStarter) {
                System.out.println(v.getName());
                this.starterList.getItems().add(v.getName());
            }
            ArrayList<MenuItem> veganMain = DatabaseController.getDietType(DatabaseConnection.getInstance(), Diet.VEGAN, "Main");
            for (MenuItem v : veganMain) {
                System.out.println(v.getName());
                this.mainList.getItems().add(v.getName());
            }
            ArrayList<MenuItem> veganDessert = DatabaseController.getDietType(DatabaseConnection.getInstance(), Diet.VEGAN, "Dessert");
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

    @FXML
    void add(ActionEvent event){
        for (int i = 0; i < quantity.getSelectionModel().getSelectedItem(); i++) { // Loop to add quantity desired.

            if (starterList.getSelectionModel().getSelectedItem() != null) {
                basketList.getItems().add(starterList.getSelectionModel().getSelectedItem());
            }
            if (mainList.getSelectionModel().getSelectedItem() != null) {
                basketList.getItems().add(mainList.getSelectionModel().getSelectedItem());
            }
            if (dessertList.getSelectionModel().getSelectedItem() != null) {
                basketList.getItems().add(dessertList.getSelectionModel().getSelectedItem());
            }
        }
        starterList.getSelectionModel().clearSelection();
        mainList.getSelectionModel().clearSelection();
        dessertList.getSelectionModel().clearSelection();
    }

    @FXML
    void remove(ActionEvent event){
        basketList.getItems().remove(basketList.getSelectionModel().getSelectedItem());
        basketList.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nonVegMenu(); // Sets default menu as Non-Vegetarian.
        ObservableList<String> list = FXCollections.observableArrayList("Non-Vegetarian", "Vegetarian", "Vegan");
        filterBox.setItems(list); // This initialises the drop-down menu with 3 diet options.
        ObservableList<Integer> quantityList = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11, 12, 13, 14, 15);
        quantity.setItems(quantityList); // This initialises the drop-down quantity with 3 options 1-10.
        quantity.setValue(1); // Sets the default value of quantity to 1.
    }


}