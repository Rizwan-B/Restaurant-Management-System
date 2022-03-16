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
import java.util.Queue;
import java.util.ResourceBundle;

/**
 * The Screen Controller for the staff landing.
 *
 * @author Lucas Kimber
 */
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

    /**
     * Below are button and list views for staff portal.
     */
    @FXML
    private ListView<String> pendingOrdersList;

    @FXML
    private ListView<String> claimedOrderList;

    /**
     *
     * @param screenParent The parent ScreensController of 'this' screen.
     */
    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screensController = screenParent;
    }

    /**
     * once back button pressed this loads login screen.
     * @param event load login screen
     */
    @FXML
    void backBtnPressed(ActionEvent event) {
        this.screensController.setScreen(Main.loginScreenID);
    }

    /**
     * This method is for kitchen staff to choose incoming orders.
     * @param event adds claimed order to working on
     * @throws InvalidMenuIdException
     * @throws SQLException
     */
    @FXML
    void claimBtnPressed(ActionEvent event) throws InvalidMenuIdException, SQLException {
        Connection connection = DatabaseConnection.getInstance();
        String selectedOrder = this.pendingOrdersList.getSelectionModel().getSelectedItems().toString();

        int index = this.pendingOrdersList.getSelectionModel().getSelectedIndex();
        this.pendingOrdersList.getItems().remove(index);

        String[] splitId = selectedOrder.split(" - ");
        Order order = DatabaseController.getOrder(connection, Integer.valueOf(splitId[0].substring(1)));

        ConfirmedOrder confirmedOrder = new ConfirmedOrder(order, Main.currentLoggedInUser);
        DatabaseController.confirmOrder(connection, confirmedOrder);

        this.claimedOrderList.getItems().add(order.toString());
    }

    /**
     * This method adds orders into pending orders from database.
     * @throws SQLException
     * @throws InvalidMenuIdException
     */
    @FXML
    public void loadOrders() throws SQLException, InvalidMenuIdException {
        Connection connection = DatabaseConnection.getInstance();
        ArrayList<Order> orders = DatabaseController.getOrders(connection);

        for(Order order: orders){
            this.pendingOrdersList.getItems().add(order.toString());
        }
    }

    /**
     * This method adds claimed orders into working on list.
     * @throws SQLException
     * @throws InvalidMenuIdException
     */
    @FXML
    public void loadWorkingOn() throws SQLException, InvalidMenuIdException {
        Connection connection = DatabaseConnection.getInstance();
        ArrayList<ConfirmedOrder> workingOn =
            DatabaseController.getWorkingOnOrders(connection, Main.currentLoggedInUser);

        for (ConfirmedOrder order : workingOn) {
            this.claimedOrderList.getItems().add(order.toString());
        }
    }

    @FXML
    void startScreen(ActionEvent event) {
        this.screensController.setScreen(Main.startScreenID);

    }

    @FXML
    void logOut(ActionEvent event) {
        Main.sessionId = null;
        try {
            DatabaseConnection.getInstance().createStatement().execute("UPDATE user_table set session_id='NULL' " +
                "WHERE user_id=" + Main.currentLoggedInUser);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        Main.currentLoggedInUser = 0;
        this.screensController.setScreen(Main.loginScreenID);
    }

    @FXML
    void markCompletedBtnPressed(ActionEvent event) throws InvalidMenuIdException, SQLException {
        Connection connection = DatabaseConnection.getInstance();

        String selectedOrder = this.claimedOrderList.getSelectionModel().getSelectedItems().toString();

        int index = this.claimedOrderList.getSelectionModel().getSelectedIndex();
        this.claimedOrderList.getItems().remove(index);

        String[] splitId = selectedOrder.split(" - ");
        Order order = DatabaseController.getOrder(connection, Integer.valueOf(splitId[0].substring(1)));

        DatabaseController.markOrderComplete(connection, order);
    }

    @FXML
    void refreshBtnPressed(ActionEvent event) {
        try {
            loadOrders();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvalidMenuIdException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadOrders();
            loadWorkingOn();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvalidMenuIdException e) {
            e.printStackTrace();
        }
    }
}
