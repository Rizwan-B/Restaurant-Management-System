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

    @FXML
    private ComboBox<String> timePicker;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Text tNumber;

    @FXML
    private TextField tableInput;

    @FXML
    private Text fullName;

    @FXML
    private TextField nameInput;

    @FXML
    private Button reserve;


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
        ObservableList<String> list = FXCollections.observableArrayList("12:00",
                                                                                "1:00", "2:00", "3:00","4:00","5:00",
                                                                                "6:00", "7:00","8:00",
                                                                                "9:00", "10:00");
        timePicker.setItems(list);


    }
}
