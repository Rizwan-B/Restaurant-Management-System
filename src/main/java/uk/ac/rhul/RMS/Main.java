package uk.ac.rhul.RMS;

import uk.ac.rhul.ScreenManager.ScreensController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  
  // The ID and location for the fxml startScreen. Make sure to follow the id naming standard I have used below. 
  public static String startScreenID = "StartScreen";
  public static String startScreenFile = "/uk/ac/rhul/RMS/WelcomeScreen.fxml";

  
  @Override
  public void start(Stage primaryStage) throws Exception {
    ScreensController mainScreenController = new ScreensController();
    mainScreenController.loadScreen(startScreenID, startScreenFile);
    
    mainScreenController.setScreen(startScreenID);
    
    
    Group root = new Group();
    root.getChildren().addAll(mainScreenController);
    Scene scene = new Scene(root, 600, 800);
    
    primaryStage.setScene(scene);
    primaryStage.show();
    
  }
  
  public static void main(String[] args) {
    launch(args);
  }

}
