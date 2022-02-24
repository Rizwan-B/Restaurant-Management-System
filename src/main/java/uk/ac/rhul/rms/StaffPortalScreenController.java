package uk.ac.rhul.rms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;
import javafx.scene.control.ListView;

public class StaffPortalScreenController implements ControlledScreen {

    private ScreensController screensController;

    @FXML
    private Button backBtn;

    @FXML
    private ListView<?> pendingOrdersList;

    @FXML
    private ListView<?> claimedOrderList;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screensController = screenParent;
    }

    @FXML
    void backBtnPressed(ActionEvent event) {
        this.screensController.setScreen(Main.loginScreenID);
    }

    @FXML
    void claimBtnPressed(ActionEvent event) {

    }
}
