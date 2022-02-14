package uk.ac.rhul.rms;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WaiterCallNotifier implements Runnable{

    @FXML
    private ListView<String> waiterCalls;

    public void setNotificationArea(ListView<String> waiterCalls) {
        this.waiterCalls = waiterCalls;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ResultSet notifications = DatabaseController.executeQuery(DatabaseConnection.getInstance(), "SELECT *, count(*) as record_count FROM waiter_call");
                if (notifications.getInt("record_count") > waiterCalls.getItems().size()) {
                    while (notifications.next()) {
                        waiterCalls.getItems().add(notifications.getString(1) + ", " + notifications.getString(2) + ", " + notifications.getString(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL exception");
        }

    }
}
