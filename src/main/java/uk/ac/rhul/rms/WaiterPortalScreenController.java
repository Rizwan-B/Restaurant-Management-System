package uk.ac.rhul.rms;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.application.Platform;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WaiterPortalScreenController implements ControlledScreen, Initializable {

    private ScreensController screensController;

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

    private int lastMaxCallId = -1;

    @FXML
    private ListView<String> waiterCalls;

    private int newRecordLength = 0;


    @FXML
    void changeMenuPressed(ActionEvent event) {
        this.screensController.loadScreen(Main.changeMenuScreenID, Main.changeMenuScreenFile);
        this.screensController.setScreen(Main.changeMenuScreenID);
    }

    @FXML
    void manageOrderBtnPressed(ActionEvent event) {
        this.screensController.loadScreen(Main.manageOrderScreenID, Main.manageOrderScreenFile);
        this.screensController.setScreen(Main.manageOrderScreenID);
    }

    private void addToList(String tableNo) {
        this.waiterCalls.getItems().add(tableNo);
    }

    void makeWaiterFree(){
        try {
            DatabaseConnection.getInstance().createStatement().execute("UPDATE user_table set busy=0 WHERE user_id=" + Main.currentLoggedInUser);
            this.waiterStatus.setText("free");
            this.waiterStatusBtn.setText("BUSY");
            this.printAllWaiterCalls();
            if (Main.workerThread == null || !Main.workerThread.isAlive()) {

                Task<Void> notifierTask = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        while (true) {
                            updateUI();
                            Thread.sleep(5000);
                        }
                    }
                    private void updateUI() {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                printAllWaiterCalls();
                            }
                        });
                    }
                };

                Main.workerThread = new Thread((notifierTask));
                Main.workerThread.start();
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    void makeWaiterBusy() {
        try {
            DatabaseConnection.getInstance().createStatement().execute("UPDATE user_table set busy=1 WHERE user_id=" + Main.currentLoggedInUser);
            this.waiterStatus.setText("busy");
            this.waiterStatusBtn.setText("FREE");
            this.waiterCalls.getItems().clear();
            if (Main.workerThread != null && Main.workerThread.isAlive()) {
                Main.workerThread.interrupt();
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    private void notificationCheck() {
        if (this.newRecordLength > Main.oldRecordLength) {
            Notifications notification = Notifications.create();
            notification.title("New Notification");
            notification.text("More tables need to be served. Visit Waiter Portal for more details.");
            notification.darkStyle();
            notification.position(Pos.BASELINE_CENTER);
            notification.hideAfter(Duration.seconds(5));
            notification.onAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    screensController.setScreen(Main.waiterPortalScreenID);
                }
            });
            notification.showInformation();
            Main.oldRecordLength = this.newRecordLength;
        }
    }

    private void printAllWaiterCalls() {
        this.waiterCalls.getItems().clear();
        this.newRecordLength = 0;
        ResultSet initialRecords = DatabaseController.executeQuery(DatabaseConnection.getInstance(),
                "SELECT call_id, table_no FROM waiter_call WHERE served=0");
        try {
            while(initialRecords.next()) {
                if (initialRecords.getInt(1) > this.lastMaxCallId) {
                    this.lastMaxCallId = initialRecords.getInt(1);
                }
                addToList(initialRecords.getString(2));
                this.newRecordLength++;
            }
            this.notificationCheck();
        } catch (SQLException | NullPointerException e) {
            System.out.println(e.toString());
        }
    }




    @FXML
    void startScreen(ActionEvent event) {
        this.screensController.setScreen(Main.startScreenID);

    }



    public void fillUserData(ResultSet data) {
        try {
            while (data.next()) {
                this.waiterName.setText(data.getString(1));
                this.waiterId.setText(Integer.toString(Main.currentLoggedInUser));
                if (data.getInt("busy") == 0) { // is free.
                    this.waiterStatus.setText("free");
                    this.waiterStatusBtn.setText("BUSY");
                } else {
                    this.waiterStatus.setText("busy");
                    this.waiterStatusBtn.setText("FREE");
                }
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println(e.toString());
        }
    }


    @FXML
    void waiterStatusBtnPressed(ActionEvent event) {
        if (waiterStatus.getText().equals("busy")) { // waiter wants to be free.
            System.out.println("waiter changed to free.");
            makeWaiterFree();
        } else { // waiter wants to be busy.
            System.out.println("waiter changed to busy.");
            makeWaiterBusy();
        }
    }

    @FXML
    void acceptCall(ActionEvent event) {
        try {
            ObservableList selectedItem = this.waiterCalls.getSelectionModel().getSelectedIndices();
            int callId = Integer.parseInt(this.waiterCalls.getItems().get(ManageOrderScreenController.convertToInt(selectedItem.toString())));

            DatabaseConnection.getInstance().createStatement().execute("UPDATE waiter_call set served=1, waiter_id=" + Main.currentLoggedInUser + " WHERE table_no="+callId);
            makeWaiterBusy();
        } catch (SQLException | NumberFormatException e) {
            System.out.println(e.toString());
        }
    }

    @FXML
    void logOut(ActionEvent event) {
        this.makeWaiterBusy();
        Main.sessionId = null;
        Main.currentLoggedInUser = 0;
        this.screensController.setScreen(Main.startScreenID);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.fillUserData(DatabaseController.executeQuery(DatabaseConnection.getInstance(), "SELECT user_name, busy FROM user_table WHERE user_id=" + Main.currentLoggedInUser));

        if (this.waiterStatus.getText().equals("free")) {
            printAllWaiterCalls();
        }

    }
}






















