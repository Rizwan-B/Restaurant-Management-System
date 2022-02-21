package uk.ac.rhul.rms;

import javafx.scene.control.*;
import javafx.scene.text.Text;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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
  private Button backBtn;

  @FXML
  private Text cardNo;

  @FXML
  private TextField cardNumber;

  @FXML
  private Text cvc;

  @FXML
  private TextField cvcBox;

  @FXML
  private Text dash;

  @FXML
  private ComboBox<Integer> date;

  @FXML
  private CheckBox electronicPayment;

  @FXML
  private Text expiryDate;

  @FXML
  private TextField holderName;

  @FXML
  private ListView<?> orderItems;

  @FXML
  private Text payment;

  @FXML
  private Text paymentMethod;

  @FXML
  private Button placeOrder;

  @FXML
  private TextField tableNo;

  @FXML
  private CheckBox tillsCheckBox;

  @FXML
  private Text userName;

  @FXML
  private ComboBox<Integer> year;

  @FXML
  void backBtnPressed(ActionEvent event) {
    this.screensController.loadScreen(Main.menuScreenID, Main.menuScreenFile);
    this.screensController.setScreen(Main.menuScreenID);
  }

}
