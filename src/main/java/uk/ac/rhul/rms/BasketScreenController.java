package uk.ac.rhul.rms;

import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import javax.swing.*;
import java.util.ArrayList;

/**
 * The screen controller for the basket screen implementing the ControlledScreen Interface.
 *
 * @author Jacob Artis
 *  
 */
public class BasketScreenController implements ControlledScreen {

  ScreensController screensController;
  
  @Override
  public void setScreenParent(ScreensController screenParent) {
    this.screensController = screenParent;
  }

  @FXML
  private ListView<String> orderItems;

  @FXML
  private Button backBtn;

  @FXML
  private Button getOrderBtn;

  @FXML
  void getOrders(ActionEvent event){
    this.orderItems.getItems().add("test");
  }

  @FXML
  void selected(ActionEvent event){
    System.out.println(orderItems.getSelectionModel().getSelectedItems());
  }

  @FXML
  void backBtnPressed(ActionEvent event) {
    this.screensController.loadScreen(Main.menuScreenID, Main.menuScreenFile);
    this.screensController.setScreen(Main.menuScreenID);
  }

}
