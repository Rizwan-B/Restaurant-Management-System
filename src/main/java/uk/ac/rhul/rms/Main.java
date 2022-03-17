package uk.ac.rhul.rms;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.rhul.screenmanager.ScreensController;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * A public Main class that starts up the application.
 *
 * @author Mohamed Yusuf
 *
 */
public class Main extends Application {
  /**
   * Below are many screens this application has to offer.
   */
  private static ScreensController mainScreenController;

  // The ID and location for the fxml startScreen. Make sure to follow the id naming standard I have
  // used below.
  public static String startScreenID = "StartScreen";
  public static String startScreenFile = "/uk/ac/rhul/rms/StartScreen.fxml";
  public static String menuScreenID = "MenuScreen";
  public static String menuScreenFile = "/uk/ac/rhul/rms/MenuScreen.fxml";

  public static String basketScreenID = "basketScreen";
  public static String basketScreenFile = "/uk/ac/rhul/rms/BasketScreen.fxml";

  public static String reservationScreenID = "ReservationScreen";
  public static String reservationScreenFile = "/uk/ac/rhul/rms/ReservationScreen.fxml";

  public static String callWaiterScreenID = "callWaiterScreen";
  public static String callWaiterScreenFile = "/uk/ac/rhul/rms/CallWaiterScreen.fxml";

  public static String loginScreenID = "loginScreen";
  public static String loginScreenFile = "/uk/ac/rhul/rms/LoginScreen.fxml";

  public static String staffPortalScreenID = "staffPortal";
  public static String staffPortalScreenFile = "/uk/ac/rhul/rms/StaffPortalScreen.fxml";

  public static String waiterPortalScreenID = "waiterPortal";
  public static String WaiterPortalScreenFile = "/uk/ac/rhul/rms/WaiterPortalScreen.fxml";

  public static String adminPortalScreenId = "adminPoltal";
  public static String adminPortalScreenFile = "/uk/ac/rhul/rms/AdminScreen.fxml";

  public static String manageOrderScreenID = "ManageOrderScreen";
  public static String manageOrderScreenFile = "/uk/ac/rhul/rms/ManageOrderScreen.fxml";

  public static String changeMenuScreenID = "changeMenuScreen";
  public static String changeMenuScreenFile = "/uk/ac/rhul/rms/ChangeMenuScreen.fxml";

  public static String addItemScreenID = "AddItem";
  public static String addItemScreenFile = "/uk/ac/rhul/rms/AddItem.fxml";

  public static String addStaffMemberScreenID = "AddStaffMemeber";
  public static String addStaffMemberScreenFile = "/uk/ac/rhul/rms/AddStaffMemberScreen.fxml";

  public static String deleteUserScreenID = "DeleteUser";
  public static String deleteUserScreenFile = "/uk/ac/rhul/rms/DeleteUserScreen.fxml";

  public static int currentLoggedInUser = 0;
  public static String sessionId = null;
  public static Thread workerThread;
  public static int oldRecordLength = 0;
  public static String status = " ";



  /**
   * 1) if user is not already logged in, log them in and create session id. 2) if user is already
   * logged in take them to their screen.
   */
  public static void loginLoader() {
    String user_role = "";

    if ((Main.currentLoggedInUser == 0) || (Main.sessionId == null)) {
      return;
    }

    ResultSet result = DatabaseController.executeQuery(DatabaseConnection.getInstance(),
        "SELECT user_role FROM user_table WHERE session_id='" + Main.sessionId + "'");
    try {
      while (result.next()) {
        user_role = result.getString(1);
      }
    } catch (NullPointerException e) {
      System.out.println("user doesn't exist: " + e.toString());
    } catch (SQLException e) {
      System.out.println(e.toString());
    }

    if (user_role.equals("STAFF")) {
      Main.mainScreenController.loadScreen(Main.staffPortalScreenID, Main.staffPortalScreenFile);
      Main.mainScreenController.setScreen(Main.staffPortalScreenID);
    } else if (user_role.equals("WAITER")) {
      Main.mainScreenController.loadScreen(Main.waiterPortalScreenID, Main.WaiterPortalScreenFile);
      Main.mainScreenController.setScreen(Main.waiterPortalScreenID);
    } else {
      System.out.println("loading");
      Main.mainScreenController.loadScreen(Main.adminPortalScreenId, Main.adminPortalScreenFile);
      Main.mainScreenController.setScreen(Main.adminPortalScreenId);
    }
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Main.mainScreenController = new ScreensController();

    mainScreenController.loadScreen(startScreenID, startScreenFile);
    mainScreenController.setScreen(startScreenID);

    Group root = new Group();
    root.getChildren().addAll(mainScreenController);
    Scene scene = new Scene(root, 960, 540);

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * A public static function that is run on startup.
   *
   * @param args The default argument requirement for public static void main functions.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
