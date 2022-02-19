package uk.ac.rhul.rms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

public class AddItemScreenController implements ControlledScreen {
    private ScreensController screensController;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screensController = screenParent;
    }

    @FXML
    private Button backButton;

    @FXML
    private Text headLine;

    @FXML
    void backBtnPressed(ActionEvent event) {
        this.screensController.loadScreen(Main.changeMenuScreenID, Main.changeMenuScreenFile);
        this.screensController.setScreen(Main.changeMenuScreenID);
    }

}