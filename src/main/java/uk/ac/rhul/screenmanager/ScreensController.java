package uk.ac.rhul.screenmanager;

import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * A public screen Controller class used by every screen controller in this application. The class
 * uses a hashmap to store and remove screen data. The package code is inspired by the repository in
 * the following link:s
 * https://github.com/alefbt/javafx-template/blob/master/src/main/java/com/korotkin/JavaFxStart/screen/framework
 *
 * @author Mohamed Yusuf
 * 
 */
public class ScreensController extends StackPane {

  /**
   * A hashMap used to store all the loaded screens that will be used in the application. We use a
   * hashMap because searching through it can be done at god speed.
   */
  private HashMap<String, Node> screens = new HashMap<>();

  private String userController;

  /**
   * A public default constructor for ScreensController.
   */
  public ScreensController() {
    super();
  }

  /**
   * A private void function that is used to add screens to the hashMap defined. The function is
   * private because it is only going to be called from within this class.
   *
   * @param screenId The ID of the screen to add to the hashMap.
   * @param screen The screen itself that must be added.
   */
  private void addScreen(String screenId, Node screen) {
    // Do not try to call this function from any other class.
    screens.put(screenId, screen);
  }

  /**
   * A public getter method to get a screen as a Node based on its ID.
   *
   * @param screenId The ID of the screen to get.
   * @return The Node screen that is requested.
   */
  public Node getScreen(String screenId) {
    return screens.get(screenId);
  }

  /**
   * A public loader method that loads screens onto the application based on their screen ID and
   * FXML file location.
   *
   * @param screenId The ID of the screen to load.
   * @param screenLocation The location of the screen that is being loaded.
   * @return True if the loader was successful, false otherwise.
   */
  public boolean loadScreen(String screenId, String screenLocation) {
    try {
      FXMLLoader myLoader = new FXMLLoader(getClass().getResource(screenLocation));
      Parent loadScreen = (Parent) myLoader.load();
      ControlledScreen myScreenController = ((ControlledScreen) myLoader.getController());
      myScreenController.setScreenParent(this);
      this.addScreen(screenId, loadScreen);
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }


  /**
   * A public setter method to a specific screen to be displayed.
   *
   * @param screenId The ID of the screen to set.
   * @return True if the set operation was successful, false otherwise.
   */
  public boolean setScreen(final String screenId) {
    if (screens.get(screenId) != null) { // screen loaded
      final DoubleProperty opacity = opacityProperty();

      if (!getChildren().isEmpty()) { // if there is more than one screen
        Timeline fade = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
            new KeyFrame(new Duration(100), new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent t) {
                getChildren().remove(0); // remove the displayed screen
                getChildren().add(0, screens.get(screenId)); // add the screen
                Timeline fadeIn =
                    new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(100), new KeyValue(opacity, 1.0)));
                fadeIn.play();
              }
            }, new KeyValue(opacity, 0.0)));
        fade.play();

      } else {
        setOpacity(0.0);
        getChildren().add(screens.get(screenId));
        // no one else being displayed, then just show this.

        Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
            new KeyFrame(new Duration(50), new KeyValue(opacity, 1.0)));
        fadeIn.play();
      }
      return true;
    } else {
      System.out.println("screen hasn't been loaded!!! \n");
      return false;
    }
  }

  /**
   * A public unload method, to unload/remove screens from the hashMap.
   *
   * @param screenId The screenId of the screen to unload.
   * @return True if the unload operation was successful, false otherwise.
   */
  public boolean unloadScreen(String screenId) {
    if (screens.remove(screenId) == null) {
      System.out.println("Screen didn't exist");
      return false;
    } else {
      return true;
    }
  }

  public void setUserController(String username) {
    this.userController = username;
  }

  public String getUserController() {
    return this.userController;
  }

}
