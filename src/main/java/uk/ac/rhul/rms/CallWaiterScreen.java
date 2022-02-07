package uk.ac.rhul.rms;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    void callWaiterBtnPressed(ActionEvent event) {
        try {
            String tableNumberString = this.tableNumberField.getText();
            int tableNumber = Integer.parseInt(tableNumberString);
            System.out.println(tableNumber);
            DatabaseController.callWaiterOnTable(DatabaseConnection.getInstance(), tableNumber);
            System.out.println("done");
        } catch (Exception e) {
            System.out.println("oops");
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
        System.out.println("-----------------------------");
        System.out.println("waiter_id | table_no | served");
        System.out.println("-----------------------------");
        while (test.next()) {
            System.out.println(test.getString(1) + " | " + test.getString(2) + " | " + test.getString(3) + " |");
        }
        System.out.println("-----------------------------");
    }
}
