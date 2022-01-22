package uk.ac.rhul.ScreenManager;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreensFramework extends Application {
  
  
  // This is an example Main.java type file. DO NOT USE THIS FILE IN PRODUCTION.
  
  public static String screen1ID = "main";
  public static String screen1File = "location/to/fxml/file";
  public static String screen2ID = "screen2";
  public static String screen2File = "location/to/fxml/file";
  public static String screen3ID = "Screen3";
  public static String screen3File = "location/to/fxml/file";

  @Override
  public void start(Stage primaryStage) throws Exception {
    ScreensController mainController = new ScreensController();
    mainController.loadScreen(screen1ID, screen1File);
    mainController.loadScreen(screen2ID, screen2File);
    mainController.loadScreen(screen3ID, screen3File);
    
    mainController.setScreen(screen1ID);
    
    
    Group root = new Group();
    root.getChildren().addAll(mainController);
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
  
  public static void main(String[] args) {
    launch(args);
  }
 

}
