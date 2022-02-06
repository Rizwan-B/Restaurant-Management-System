package uk.ac.rhul.rms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

/**
 * The screen controller for the reservation screen implementing the ControlledScreen Interface.
 *
 * @author Jacob Artis
 *
 */
public class ReservationScreenController implements ControlledScreen {

    ScreensController screensController;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screensController = screenParent;
    }

    @FXML
    void backBtnPressed(ActionEvent event) {
        this.screensController.loadScreen(Main.startScreenID, Main.startScreenFile);
        this.screensController.setScreen(Main.startScreenID);
    }
}
