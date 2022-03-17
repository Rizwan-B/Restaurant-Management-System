package uk.ac.rhul.rms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

import java.sql.SQLException;

public class DeleteUserScreenController implements ControlledScreen {

  private ScreensController screensController;

  /**
   *
   * @param screenParent The parent ScreensController of 'this' screen.
   */
  @Override
  public void setScreenParent(ScreensController screenParent) {
    this.screensController = screenParent;
  }


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

}
