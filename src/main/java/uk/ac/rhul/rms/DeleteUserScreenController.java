package uk.ac.rhul.rms;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DeleteUserScreenController implements ControlledScreen, Initializable {

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
  private ListView<String> userList;

  @FXML
  private Label errorLog;

  @FXML
  private Pane pane;


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
  void addStaffBtnPressed(ActionEvent event) {
    this.screensController.loadScreen(Main.addStaffMemberScreenID, Main.addStaffMemberScreenFile);
    this.screensController.setScreen(Main.addStaffMemberScreenID);
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


  void printUsers() {
    try {
      ResultSet users = DatabaseController.executeQuery(DatabaseConnection.getInstance(), "SELECT user_name, user_role FROM user_table");
      while (users.next()) {
        System.out.println(users.getString(1));
        this.userList.getItems().add(users.getString(1) + " - " + users.getString(2));
      }
    } catch (SQLException | NullPointerException e) {
      System.out.println(e.toString());
    }
  }


  @FXML
  void removeUserBtnPressed(ActionEvent event) {
    ObservableList selectedItem = this.userList.getSelectionModel().getSelectedIndices();
    int selectedUserId = Integer.parseInt(selectedItem.toArray()[0].toString());
    String selectedName = this.userList.getItems().get(selectedUserId).split(" - ")[0];
    System.out.println(selectedName);
    ResultSet loggedUser = DatabaseController.executeQuery(DatabaseConnection.getInstance(), "SELECT session_id FROM user_table WHERE user_name='" + selectedName + "'");
    try {
      while (loggedUser.next()) {
        if (!loggedUser.getString(1).equals("NULL")) {
          System.out.println("Please ask user to log out before trying to delete it.");
          this.errorLog.setText("Please ask user to log out before trying to delete it.");
        } else {
          DatabaseConnection.getInstance().createStatement().execute("DELETE FROM user_table WHERE user_name='" + selectedName + "'");
          this.errorLog.setText("User Deleted");
          this.userList.getItems().clear();
          printUsers();
        }
      }
    } catch (SQLException e) {
      System.out.println(e.toString());
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    printUsers();

    File file = new File("src/main/resources/uk/ac/rhul/rms/media/remove staff screen.png");
    javafx.scene.image.Image image = new Image(file.toURI().toString());
    BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1,1, true, true, false,false));
    Background background = new Background(backgroundImage);
    pane.setBackground(background);
  }
}
