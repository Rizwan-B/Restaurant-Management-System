package uk.ac.rhul.rms;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

/**
 * The screen controller for the managing orders screen implementing the ControlledScreen Interface.
 *
 * @author Virginia, Ahmed, Muqdas
 */

public class ManageOrderScreenController implements ControlledScreen, Initializable {

  ScreensController screensController;

  /**
   * Sets parent screen.
   *
   * @param screenParent The parent ScreensController of 'this' screen.
   */
  @Override
  public void setScreenParent(ScreensController screenParent) {
    this.screensController = screenParent;
  }

  /**
   * Below are buttons, list views, text, drop down menus and text fields for manage order screen.
   */
  @FXML
  private Button backBtn;

  @FXML
  private Button cancelBtn;

  @FXML
  private Button orderDeliveredBtn;

  @FXML
  private ListView<String> orderList;

  @FXML
  private ListView<String> displayPayment;

  @FXML
  private Text paymentStatus;

  @FXML
  private ComboBox<String> changeStatus;

  @FXML
  private Text total;

  @FXML
  private TextField totalField;

  @FXML
  private Text display;

  @FXML
  private Text text;

  /**
   * Once back button is clicked waiter portal screen is loaded.
   *
   * @param event waiter portal screen is loaded.
   */
  @FXML
  void backBtnPressed(ActionEvent event) {
    this.screensController.setScreen(Main.waiterPortalScreenID);
    this.screensController.unloadScreen(Main.manageOrderScreenID);
  }

  /**
   * Converts String into an int.
   *
   * @param arrayString Given string.
   * @return the int.
   */
  public static int convertToInt(String arrayString) {
    StringBuilder sb = new StringBuilder(arrayString);
    sb.deleteCharAt(0);
    sb.deleteCharAt(arrayString.length() - 2);
    return Integer.parseInt(sb.toString());
  }

  /**
   * This cancels the order after asking for confirmation from the waiter.
   */
  @FXML
  void cancelBtnPressed(ActionEvent event) {

    Alert alert = new Alert(Alert.AlertType.NONE, "Are you sure you want to cancel this order?",
        ButtonType.YES, ButtonType.NO);
    alert.showAndWait();

    if (alert.getResult() == ButtonType.YES) {

      try {
        ObservableList selectedItem = orderList.getSelectionModel().getSelectedIndices();
        int orderId = Integer.parseInt(
            orderList.getItems().get(convertToInt(selectedItem.toString())).split("|")[0].trim());

        DatabaseController.cancelOrder(DatabaseConnection.getInstance(), orderId);
        this.orderList.getItems().clear();
        this.displayItems();
      } catch (Exception e) {
        System.out.println("ERROR: SQL connection error, or you did not select an item to delete.");
      }
    }
  }

  /**
   * This method displays the orders item in the basket.
   */
  public void displayItems() {
    ResultSet allOrders = DatabaseController.executeQuery(DatabaseConnection.getInstance(),
        "SELECT * FROM orders_table");
    try {
      while (allOrders.next()) {
        if (allOrders.getString(4).equals("0")) {
          orderList.getItems().add(allOrders.getString(1) + " | " + allOrders.getString(2) + " | "
              + allOrders.getString(4));
        }
      }
    } catch (SQLException e) {
      System.out.println("SQL exception.");
    }
  }

  /**
   * This method gets the order items from the database which are not paid yet.
   */
  public void unPaidOrder() {
    ResultSet unPaidOrders = DatabaseController.executeQuery(DatabaseConnection.getInstance(),
        "SELECT * FROM payments;");
    try {
      while (unPaidOrders.next()) {
        displayPayment.getItems().add(unPaidOrders.getString(1) + " | " + unPaidOrders.getString(2)
            + " | " + unPaidOrders.getString(3) + " | " + unPaidOrders.getString(4) + " | " + unPaidOrders.getString(5));

      }
    } catch (SQLException e) {
      System.out.println("SQL exception.");
    }
  }

  @FXML
  void paidOrder() {
    int index_main = this.displayPayment.getSelectionModel().getSelectedIndex();
    String selected_item = this.displayPayment.getSelectionModel().getSelectedItem();
    String[] split = selected_item.split(" | ");
    String order_id = split[2];
    System.out.println(order_id);


    if (index_main >= 0) {
      this.displayPayment.getItems().remove(index_main);
      try {
        DatabaseConnection.getInstance().createStatement()
            .execute("DELETE FROM payments WHERE order_id = '" + order_id + "';");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }


  }


  /**
   * This method is to confirm that the order has been delivered and removes them from order list.
   *
   * @param event removes thr order from orderlist
   */
  @FXML
  void orderDeliveredBtnPressed(ActionEvent event) {
    System.out.println("Order delivered.");

    // code below for functionality of orders completed being removed from order list.

    int index_main = this.orderList.getSelectionModel().getSelectedIndex();
    String selected_item = this.orderList.getSelectionModel().getSelectedItem();
    String[] split = selected_item.split(" | ");
    String order_id = split[0];

    int orderID = Integer.parseInt(order_id);


    if (index_main >= 0) {
      this.orderList.getItems().remove(index_main);
      try {
        DatabaseConnection.getInstance().createStatement()
            .execute("DELETE FROM orders_table WHERE order_id = '" + orderID + "';");
        // DatabaseConnection.getInstance().createStatement().execute("INSERT INTO = '"+orderID+
        // "';");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * This method is to initialize unpaid order and display items.
   *
   * @param location Location of URL.
   * @param resources Resources.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.displayItems();
    this.unPaidOrder();
  }
}
