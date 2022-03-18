package uk.ac.rhul.rms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The screen controller for the reservation screen implementing the ControlledScreen Interface.
 *
 * @author Jacob Artis
 * @author Muqdas Sheikh
 *
 */
public class ReservationScreenController implements ControlledScreen, Initializable {

    ScreensController screensController;
    private Connection connection;

    /**
     *
     * @param screenParent The parent ScreensController of 'this' screen.
     */
    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screensController = screenParent;
    }

    /**
     * this takes you to start screen once back button pressed.
     * @param event load start screen
     */
    @FXML
    void backBtnPressed(ActionEvent event) {
        this.screensController.loadScreen(Main.startScreenID, Main.startScreenFile);
        this.screensController.setScreen(Main.startScreenID);
    }

    /**
     * Below are labels, ListViews, combox box,text field and label for reservation screen.
     */
    @FXML
    private Label seatNo;

    @FXML
    private Label tableNo;

    @FXML
    private ListView<Integer> seat_no;

    @FXML
    private ListView<Integer> table_no;

    @FXML
    private Text layout;

    @FXML
    private TextField nameInput;

    @FXML
    private Button reserve;

    @FXML
    private Text text;

    @FXML
    private ListView<?> trackOrder;


    public void tableSeat() {
        try {
            ArrayList<SeatNumber> st = DatabaseController.getSeatNumber(this.connection);
            for (SeatNumber v : st) {
                System.out.println(v.getTableNumber());
                this.table_no.getItems().add(v.getTableNumber());
            }
            for (SeatNumber v : st) {
                System.out.println(v.getSeatNumber());
                this.seat_no.getItems().add(v.getSeatNumber());
            }
        } catch(Exception e) {
            System.out.println("oops");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.connection = DatabaseConnection.getInstance();
        tableSeat();



    }
}
