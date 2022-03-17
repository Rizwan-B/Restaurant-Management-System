package uk.ac.rhul.rms;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;

import static java.lang.Double.sum;

/**
 * The screen controller for the menu screen implementing the ControlledScreen Interface.
 *
 * @author Mohamed Yusuf, RIzwan Bagdadi
 *
 */
public class MenuScreenController implements ControlledScreen, Initializable {

  private ScreensController screensController;

  /**
   * Sets the parent screen.
   *
   * @param screenParent The parent ScreensController of 'this' screen.
   */
  @Override
  public void setScreenParent(ScreensController screenParent) {
    this.screensController = screenParent;
  }

  /**
   * Below are listviews, buttons,checkboxes and drop down boxes for menu screen.
   */
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
  private Text track;

  @FXML
  private Text layOut;

  @FXML
  private ListView<String> TrackOrder;


  /**
   * Once back button is pressed start screen is bought up.
   *
   * @param event loads start screen
   */
  @FXML
  void backBtnPressed(ActionEvent event) {
    this.screensController.setScreen(Main.startScreenID);
  }

  /**
   * Method to load basket screen.
   *
   * @param event basket screen is loaded.
   */
  @FXML
  void basketBtnPressed(ActionEvent event) {
    addToDB();
    this.screensController.loadScreen(Main.basketScreenID, Main.basketScreenFile);
    this.screensController.setScreen(Main.basketScreenID);
  }

  /**
   * Once call waiter button is clicked this method takes you to call waiter screen.
   *
   * @param event Call waiter screen is loaded
   */
  @FXML
  void callWaiterBtnPressed(ActionEvent event) {
    this.screensController.loadScreen(Main.callWaiterScreenID, Main.callWaiterScreenFile);
    this.screensController.setScreen(Main.callWaiterScreenID);
  }

  /**
   * This filters the menu to show only selected diet type courses.
   *
   * @param event action event.
   */
  @FXML
  void filterSelect(ActionEvent event) {
    this.starterList.getItems().clear();
    this.mainList.getItems().clear();
    this.dessertList.getItems().clear();
    String s = filterBox.getSelectionModel().getSelectedItem();
    if (s.equals("Vegan")) {
      veganMenu();
    } else if (s.equals("Vegetarian")) {
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
      ArrayList<MenuItem> nonVegStarter =
          DatabaseController.getMenuItems(DatabaseConnection.getInstance(), "Starters");
      for (MenuItem v : nonVegStarter) {
        this.starterList.getItems().add(v.getName() + " - £" + v.getprice());
      }
      ArrayList<MenuItem> nonVegMain =
          DatabaseController.getMenuItems(DatabaseConnection.getInstance(), "Main");
      for (MenuItem v : nonVegMain) {
        this.mainList.getItems().add(v.getName() + " - £" + v.getprice());
      }
      ArrayList<MenuItem> nonVegDessert =
          DatabaseController.getMenuItems(DatabaseConnection.getInstance(), "Dessert");
      for (MenuItem v : nonVegDessert) {
        this.dessertList.getItems().add(v.getName() + " - £" + v.getprice());
      }
    } catch (Exception e) {
      System.out.println("oops");
    }
  }

  /**
   * This filters the menu to show the suitable for vegetarian menu.
   */
  public void vegetarianMenu() {
    try {
      ArrayList<MenuItem> vegStarters = DatabaseController
          .getDietType(DatabaseConnection.getInstance(), Diet.VEGETARIAN, "Starters");
      this.starterList.getItems().clear();
      for (MenuItem v : vegStarters) {
        this.starterList.getItems().add(v.getName() + " - £" + v.getprice());
      }
      ArrayList<MenuItem> vegMain =
          DatabaseController.getDietType(DatabaseConnection.getInstance(), Diet.VEGETARIAN, "Main");
      this.mainList.getItems().clear();
      for (MenuItem v : vegMain) {
        this.mainList.getItems().add(v.getName() + " - £" + v.getprice());
      }
      ArrayList<MenuItem> vegDessert = DatabaseController
          .getDietType(DatabaseConnection.getInstance(), Diet.VEGETARIAN, "Dessert");
      this.dessertList.getItems().clear();
      for (MenuItem v : vegDessert) {
        this.dessertList.getItems().add(v.getName() + " - £" + v.getprice());
      }
    } catch (Exception e) {
      System.out.println("oops");
    }
  }

  /**
   * This filters the menu to show only vegan options.
   */
  public void veganMenu() {
    try {
      ArrayList<MenuItem> veganStarter =
          DatabaseController.getDietType(DatabaseConnection.getInstance(), Diet.VEGAN, "Starters");
      for (MenuItem v : veganStarter) {
        this.starterList.getItems().add(v.getName() + " - £" + v.getprice());
      }
      ArrayList<MenuItem> veganMain =
          DatabaseController.getDietType(DatabaseConnection.getInstance(), Diet.VEGAN, "Main");
      for (MenuItem v : veganMain) {
        this.mainList.getItems().add(v.getName() + " - £" + v.getprice());
      }
      ArrayList<MenuItem> veganDessert =
          DatabaseController.getDietType(DatabaseConnection.getInstance(), Diet.VEGAN, "Dessert");
      for (MenuItem v : veganDessert) {
        this.dessertList.getItems().add(v.getName() + " - £" + v.getprice());
      }
    } catch (Exception e) {
      System.out.println(e.toString());
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

  /**
   * This method is to add starters items, main items and desert items into basket.
   *
   * @param event adds items to basket.
   */
  @FXML
  void add(ActionEvent event) {

    for (int i = 0; i < quantity.getSelectionModel().getSelectedItem(); i++) { // Loop to add
                                                                               // quantity desired.

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
    quantity.setValue(1);
    // wordRepeated();
  }

  /**
   * This method is to remove items from basket.
   *
   * @param event remove items from basket.
   */
  @FXML
  void remove(ActionEvent event) {
    basketList.getItems().remove(basketList.getSelectionModel().getSelectedItem());
    basketList.getSelectionModel().clearSelection();
  }

  /**
   * This method is to add items in database which are in basket.
   */
  @FXML
  void addToDB() {

    String orderList = "";
    if (basketList.getItems().size() != 0) {
      for (int i = 0; i < basketList.getItems().size(); i++) {
        orderList += basketList.getItems().get(i) + "-";
      }
      try {
        DatabaseController.makeTempOrder(DatabaseConnection.getInstance(), -1, orderList);
      } catch (SQLException e) {
        System.out.println("Problem using the database: " + e + "\n At line 241.");
      }
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    nonVegMenu(); // Sets default menu as Non-Vegetarian.
    ObservableList<String> list =
        FXCollections.observableArrayList("Non-Vegetarian", "Vegetarian", "Vegan");
    filterBox.setItems(list); // This initialises the drop-down menu with 3 diet options.
    ObservableList<Integer> quantityList =
        FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
    quantity.setItems(quantityList); // This initialises the drop-down quantity with 3 options 1-10.
    quantity.setValue(1); // Sets the default value of quantity to 1.
  }
}
