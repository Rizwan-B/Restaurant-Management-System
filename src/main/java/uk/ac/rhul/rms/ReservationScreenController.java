package uk.ac.rhul.rms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * The screen controller for the reservation screen implementing the ControlledScreen Interface.
 *
 * @author Jacob Artis
 * @author Muqdas Sheikh
 *
 */
public class ReservationScreenController implements ControlledScreen {

    ScreensController screensController;
    private Connection connection;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screensController = screenParent;
    }

    @FXML
    void backBtnPressed(ActionEvent event) {
        this.screensController.loadScreen(Main.startScreenID, Main.startScreenFile);
        this.screensController.setScreen(Main.startScreenID);
    }
    @FXML
    private Label seatNo;

    @FXML
    private Label tableNo;
    @FXML
    private ListView<Integer> seat_no;

    @FXML
    private ListView<Integer> table_no;


    public void tableSeat() {
        try {
            ArrayList<SeatNumber> st = DatabaseController.getSeatNumber(this.connection);
            for (SeatNumber v : st) {
                System.out.println(v.getTableNumber());
               // this.tableNo.getItems().ass(v.getTableNumber());

            }
        } catch(Exception e) {
            System.out.println("oops");
        }
    }


}
