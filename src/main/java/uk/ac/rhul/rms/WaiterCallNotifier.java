package uk.ac.rhul.rms;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class WaiterCallNotifier implements Runnable{

    @FXML
    private ListView<String> waiterCalls;

    public void setNotificationArea(ListView<String> waiterCalls) {
        this.waiterCalls = waiterCalls;
    }

    @Override
    public void run() {
        for (int i = 0; i == i; i++) {
            this.waiterCalls.getItems().add(Integer.toString(i));
        }
    }
}
