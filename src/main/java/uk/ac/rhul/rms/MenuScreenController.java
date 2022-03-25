package uk.ac.rhul.rms;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

import javax.xml.crypto.Data;
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

  public static String itemName = "";

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

  @FXML
  private Button refresh;

  @FXML
  private Label items;

  @FXML
  private Pane pane;

  int count = 0;
                              //    dairy, nut, shellfish, wheat, non-vegan, non-veg
  private String[] allergenArray = {"'NOPE'", "'NOPE'", "'NOPE'", "'NOPE'", "'NOPE'", "'NOPE'"};


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
    if (count == 0) {
      Alert alert = new Alert(Alert.AlertType.NONE, "The basket is empty!", ButtonType.OK);
      alert.showAndWait();
    } else {
      addToDB();
      this.screensController.loadScreen(Main.basketScreenID, Main.basketScreenFile);
      this.screensController.setScreen(Main.basketScreenID);
    }
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
      ArrayList<MenuItem> nonVegStarterNV =
          DatabaseController.getMenuItemsFiltered(DatabaseConnection.getInstance(), "Starters", Diet.NON_VEGETERIAN, this.allergenArray);
      ArrayList<MenuItem> nonVegStarterVE =
          DatabaseController.getMenuItemsFiltered(DatabaseConnection.getInstance(), "Starters", Diet.VEGAN, this.allergenArray);
      ArrayList<MenuItem> nonVegStarterV =
          DatabaseController.getMenuItemsFiltered(DatabaseConnection.getInstance(), "Starters", Diet.VEGETARIAN, this.allergenArray);

      nonVegStarterNV.addAll(nonVegStarterVE);
      nonVegStarterNV.addAll(nonVegStarterV);

      for (MenuItem v : nonVegStarterNV) {
        this.starterList.getItems().add(v.getName() + " - £" + v.getprice());
      }
      ArrayList<MenuItem> nonVegMainNV =
          DatabaseController.getMenuItemsFiltered(DatabaseConnection.getInstance(), "Main", Diet.NON_VEGETERIAN, this.allergenArray);
      ArrayList<MenuItem> nonVegMainVE =
          DatabaseController.getMenuItemsFiltered(DatabaseConnection.getInstance(), "Main", Diet.VEGAN, this.allergenArray);
      ArrayList<MenuItem> nonVegMainV =
          DatabaseController.getMenuItemsFiltered(DatabaseConnection.getInstance(), "Main", Diet.VEGETARIAN, this.allergenArray);

      nonVegMainNV.addAll(nonVegMainVE);
      nonVegMainNV.addAll(nonVegMainV);
      for (MenuItem v : nonVegMainNV) {
        this.mainList.getItems().add(v.getName() + " - £" + v.getprice());
      }
      ArrayList<MenuItem> nonVegDessertNV =
          DatabaseController.getMenuItemsFiltered(DatabaseConnection.getInstance(), "Dessert", Diet.NON_VEGETERIAN, this.allergenArray);
      ArrayList<MenuItem> nonVegDessertVE =
          DatabaseController.getMenuItemsFiltered(DatabaseConnection.getInstance(), "Dessert", Diet.VEGAN, this.allergenArray);
      ArrayList<MenuItem> nonVegDessertV =
          DatabaseController.getMenuItemsFiltered(DatabaseConnection.getInstance(), "Dessert", Diet.VEGETARIAN, this.allergenArray);

      nonVegDessertNV.addAll(nonVegDessertVE);
      nonVegDessertNV.addAll(nonVegDessertV);
      for (MenuItem v : nonVegDessertNV) {
        this.dessertList.getItems().add(v.getName() + " - £" + v.getprice());
      }
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  /**
   * This filters the menu to show the suitable for vegetarian menu.
   */
  public void vegetarianMenu() {
    try {
      ArrayList<MenuItem> vegStarters = DatabaseController
          .getMenuItemsFiltered(DatabaseConnection.getInstance(), "Starters", Diet.VEGETARIAN, this.allergenArray);
      this.starterList.getItems().clear();
      for (MenuItem v : vegStarters) {
        this.starterList.getItems().add(v.getName() + " - £" + v.getprice());
      }
      ArrayList<MenuItem> vegMain =
          DatabaseController.getMenuItemsFiltered(DatabaseConnection.getInstance(), "Main", Diet.VEGETARIAN, this.allergenArray);
      this.mainList.getItems().clear();
      for (MenuItem v : vegMain) {
        this.mainList.getItems().add(v.getName() + " - £" + v.getprice());
      }
      ArrayList<MenuItem> vegDessert = DatabaseController
          .getMenuItemsFiltered(DatabaseConnection.getInstance(), "Dessert", Diet.VEGETARIAN, this.allergenArray);
      this.dessertList.getItems().clear();
      for (MenuItem v : vegDessert) {
        this.dessertList.getItems().add(v.getName() + " - £" + v.getprice());
      }
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }



  /**
   * This filters the menu to show only vegan options.
   */
  public void veganMenu() {
    try {
      ArrayList<MenuItem> veganStarter =
          DatabaseController.getMenuItemsFiltered(DatabaseConnection.getInstance(), "Starters", Diet.VEGAN, this.allergenArray);
      for (MenuItem v : veganStarter) {
        this.starterList.getItems().add(v.getName() + " - £" + v.getprice());
      }
      ArrayList<MenuItem> veganMain =
          DatabaseController.getMenuItemsFiltered(DatabaseConnection.getInstance(), "Main", Diet.VEGAN, this.allergenArray);
      for (MenuItem v : veganMain) {
        this.mainList.getItems().add(v.getName() + " - £" + v.getprice());
      }
      ArrayList<MenuItem> veganDessert =
          DatabaseController.getMenuItemsFiltered(DatabaseConnection.getInstance(), "Dessert", Diet.VEGAN, this.allergenArray);
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
      allergenArray[0] = "'dairy'";
    } else {
      allergenArray[0] = "'NOPE'";
    }

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

  @FXML
  void peanutFilter(ActionEvent event) {
    if (peanut.isSelected()) {
      allergenArray[1] = "'nut'";
    } else {
      allergenArray[1] = "'NOPE'";
    }

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

  @FXML
  void shellfishFilter(ActionEvent event) {
    if (shellfish.isSelected()) {
      allergenArray[2] = "'shellfish'";
    } else {
      allergenArray[2] = "'NOPE'";
    }

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

  @FXML
  void wheatFilter(ActionEvent event) {
    if (wheat.isSelected()) {
      allergenArray[3] = "'wheat'";
    } else {
      allergenArray[3] = "'NOPE'";
    }

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
        count++;
        items.setText("(" + count + ")");
      }

      if (mainList.getSelectionModel().getSelectedItem() != null) {
        basketList.getItems().add(mainList.getSelectionModel().getSelectedItem());
        count++;
        items.setText("(" + count + ")");
      }

      if (dessertList.getSelectionModel().getSelectedItem() != null) {
        basketList.getItems().add(dessertList.getSelectionModel().getSelectedItem());
        count++;
        items.setText("(" + count + ")");
      }



    }

    starterList.getSelectionModel().clearSelection();
    mainList.getSelectionModel().clearSelection();
    dessertList.getSelectionModel().clearSelection();
    quantity.setValue(1);
    // wordRepeated();
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
        System.out.println("Problem using the database: " + e + "\n At line 360.");
      }
    }
  }

  /**
   * Double click on item to view picture and information.
   *
   * @param event event caused by double click.
   * @throws IOException IO exception
   * @throws SQLException SQL exception
   */
  @FXML
  void viewItem(MouseEvent event) throws IOException, SQLException {
    String[] selectedItem;
    String item = " ";
    if (event.getClickCount() == 2) {
      if (!starterList.getSelectionModel().isEmpty()) {
        selectedItem = starterList.getSelectionModel().getSelectedItem().split(" -");
        item = selectedItem[0];
        MenuScreenController.itemName = item;
      }
      if (!mainList.getSelectionModel().isEmpty()) {
        selectedItem = mainList.getSelectionModel().getSelectedItem().split(" -");
        item = selectedItem[0];
        MenuScreenController.itemName = item;
      }
      if (!dessertList.getSelectionModel().isEmpty()) {
        selectedItem = dessertList.getSelectionModel().getSelectedItem().split(" -");
        item = selectedItem[0];
        MenuScreenController.itemName = item;
      }

      starterList.getSelectionModel().clearSelection();
      mainList.getSelectionModel().clearSelection();
      dessertList.getSelectionModel().clearSelection();

      System.out.println(item);


      Parent root = FXMLLoader.load(getClass().getResource(Main.nutrientsScreenFile));
      Scene scene = new Scene(root);

      Stage stage = new Stage();
      stage.setScene(scene);
      stage.show();
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
    items.setText("(0)");

    File file = new File("src/main/resources/uk/ac/rhul/rms/media/menu screen.png");
    Image image = new Image(file.toURI().toString());
    BackgroundImage backgroundImage =
        new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT, new BackgroundSize(1, 1, true, true, false, false));
    Background background = new Background(backgroundImage);
    pane.setBackground(background);
  }
}
