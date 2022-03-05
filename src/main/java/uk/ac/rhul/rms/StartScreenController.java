package uk.ac.rhul.rms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Controller for the Welcome/Start screen implementing the ControlledScreen Interface.
 *
 * @author Mohamed Yusuf
 *
 */
public class StartScreenController implements ControlledScreen {

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
      ResultSet loggedUser = DatabaseController.executeQuery(DatabaseConnection.getInstance(), "SELECT user_id, user_role FROM user_table WHERE session_id=" + Main.sessionId);
      try {
        while (loggedUser.next()) {
          if (loggedUser.getString(2).equals("STAFF")) {
            this.screenController.loadScreen(Main.staffPortalScreenID, Main.staffPortalScreenFile);
            this.screenController.setScreen(Main.staffPortalScreenID);
          } else if (loggedUser.getString(2).equals("WAITER")) {
            this.screenController.loadScreen(Main.waiterPortalScreenID, Main.WaiterPortalScreenFile);
            this.screenController.setScreen(Main.waiterPortalScreenID);
          } else {
            System.out.println("admin.");
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
}
