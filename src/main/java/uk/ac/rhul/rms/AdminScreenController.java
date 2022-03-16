package uk.ac.rhul.rms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AdminScreenController implements ControlledScreen {


  ScreensController screensController;

  /**
   * Sets the parent screen.
   *
   * @param screenParent The parent ScreensController of 'this' screen.
   */
  @Override
  public void setScreenParent(ScreensController screenParent) {
    this.screensController = screenParent;
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
  void startScreen(ActionEvent event) {
    this.screensController.setScreen(Main.startScreenID);

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

}
