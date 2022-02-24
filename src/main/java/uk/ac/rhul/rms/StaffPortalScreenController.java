package uk.ac.rhul.rms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StaffPortalScreenController implements ControlledScreen, Initializable {

    private ScreensController screensController;

    /**
     * Confirms an order by inserting it into the database.
     *
     * @param order The confirmed order to be inserted into the database.
     */
    private void confirmOrder(ConfirmedOrder order) {
        Connection connection = DatabaseConnection.getInstance();
        DatabaseController.confirmOrder(connection, order);
    }

    @FXML
    private Button backBtn;

    @FXML
    private ListView<String> pendingOrdersList;

    @FXML
    private ListView<String> claimedOrderList;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screensController = screenParent;
    }

    @FXML
    void backBtnPressed(ActionEvent event) {
        this.screensController.setScreen(Main.loginScreenID);
    }

    @FXML
    void claimBtnPressed(ActionEvent event) {

    }

    @FXML
    public void loadOrders() throws SQLException, InvalidMenuIdException {
        Connection connection = DatabaseConnection.getInstance();
        ArrayList<Order> orders = DatabaseController.getOrders(connection);

        for(Order order: orders){
            this.pendingOrdersList.getItems().add(order.toString());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadOrders();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvalidMenuIdException e) {
            e.printStackTrace();
        }
    }
}
