package uk.ac.rhul.rms;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.sql.SQLException;

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
    private Button loginBtn;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label resultLabel;

    @FXML
    void backBtnPressed(ActionEvent event) {
        this.screensController.loadScreen(Main.startScreenID, Main.startScreenFile);
        this.screensController.setScreen(Main.startScreenID);
    }

    @FXML
    void bypass(ActionEvent event) {
        System.out.println("bypasss button pressed");
        String id = event.getSource().toString().split(",")[1].split("'")[1].split("'")[0];
        System.out.println(id);
        if (id.equals("Ahmed")) {
            this.screensController.setScreen(Main.waiterPortalScreenID);
            this.screensController.setUserController("Ahmed");
        } else if (id.equals("Rizwan")){
            this.screensController.setScreen(Main.waiterPortalScreenID);
            this.screensController.setUserController("Rizwan");
        } else if (id.equals("Virginia")){
            this.screensController.setScreen(Main.waiterPortalScreenID);
            this.screensController.setUserController("Virginia");
        } else if (id.equals("Mo")) {
            this.screensController.setScreen(Main.staffPortalScreenID);
            this.screensController.setUserController("Mo");
        } else if (id.equals("Lucas")) {
            this.screensController.setScreen(Main.staffPortalScreenID);
            this.screensController.setUserController("Lucas");
        } else if (id.equals("Muqdas")) {
            this.screensController.setScreen(Main.staffPortalScreenID);
            this.screensController.setUserController("Muqdas");
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
            String role = DatabaseController.loginTest(DatabaseConnection.getInstance(), username, password);
            if (role.equals("STAFF")) {
                System.out.println("You are a staff member.");
                this.screensController.setScreen(Main.staffPortalScreenID);
            } else if (role.equals("WAITER")) {
                System.out.println("You are a waiter");
            } else if (role.equals("ADMIN")) {
                System.out.println("You are an admin.");
            } else {
                System.out.println("username or password incorrect");
                this.resultLabel.setText("Login Failed: incorrect username of password");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}


