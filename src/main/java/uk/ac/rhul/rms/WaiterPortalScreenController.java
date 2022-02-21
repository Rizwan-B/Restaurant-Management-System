package uk.ac.rhul.rms;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.application.Platform;
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
                        int i = 0;
                        while (true) {
                            updateUI();
                            System.out.println("i: " + i);
                            Thread.sleep(2000);
                            i++;
                        }
                    }

                    private void updateUI() {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //Set your new values in your UI
                                //Call the method in your UI to update values.
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

    private void printAllWaiterCalls() {
        this.waiterCalls.getItems().clear();
        ResultSet initialRecords = DatabaseController.executeQuery(DatabaseConnection.getInstance(),
                "SELECT call_id, table_no FROM waiter_call WHERE served=0");
        try {
            while(initialRecords.next()) {
                if (initialRecords.getInt(1) > this.lastMaxCallId) {
                    this.lastMaxCallId = initialRecords.getInt(1);
                }
                addToList(initialRecords.getString(2));
            }
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
        // do this later.
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






















