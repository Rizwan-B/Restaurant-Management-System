package uk.ac.rhul.rms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The screen controller for the reservation screen implementing the ControlledScreen Interface.
 *
 * @author Jacob Artis
 * @author Muqdas Sheikh
 *
 */
public class TrackOrderScreenController implements ControlledScreen, Initializable {

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
    private ListView<String> trackOrder;

    @FXML
    private Pane pane;


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

    @FXML
    public void refreshButton() {
        String status = " ";

        ResultSet payments = DatabaseController.executeQuery(DatabaseConnection.getInstance(),
                "SELECT * FROM payments;");
        try {
            while (payments.next()) {
                ResultSet orders = DatabaseController.executeQuery(DatabaseConnection.getInstance(),
                        "SELECT status FROM orders_table WHERE order_id = '" + payments.getInt("order_id") + "';");
                if (orders.getString("status").equals("0")) {
                    status = "Working On";
                }
                if (orders.getString("status").equals("1")) {
                    status = "Cancelled";
                }
                if (orders.getString("status").equals("2")) {
                    status = "On the Way!";
                }
                trackOrder.getItems().add(payments.getString(2) + " | " + payments.getString(3)
                        + " | " + payments.getString(4) + " | " + status);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.connection = DatabaseConnection.getInstance();
        tableSeat();
        refreshButton();

        File file = new File("src/main/resources/uk/ac/rhul/rms/media/track order screen.png");
        javafx.scene.image.Image image = new Image(file.toURI().toString());
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1,1, true, true, false,false));
        Background background = new Background(backgroundImage);
        pane.setBackground(background);
    }
}
