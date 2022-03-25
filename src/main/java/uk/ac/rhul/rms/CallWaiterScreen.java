package uk.ac.rhul.rms;

import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;


/**
 * The screen controller for the call waiter screen interface.
 *
 * @author Rizwan Bagdadi
 *
 */
public class CallWaiterScreen implements ControlledScreen, Initializable {


  private ScreensController screensController;

  @Override
  public void setScreenParent(ScreensController screenParent) {
    this.screensController = screenParent;
  }

  /**
   * Below are releveant text fields , buttons and texts for the call waiter screen.
   */
  @FXML
  private TextField tableNumberField;

  @FXML
  private Button callWaiterBtn;

  @FXML
  private Text textConfirm;

  @FXML
  private Pane pane;

  /**
   * In the method below once call waiter button is pressed, user enters his table number and the
   * waiter is alerted. Message to user is also given that waiter is on its way.
   *
   * @param event call waiter button pressed.
   */
  @FXML
  void callWaiterBtnPressed(ActionEvent event) {
    try {
      String tableNumberString = this.tableNumberField.getText();
      if (tableNumberString.isEmpty()) {
        Alert alert =
            new Alert(Alert.AlertType.NONE, "Please enter your table number.", ButtonType.OK);
        alert.showAndWait();
      } else {
        int tableNumber = Integer.parseInt(tableNumberString);
        DatabaseConnection.getInstance().createStatement()
            .execute("insert into waiter_call (table_no,waiter_id,served) values(" + tableNumber
                + ", NULL, 0)");
        // textConfirm.setText("A waiter has been called.");
        Alert alert = new Alert(Alert.AlertType.NONE,
            "A waiter will be with you as soon as possible.", ButtonType.OK);
        alert.showAndWait();
        this.screensController.setScreen(Main.menuScreenID);
      }
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  /**
   * in the function below oncce back button is pressed Menu screen is loaded.
   *
   * @param event back button is pressed.
   */
  @FXML
  void backBtnPressed(ActionEvent event) {
    this.screensController.loadScreen(Main.menuScreenID, Main.menuScreenFile);
    this.screensController.setScreen(Main.menuScreenID);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    File file = new File("src/main/resources/uk/ac/rhul/rms/media/call waiter screen.png");
    Image image = new Image(file.toURI().toString());
    BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1,1, true, true, false,false));
    Background background = new Background(backgroundImage);
    pane.setBackground(background);
  }
}
