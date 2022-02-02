package uk.ac.rhul.rms;

import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    void callWaiterBtnPressed(ActionEvent event) {
        try {
            String tableNumberString = this.tableNumberField.getText();
            int tableNumber = Integer.parseInt(tableNumberString);

            System.out.println(tableNumber);
        } catch (Exception e) {
            System.out.println("number format exception.");
        }
    }
}
