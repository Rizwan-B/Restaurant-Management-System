package uk.ac.rhul.rms;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.sql.Connection;

public class KitchenStaffScreenController {

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
    private ListView<?> pendingOrders;

    @FXML
    private ListView<?> workingOn;

}
