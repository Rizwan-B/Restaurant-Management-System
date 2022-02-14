package uk.ac.rhul.rms;

import javafx.collections.ObservableList;
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
    private Button changeMenu;

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
        /*
            1) check if waiter is free...
            2) while waiter is free -> start a new thread that does a constant db lookup for updates in the waiter_call table.
            3) If the table is not being served -> show it on the screen for waiter to accept.
            4) update waiter_call table to say it is being served by this waiter.
         */
        if (this.waiterStatus.getText().equals("busy")) {
            this.waiterStatus.setText("free");
            this.waiterStatusBtn.setText("BUSY");
            try {
                DatabaseConnection.getInstance().createStatement().execute("UPDATE user_table set busy=0 WHERE user_id=" + Main.currentLoggedInUser);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                DatabaseConnection.getInstance().createStatement().execute("UPDATE user_table set busy=1 WHERE user_id=" + Main.currentLoggedInUser);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.waiterStatus.setText("busy");
            this.waiterStatusBtn.setText("FREE");
            this.waiterCalls.getItems().clear();
        }

        this.freeBusyRoutineCheck();
        System.out.println("button pressed.");

    }

    @FXML
    void acceptCall(ActionEvent event) {
        try {
            ObservableList selectedItem = waiterCalls.getSelectionModel().getSelectedIndices();
            int call_id = Integer.parseInt(waiterCalls.getItems().get(ManageOrderScreenController.convertToInt(selectedItem.toString())).split("|")[0].trim());
            System.out.println(call_id);
            DatabaseConnection.getInstance().createStatement().execute("UPDATE waiter_call set served=1 WHERE call_id=" + call_id);
            DatabaseConnection.getInstance().createStatement().execute("UPDATE user_table set busy=1 WHERE user_id=" + Main.currentLoggedInUser);
            this.waiterStatus.setText("busy");
            this.waiterStatusBtn.setText("FREE");
            this.freeBusyRoutineCheck();

        } catch (Exception e) {
            System.out.println("ERROR: SQL connection error, or you did not select an item to delete.");
        }
    }

    @FXML
    void logOut(ActionEvent event) {
        Main.sessionId = null;
        Main.currentLoggedInUser = 0;
        this.screensController.setScreen(Main.startScreenID);
    }

    void freeBusyRoutineCheck() {
        if (this.waiterStatus.getText().equals("busy")) {
            this.waiterCalls.getItems().clear();
        } else {
            ResultSet calls = DatabaseController.executeQuery(DatabaseConnection.getInstance(), "SELECT call_id, table_no FROM waiter_call WHERE served=0");
            try {
                while (calls.next()) {
                    this.waiterCalls.getItems().add(calls.getString(1) + " | " + calls.getString(2));
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ResultSet waiterData = DatabaseController.executeQuery(DatabaseConnection.getInstance(), "SELECT user_id, user_name, busy FROM user_table WHERE user_id=" + Main.currentLoggedInUser);
        try {
            while(waiterData.next()) {
                this.waiterName.setText(waiterData.getString(2));
                this.waiterId.setText(waiterData.getString(1));
                if (waiterData.getInt(3) == 1) {
                    System.out.println("waiter is busy.");
                    this.waiterStatus.setText("busy");
                    this.waiterStatusBtn.setText("FREE");
                } else {
                    System.out.println("waiter is free");
                    this.waiterStatus.setText("free");
                    this.waiterStatusBtn.setText("BUSY");
                }
                this.freeBusyRoutineCheck();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
