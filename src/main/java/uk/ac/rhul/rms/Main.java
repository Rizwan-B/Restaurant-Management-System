package uk.ac.rhul.rms;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.rhul.screenmanager.ScreensController;


/**
 * A public Main class that starts up the application.
 *
 * @author Mohamed Yusuf
 *
 */
public class Main extends Application {

  // The ID and location for the fxml startScreen. Make sure to follow the id naming standard I have
  // used below.
  public static String startScreenID = "StartScreen";
  public static String startScreenFile =
      "/uk/ac/rhul/rms/StartScreen.fxml";
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

  public static int currentLoggedInUser = 0;


  @Override
  public void start(Stage primaryStage) throws Exception {
    ScreensController mainScreenController = new ScreensController();
    mainScreenController.loadScreen(staffPortalScreenID, staffPortalScreenFile);
    // ^ added this here cause of the login bypass functionality.
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
