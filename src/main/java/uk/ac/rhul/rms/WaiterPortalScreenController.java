package uk.ac.rhul.rms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WaiterPortalScreenController implements ControlledScreen, Initializable {

    private ScreensController screensController;
    private boolean busy = false;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screensController = screenParent;
    }

    @FXML
    private Label waiterName;

    @FXML
    private Label waiterId;

    @FXML
    private Button waiterStatusBtn;

    @FXML
    private Label waiterStatus;

    @FXML
    private Button backBtn;

    @FXML
    private Button cancelOrderBtn;

    @FXML
    private ListView<String> waiterCalls;

    @FXML
    void backBtnPressed(ActionEvent event) {
        this.screensController.setScreen(Main.loginScreenID);
        this.screensController.unloadScreen(Main.waiterPortalScreenID);
    }

    @FXML
    void manageOrderBtnPressed(ActionEvent event) {
        this.screensController.loadScreen(Main.manageOrderScreenID, Main.manageOrderScreenFile);
        this.screensController.setScreen(Main.manageOrderScreenID);
    }

    @FXML
    void waiterStatusBtnPressed(ActionEvent event) {
        if (waiterStatusBtn.getText().equals("FREE")){ // make the waiter free.
            waiterStatusBtn.setText("BUSY");
            this.waiterStatus.setText("free");
            this.busy = false;
            ResultSet waiterCallRecords = DatabaseController.executeQuery(DatabaseConnection.getInstance(), "SELECT * FROM waiter_call");
            try {
                while(waiterCallRecords.next()) {
                    this.waiterCalls.getItems().add(waiterCallRecords.getString(2));
                }
            } catch (SQLException e) {
                System.out.println(e);
            }

        } else { // make the waiter busy.
            waiterStatusBtn.setText("FREE");
            this.waiterStatus.setText("busy");
            this.busy = true;
            this.waiterCalls.getItems().removeAll();
        }

        // 3) change waiterStatus label to green.
        // 4) if waiter is free -> show all the waiter calls in list view.
        System.out.println("free pressed.");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ResultSet waiterData = DatabaseController.executeQuery(DatabaseConnection.getInstance(), "SELECT user_id, user_name FROM user_table WHERE user_id=" + Main.currentLoggedInUser);
        try {
            while(waiterData.next()) {
                this.waiterName.setText(waiterData.getString(2));
                this.waiterId.setText(waiterData.getString(1));
                this.waiterStatus.setText("busy");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
