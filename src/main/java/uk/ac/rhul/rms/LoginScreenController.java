package uk.ac.rhul.rms;

import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * The screen controller for the login screen implementing the ControlledScreen Interface.
 *
 * @author Virginia Litta
 *
 */

public class LoginScreenController implements ControlledScreen {

    ScreensController screensController;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screensController = screenParent;
    }

    @FXML
    private Button backBtn;

    @FXML
    void backBtnPressed(ActionEvent event) {
        this.screensController.loadScreen(Main.startScreenID, Main.startScreenFile);
        this.screensController.setScreen(Main.startScreenID);
    }

}


