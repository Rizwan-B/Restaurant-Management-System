package uk.ac.rhul.rms;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The screen controller for the managing orders screen implementing the ControlledScreen Interface.
 *
 * @author Virginia, Ahmed
 */

public class ManageOrderScreenController implements ControlledScreen, Initializable {

    ScreensController screensController;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screensController = screenParent;
    }

    @FXML
    private Button backBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button orderDeliveredBtn;

    @FXML
    private ListView<String> orderList;

    @FXML
    void backBtnPressed(ActionEvent event) {
        this.screensController.setScreen(Main.waiterPortalScreenID);
        this.screensController.unloadScreen(Main.manageOrderScreenID);
    }

    public static int convertToInt(String arrayString) {
        StringBuilder sb = new StringBuilder(arrayString);
        sb.deleteCharAt(0);
        sb.deleteCharAt(arrayString.length() - 2);

        int result = Integer.parseInt(sb.toString());
        return result;
    }

    /**
     * This cancels the order after asking for confirmation from the waiter.
     */
    @FXML
    void cancelBtnPressed(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.NONE, "Are you sure you want to cancel this order?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            try {
                ObservableList selectedItem = orderList.getSelectionModel().getSelectedIndices();
                int orderId = Integer.parseInt(orderList.getItems().get(convertToInt(selectedItem.toString())).split("|")[0].trim());

                DatabaseController.cancelOrder(DatabaseConnection.getInstance(), orderId);
                this.orderList.getItems().clear();
                this.displayItems();
            } catch (Exception e) {
                System.out.println("ERROR: SQL connection error, or you did not select an item to delete.");
            }
        }

    }

    public void displayItems() {
        ResultSet allOrders = DatabaseController.executeQuery(DatabaseConnection.getInstance(), "SELECT * FROM orders_table");
        try {
            while (allOrders.next()) {
                if (allOrders.getString(4).equals("0")) {
                    orderList.getItems().add(allOrders.getString(1) + " | " + allOrders.getString(2) + " | " + allOrders.getString(3));
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL exception.");
        }
    }

    @FXML
    void orderDeliveredBtnPressed(ActionEvent event) {
        System.out.println("Order delivered.");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.displayItems();
    }
}
