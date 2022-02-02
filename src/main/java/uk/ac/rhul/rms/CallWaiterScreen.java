package uk.ac.rhul.rms;

import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.crypto.Data;

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
    void justNothing(ActionEvent event) {
        ResultSet test = DatabaseController.executeQuery(DatabaseConnection.getInstance(), "select * from waiter_call");
        try{
            while (test.next()) {
                System.out.println(test.getString(1));
                System.out.println(test.getString(2));
            }
        } catch (SQLException e){
            System.out.println(e.toString());
        }

    }
}
