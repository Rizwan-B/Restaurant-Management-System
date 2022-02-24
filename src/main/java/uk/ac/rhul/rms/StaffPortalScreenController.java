package uk.ac.rhul.rms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;
import javafx.scene.control.ListView;

import java.sql.Connection;

public class StaffPortalScreenController implements ControlledScreen {

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
    private ListView<?> pendingOrdersList;

    @FXML
    private ListView<?> claimedOrderList;

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
}
