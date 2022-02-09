package uk.ac.rhul.rms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

public class CancelOrderScreenController implements ControlledScreen {

    ScreensController screensController;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screensController = screenParent;
    }

    @FXML
    private Button backBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void backBtnPressed(ActionEvent event) {
        this.screensController.setScreen(Main.waiterPortalScreenID);
        this.screensController.unloadScreen(Main.cancelOrderScreenID);
    }

    @FXML
    void cancelBtnPressed(ActionEvent event) {
        System.out.println("Order cancelled.");
    }
}
