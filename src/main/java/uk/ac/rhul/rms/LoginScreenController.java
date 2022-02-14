package uk.ac.rhul.rms;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The screen controller for the login screen implementing the ControlledScreen Interface.
 *
 * @author Virginia Litta
 * @author Mohamed Yusuf
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
    private Button loginBtn;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label resultLabel;

    @FXML
    void backBtnPressed(ActionEvent event) {
        this.screensController.setScreen(Main.startScreenID);
    }

    @FXML
    void bypass(ActionEvent event) {
        String id = event.getSource().toString().split(",")[1].split("'")[1].split("'")[0];
        if (id.equals("Ahmed")) {
            Main.currentLoggedInUser = 2;
            this.screensController.loadScreen(Main.waiterPortalScreenID, Main.WaiterPortalScreenFile);
            this.screensController.setScreen(Main.waiterPortalScreenID);
        } else if (id.equals("Rizwan")){
            Main.currentLoggedInUser = 3;
            this.screensController.loadScreen(Main.waiterPortalScreenID, Main.WaiterPortalScreenFile);
            this.screensController.setScreen(Main.waiterPortalScreenID);
        } else if (id.equals("Virginia")){
            Main.currentLoggedInUser = 4;
            this.screensController.loadScreen(Main.waiterPortalScreenID, Main.WaiterPortalScreenFile);
            this.screensController.setScreen(Main.waiterPortalScreenID);
        } else if (id.equals("Mo")) {
            Main.currentLoggedInUser = 5;
            this.screensController.loadScreen(Main.staffPortalScreenID, Main.staffPortalScreenFile);
            this.screensController.setScreen(Main.staffPortalScreenID);

        } else if (id.equals("Lucas")) {
            Main.currentLoggedInUser = 6;
            this.screensController.loadScreen(Main.staffPortalScreenID, Main.staffPortalScreenFile);
            this.screensController.setScreen(Main.staffPortalScreenID);
        } else if (id.equals("Muqdas")) {
            Main.currentLoggedInUser = 7;
            this.screensController.loadScreen(Main.staffPortalScreenID, Main.staffPortalScreenFile);
            this.screensController.setScreen(Main.staffPortalScreenID);
        } else {
            System.out.println("admin pressed");
        }
    }

    @FXML
    void login(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        try {
            ResultSet loginResult = DatabaseController.executeQuery(DatabaseConnection.getInstance(),
                    "SELECT user_id, user_role FROM user_table WHERE user_name='" + username + "' AND password='" + password + "'");
            while (loginResult.next()) {
                if (loginResult.getString(2).equals("STAFF")) {
                    Main.currentLoggedInUser = loginResult.getInt(1);
                    Main.sessionId = loginResult.getString(1);
                    DatabaseConnection.getInstance().createStatement().execute(
                            "UPDATE user_table set session_id="
                                    + loginResult.getString(1)
                                    + " WHERE user_id="
                                    + loginResult.getString(1));
                    // this will be a hashcode in production. currently just setting sessionId to userID.
                    this.screensController.loadScreen(Main.staffPortalScreenID, Main.staffPortalScreenFile);
                    this.screensController.setScreen(Main.staffPortalScreenID);
                } else if (loginResult.getString(2).equals("WAITER")) {
                    Main.currentLoggedInUser = loginResult.getInt(1);
                    Main.sessionId = loginResult.getString(1);
                    DatabaseConnection.getInstance().createStatement().execute(
                            "UPDATE user_table set session_id="
                                    + loginResult.getString(1)
                                    + " WHERE user_id="
                                    + loginResult.getString(1));
                    // this will be a hashcode in production. currently just setting sessionId to userID.
                    this.screensController.loadScreen(Main.waiterPortalScreenID, Main.WaiterPortalScreenFile);
                    this.screensController.setScreen(Main.waiterPortalScreenID);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}


