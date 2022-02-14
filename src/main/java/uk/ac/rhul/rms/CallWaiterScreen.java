package uk.ac.rhul.rms;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    @FXML
    private TextField tableNumberField;

    @FXML
    private Button callWaiterBtn;

    @FXML
    private Button run;

    @FXML
    private Text textConfirm;

    @FXML
    void callWaiterBtnPressed(ActionEvent event) {
        try {
            String tableNumberString = this.tableNumberField.getText();
            int tableNumber = Integer.parseInt(tableNumberString);
            System.out.println(tableNumber);
            DatabaseConnection.getInstance().createStatement().execute("insert into waiter_call (table_no,waiter_id,served) values(" + tableNumber + ", NULL, 0)");
            System.out.println("done");
            textConfirm.setText("A waiter has been called.");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @FXML
    void backBtnPressed(ActionEvent event) {
        this.screensController.loadScreen(Main.menuScreenID, Main.menuScreenFile);
        this.screensController.setScreen(Main.menuScreenID);
    }

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
