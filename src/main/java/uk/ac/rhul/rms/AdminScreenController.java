package uk.ac.rhul.rms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;
import javafx.fxml.Initializable;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminScreenController implements ControlledScreen, Initializable {


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

  @FXML
  private Pane pane;

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

  @FXML
  void deleteUserBtnPressed(ActionEvent event) {
    this.screensController.loadScreen(Main.deleteUserScreenID, Main.deleteUserScreenFile);
    this.screensController.setScreen(Main.deleteUserScreenID);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    File file = new File("src/main/resources/uk/ac/rhul/rms/media/admin screen.png");
    javafx.scene.image.Image image = new Image(file.toURI().toString());
    BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1,1, true, true, false,false));
    Background background = new Background(backgroundImage);
    pane.setBackground(background);
  }

}
