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
            String role = DatabaseController.loginTest(DatabaseConnection.getInstance(), username, password);
            if (role.equals("STAFF")) {
                System.out.println("You are a staff member.");
                this.screensController.setScreen(Main.staffPortalScreenID);
            } else if (role.equals("WAITER")) {
                this.screensController.loadScreen(Main.waiterPortalScreenID, Main.WaiterPortalScreenFile);
                System.out.println("You are a waiter");
                this.screensController.setScreen(Main.waiterPortalScreenID);
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


