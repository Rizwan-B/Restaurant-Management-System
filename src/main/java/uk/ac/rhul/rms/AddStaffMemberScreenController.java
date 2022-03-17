package uk.ac.rhul.rms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddStaffMemberScreenController implements ControlledScreen, Initializable {

  private ScreensController screensController;

  /**
   *
   * @param screenParent The parent ScreensController of 'this' screen.
   */
  @Override
  public void setScreenParent(ScreensController screenParent) {
    this.screensController = screenParent;
  }

  @FXML
  private ComboBox<String> roleChoiceBox;

  @FXML
  private TextField usernameField;

  @FXML
  private TextField passwordField;

  @FXML
  private TextField reTypePasswordField;

  @FXML
  private TextField emailField;



  /**
   *
   * @param event manage order screen is loaded
   */
  @FXML
  void manageOrderBtnPressed(ActionEvent event) {
    this.screensController.loadScreen(Main.manageOrderScreenID, Main.manageOrderScreenFile);
    this.screensController.setScreen(Main.manageOrderScreenID);
  }

  /**
   * once menu is pressed, menu screen is loaded.
   * @param event load menu screen
   */
  @FXML
  void changeMenuPressed(ActionEvent event) {
    this.screensController.loadScreen(Main.changeMenuScreenID, Main.changeMenuScreenFile);
    this.screensController.setScreen(Main.changeMenuScreenID);
  }

  @FXML
  void logOut(ActionEvent event) {
    Main.sessionId = null;
    try {
      DatabaseConnection.getInstance().createStatement().execute("UPDATE user_table set session_id='NULL' WHERE user_id=" + Main.currentLoggedInUser);
    } catch (SQLException e) {
      System.out.println(e.toString());
    }
    Main.currentLoggedInUser = 0;
    this.screensController.setScreen(Main.loginScreenID);
  }

  @FXML
  void backBtnPressed(ActionEvent event) {
    Main.loginLoader();
  }

  @FXML
  void createUser(ActionEvent event) {
    String username = usernameField.getText();
    String password = passwordField.getText();
    String reTypePassword = reTypePasswordField.getText();
    String email = emailField.getText();
    String role = roleChoiceBox.getSelectionModel().getSelectedItem();

    try {
      if (username.equals("") || password.equals("") || reTypePassword.equals("") || role.equals("") || email.equals("")) {
        System.out.println("Please fill all fields.");
      } else {
        if (!password.equals(reTypePassword)) {
          System.out.println("Passwords do not match.");
        } else {
          System.out.println("OK");
          Security security = new Security(username, password);
          String hashedPassword = security.getHashPassword();
          DatabaseConnection.getInstance().createStatement().execute(
              "INSERT INTO user_table(user_name, password, user_role, session_id, busy)" +
                  " VALUES('"+ username + "', '" + hashedPassword + "', '" + role + "', 'NULL', " + "'1'" +")");
          Mail sendMail = new Mail(email);
          sendMail.sendConfirmationEmail(username, password);
        }
      }
    } catch (NullPointerException | SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
      System.out.println(e.toString());
    }

  }

  @FXML
  void deleteUserBtnPressed(ActionEvent event) {
    this.screensController.loadScreen(Main.deleteUserScreenID, Main.deleteUserScreenFile);
    this.screensController.setScreen(Main.deleteUserScreenID);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ObservableList<String> list = FXCollections.observableArrayList("WAITER", "STAFF", "ADMIN");
    roleChoiceBox.setItems(list);
  }
}
