package uk.ac.rhul.rms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The Controller for the Welcome/Start screen implementing the ControlledScreen Interface.
 *
 * @author Mohamed Yusuf
 *
 */
public class StartScreenController implements ControlledScreen, Initializable {

  ScreensController screenController;


  /**
   *
   * @param screenParent The parent ScreensController of 'this' screen.
   */
  @Override
  public void setScreenParent(ScreensController screenParent) {
    this.screenController = screenParent;

  }

  /**
   * Below are buttons for start screen.
   */
  @FXML
  private Button menuBtn;
  
  @FXML
  private Button loginBtn;

  @FXML
  private Button reservationBtn;

  @FXML
  private Pane pane;

  @FXML
  private ImageView image;


  /**
   * this loads menu screen once menu is clicked.
   * @param event load menu screen
   */
  @FXML
  void changeToMenu(ActionEvent event) {
    this.screenController.loadScreen(Main.menuScreenID, Main.menuScreenFile);
    this.screenController.setScreen(Main.menuScreenID);
  }

  /**
   * This loads reservation screen when clicked reservation.
   * @param event load reservation screen
   */
  @FXML
  void changeToReservation(ActionEvent event) {
    this.screenController.loadScreen(Main.reservationScreenID, Main.reservationScreenFile);
    this.screenController.setScreen(Main.reservationScreenID);
  }

  /**
   * this is to bring up login screen when clicked login.
   * @param event load login screen
   */
  @FXML
  private void loginBtnPressed(ActionEvent event) {
    if (Main.sessionId != null) {
      ResultSet loggedUser = DatabaseController.executeQuery(DatabaseConnection.getInstance(), "SELECT user_id, user_role FROM user_table WHERE session_id='" + Main.sessionId + "'");
      try {
        while (loggedUser.next()) {
          if (loggedUser.getString(2).equals("STAFF")) {
            this.screenController.loadScreen(Main.staffPortalScreenID, Main.staffPortalScreenFile);
            this.screenController.setScreen(Main.staffPortalScreenID);
          } else if (loggedUser.getString(2).equals("WAITER")) {
            this.screenController.loadScreen(Main.waiterPortalScreenID, Main.WaiterPortalScreenFile);
            this.screenController.setScreen(Main.waiterPortalScreenID);
          } else {
            this.screenController.loadScreen(Main.adminPortalScreenId, Main.adminPortalScreenFile);
            this.screenController.setScreen(Main.adminPortalScreenId);
          }
        }
      } catch (SQLException e) {
        System.out.println(e.toString());
      }
    } else {
      this.screenController.loadScreen(Main.loginScreenID, Main.loginScreenFile);
      this.screenController.setScreen(Main.loginScreenID);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    File file = new File("src/main/resources/uk/ac/rhul/rms/media/start screen.png");
    Image image = new Image(file.toURI().toString());
    BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1,1, true, true, false,false));
    Background background = new Background(backgroundImage);
    pane.setBackground(background);
  }
}
