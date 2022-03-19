package uk.ac.rhul.rms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.apache.commons.lang3.ArrayUtils;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.*;

/**
 * The screen controller for the basket screen implementing the ControlledScreen Interface.
 *
 * @author Jacob Artis
 * @author Muqdas
 *
 */
public class BasketScreenController implements ControlledScreen, Initializable {

  ScreensController screensController;
  private Connection connection;
  private double sum = 0;

  @Override
  public void setScreenParent(ScreensController screenParent) {
    this.screensController = screenParent;
  }

  /**
   * Below are buttons, TextFields, dropdown boxes which have different actions. Some are fields
   * which store certain numbers and text. Some are buttons which have certain actions.
   */
  @FXML
  private Button backBtn;

  @FXML
  private Text cardNo;

  @FXML
  private TextField cardNumber;

  @FXML
  private Text cvc;

  @FXML
  private TextField cvcBox;

  @FXML
  private Text dash;

  @FXML
  private ComboBox<String> date;

  @FXML
  private CheckBox electronicPayment;

  @FXML
  private Text expiryDate;

  @FXML
  private TextField holderName;

  @FXML
  private ListView<String> orderItems;

  @FXML
  private Text payment;

  @FXML
  private Text paymentMethod;

  @FXML
  private Button placeOrder;

  @FXML
  private TextField tableNo;

  @FXML
  private CheckBox tillsCheckBox;

  @FXML
  private Text userName;

  @FXML
  private ComboBox<String> year;

  @FXML
  private Text responseText;


  @FXML
  private Text total;

  @FXML
  private Text totalPrice;

  @FXML
  private Pane pane;

  private ArrayList<String> list = new ArrayList<>();
  public String[] items;

