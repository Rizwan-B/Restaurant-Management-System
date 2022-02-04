package uk.ac.rhul.rms;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.sql.Connection;
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
    void backBtnPressed(ActionEvent event) {
        this.screensController.loadScreen(Main.startScreenID, Main.startScreenFile);
        this.screensController.setScreen(Main.startScreenID);
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
            } else if (role.equals("WAITER")) {
                System.out.println("You are a waiter");
            } else if (role.equals("ADMIN")) {
                System.out.println("You are an admin.");
            } else {
                System.out.println("username or password incorrect");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}


