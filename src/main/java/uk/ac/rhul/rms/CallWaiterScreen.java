package uk.ac.rhul.rms;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;


/**
 * The screen controller for the call waiter screen interface.
 *
 * @author Rizwan Bagdadi
 *
 */
public class CallWaiterScreen implements ControlledScreen {


    private ScreensController screensController;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screensController = screenParent;
    }

    /**
     * Below are releveant text fields , buttons and texts for the call waiter screen.
     */
    @FXML
    private TextField tableNumberField;

    @FXML
    private Button callWaiterBtn;

    @FXML
    private Button run;

    @FXML
    private Text textConfirm;

    /**
     * In the method below once call waiter button is pressed, user enters his table number and the waiter is alerted.
     * Message to user is also given that waiter is on its way.
     * @param event
     */
    @FXML
    void callWaiterBtnPressed(ActionEvent event) {
        try {
            String tableNumberString = this.tableNumberField.getText();
            if (tableNumberString.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.NONE, "Please enter your table number.", ButtonType.OK);
                alert.showAndWait();
            } else {
                int tableNumber = Integer.parseInt(tableNumberString);
                System.out.println(tableNumber);
                DatabaseConnection.getInstance().createStatement().execute("insert into waiter_call (table_no,waiter_id,served) values(" + tableNumber + ", NULL, 0)");
                System.out.println("done");
//            textConfirm.setText("A waiter has been called.");
                Alert alert = new Alert(Alert.AlertType.NONE, "A waiter will be with you as soon as possible.", ButtonType.OK);
                alert.showAndWait();
                this.screensController.setScreen(Main.menuScreenID);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * in the function below oncce back button is pressed Menu screen is loaded.
     * @param event
     */
    @FXML
    void backBtnPressed(ActionEvent event) {
        this.screensController.loadScreen(Main.menuScreenID, Main.menuScreenFile);
        this.screensController.setScreen(Main.menuScreenID);
    }

    /**
     * Below Method is for database to select call ID, Table no and Waiter ID.
     * @param event
     * @throws SQLException
     */
    @FXML
    void justNothing(ActionEvent event) throws SQLException{
        ResultSet test = DatabaseController.executeQuery(DatabaseConnection.getInstance(), "select * from waiter_call");
        System.out.println("---------------------------------------");
        System.out.println("call_id | table_no | waiter_id | served");
        System.out.println("---------------------------------------");
        while (test.next()) {
            System.out.println(test.getString(1) + " | " + test.getString(2) + " | " + test.getString(3) + " | " + test.getString(4) + " |");
        }
        System.out.println("---------------------------------------");
    }
}