  {
    try {
      items = DatabaseController.getTempOrder(DatabaseConnection.getInstance()).split("-");
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (InvalidMenuIdException e) {
      e.printStackTrace();
    }
  }

  /**
   * Below is back button action which is to load Menu screen when clicked.
   *
   * @param event upon clicking back button menu screen is loaded.
   */
  @FXML
  void backBtnPressed(ActionEvent event) {
    this.screensController.loadScreen(Main.menuScreenID, Main.menuScreenFile);
    this.screensController.setScreen(Main.menuScreenID);
  }
  /**
   * This method is to remove items from basket.
   *
   * @param event remove items from basket.
   */
  @FXML
  void remove(ActionEvent event) {
    sum = 0;
    String item = orderItems.getSelectionModel().getSelectedItem();
    String[] item_split = item.split(" x");
    orderItems.getItems().remove(item);
    orderItems.getSelectionModel().clearSelection();

      for(int i=0 ; i<items.length; i=i+2) {

        if (items[i].equals(item_split[0])) {
         items[i] = "null";
         items[i+1]= "£ 0";
        }

      }

    for (int i = 1; i < items.length; i = i + 2) {

      String str = items[i];
      System.out.println(str);
      String price = str.replaceAll("[^0-9]", "");
      sum += Double.parseDouble(price);

    }
    totalPrice.setText("£ " + sum);



  }

  /**
   * This method stores payment information which is table number, card no, holder name and cvc.
   * once the user selects electronic payment,user has to enter payment details. Those details are
   * then processed if they are incorrect then it would print incorrect details. if the details are
   * correct, payment complete message will be printed. This method also allows user to pay using
   * their table number when tillCheckBox is selected. This method assures that payment has been
   * successful with paymentStatus field.
   *
   * @param event electronic payment is selected
   */
  @FXML
  void getPayment(ActionEvent event) throws SQLException, InvalidMenuIdException {
    int order_id = DatabaseController.nextInt(DatabaseConnection.getInstance());
    makeOrder(order_id);
    String tableNumber = tableNo.getText();
    String card_no = cardNumber.getText();
    String holder_no = holderName.getText();
    String cvc = cvcBox.getText();
    int table_Number = Integer.parseInt(tableNumber);

    if (electronicPayment.isSelected()) {

      if (tableNumber.isEmpty() || card_no.isEmpty() || holder_no.isEmpty() || cvc.isEmpty()
          || date.getSelectionModel().isEmpty() || year.getSelectionModel().isEmpty()) {
        Alert alert1 = new Alert(Alert.AlertType.NONE, "Enter Missing Information", ButtonType.OK);
        alert1.showAndWait();

      } else {

        if (!(card_no.length() == 16) || (!(cvc.length() == 3))) {
          Alert alert =
              new Alert(Alert.AlertType.NONE, "Incorrect Card Information!", ButtonType.OK);
          alert.showAndWait();

        } else {

          long now = System.currentTimeMillis();
          Time time= new Time(now);
          System.out.println(time);
          String t = time.toString();
          String paymentStatus = "paid";
          String table = tableNo.getText();
          int intTable = Integer.parseInt(table);
          String x = totalPrice.getText();
          Alert order = new Alert(Alert.AlertType.NONE,
                  "Your Order Number is " + order_id, ButtonType.OK);
          order.showAndWait();
          Alert alert = new Alert(Alert.AlertType.NONE, "Payment complete, Thank You for visiting!",
                  ButtonType.OK);
          alert.showAndWait();
          DatabaseConnection.getInstance().createStatement()
                  .execute("INSERT INTO payments VALUES('" + paymentStatus + "','"+order_id
                          + "', '" + intTable +"', '"+x+"', '"+ t+ "'); ");

          String allItems = this.orderItems.getItems().toString();
          System.out.println(allItems);
          DatabaseConnection.getInstance().createStatement()
                  .execute("INSERT INTO orders_table(order_id, table_no,status) VALUES('" + order_id
                          + "', '" + intTable +"', '0'); ");
          this.screensController.setScreen(Main.startScreenID);
        }
      }
    } else if (tillsCheckBox.isSelected()) {

      if (tableNumber.isEmpty()) {
        Alert alert1 = new Alert(Alert.AlertType.NONE, "Enter table Number", ButtonType.OK);
        alert1.showAndWait();

      } else {
        if(!(table_Number>0 && table_Number<14)){
          Alert alert = new Alert(Alert.AlertType.NONE, "Wrong Table Number!!", ButtonType.OK);
          alert.showAndWait();
        }else{
          long now = System.currentTimeMillis();
          Time time= new Time(now);
          System.out.println(time);
          String t = time.toString();
          String paymentStatus = "unpaid";
          String table = tableNo.getText().toString();
          int intTable = Integer.parseInt(table);
          String x = totalPrice.getText();
          Alert order = new Alert(Alert.AlertType.NONE,
                  "Your Order Number is " + order_id, ButtonType.OK);
          order.showAndWait();
          Alert alert = new Alert(Alert.AlertType.NONE,
                  "Please head to the tills, Thank you for visiting!", ButtonType.OK);
          alert.showAndWait();
          DatabaseConnection.getInstance().createStatement()
                  .execute("INSERT INTO payments VALUES('" + paymentStatus + "','"+order_id
                          + "', '" + intTable +"', '"+x+"', '"+ t+ "'); ");
          DatabaseConnection.getInstance().createStatement()
                  .execute("INSERT INTO orders_table(order_id, table_no,status) VALUES('" + order_id
                          + "', '" + intTable +"', '0'); ");

          this.screensController.setScreen(Main.startScreenID);
        }


      }

    } else {
      Alert alert1 = new Alert(Alert.AlertType.NONE, "Select a payment option.", ButtonType.OK);
      alert1.showAndWait();
    }

  }


  /**
   * This method sorts tills when tillsCheckBox is selected electronic payment details will be
   * hidden.
   */
  @FXML
  void handleTills() {
    if (tillsCheckBox.isSelected()) {
      electronicPayment.setSelected(false);
      cardNumber.setDisable(true);
      holderName.setDisable(true);
      date.setDisable(true);
      year.setDisable(true);
      cvcBox.setDisable(true);
    }
  }

  /**
   * This method will show all the electronic payment details when electronic payment is selected.
   *
   */
  @FXML
  void handleElectronic() {
    if (electronicPayment.isSelected()) {
      tillsCheckBox.setSelected(false);
      cardNumber.setDisable(false);
      holderName.setDisable(false);
      date.setDisable(false);
      year.setDisable(false);
      cvcBox.setDisable(false);
    }
  }

  @FXML
  void makeOrder(int order_id){
    String orderList = "";
    try {
      if (orderItems.getItems().size() != 0) {
        for (int i = 0; i < orderItems.getItems().size(); i++) {
          String itemName = orderItems.getItems().get(i);

          int itemId = DatabaseController.getItemId(DatabaseConnection.getInstance(),itemName.substring(0, itemName.length()-4));

          orderList += itemId + "-";

        }

        Order newOrder = new Order(order_id, Integer.parseInt(tableNo.getText()), orderList, false);
        DatabaseController.addOrder(DatabaseConnection.getInstance(),newOrder);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (InvalidMenuIdException e) {
      e.printStackTrace();
    }
  }

  /**
   * getItems method returns total price of the selected items once in the basket. A for loops
   * checks how many items are in the basket and calculates the total price accordingly.
   *
   * @throws SQLException SQL Exception.
   * @throws InvalidMenuIdException Invalid Menu ID given.
   */
  @FXML
  void getItems() throws SQLException, InvalidMenuIdException {

    for (int i = 0; i < items.length; i = i + 2) {
      list.add(items[i]);
    }

    wordRepeated();

    for (int i = 1; i < items.length; i = i + 2) {
      String str = items[i];
      String price = str.replaceAll("[^0-9]", "");
      sum += Double.parseDouble(price);
    }
    totalPrice.setText("£ " + sum);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.connection = DatabaseConnection.getInstance();

    ObservableList<String> list = FXCollections.observableArrayList("01", "02", "03", "04", "05",
        "06", "07", "08", "09", "10", "11", "12");
    date.setItems(list);
    ObservableList<String> listYear = FXCollections.observableArrayList("2022", "2023", "2024",
        "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035",
        "2036", "2037", "2038", "2039", "2040");
    year.setItems(listYear);

    File file = new File("src/main/resources/uk/ac/rhul/rms/media/payment screen.png");
    Image image = new Image(file.toURI().toString());
    BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1,1, true, true, false,false));
    Background background = new Background(backgroundImage);
    pane.setBackground(background);

    try {

      getItems();

    } catch (SQLException e) {
      e.printStackTrace();
    } catch (InvalidMenuIdException e) {
      e.printStackTrace();
    }
  }

  /**
   * Groups up the basket - rather than showing multiple items.
   */
  public void wordRepeated() {
    String item = " ";
    HashMap<String, Integer> map = new HashMap<String, Integer>();

    int occurrences = 0;


    for (String s : list) {
      occurrences = Collections.frequency(list, s);
      map.put(s, occurrences);
    }
    for (HashMap.Entry<String, Integer> entry : map.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      orderItems.getItems().add(key + " x" + value);
    }
  }
}
