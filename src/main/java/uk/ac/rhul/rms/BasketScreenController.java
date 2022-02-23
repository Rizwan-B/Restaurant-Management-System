package uk.ac.rhul.rms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The screen controller for the basket screen implementing the ControlledScreen Interface.
 *
 * @author Jacob Artis
 * @author Muqdas
 *  
 */
public class BasketScreenController implements ControlledScreen, Initializable {

  ScreensController screensController;
  private Connection connection;
  
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
  private ComboBox<String> date;

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
  private ComboBox<String> year;

  @FXML
  void backBtnPressed(ActionEvent event) {
    this.screensController.loadScreen(Main.menuScreenID, Main.menuScreenFile);
    this.screensController.setScreen(Main.menuScreenID);
  }

  @FXML
  void getPayment(ActionEvent event){
    String tableNumber = tableNo.getText();
    int table_number = Integer.parseInt(tableNumber);
    String card_no = cardNumber.getText();
    String holder_no = holderName.getText();
    String cvc = cvcBox.getText();




    if(electronicPayment.isSelected()) {
      if (!(card_no.length() == 16)) {
        Alert alert = new Alert(Alert.AlertType.NONE, "Incorrect Card Number", ButtonType.OK);
        alert.showAndWait();

      }

      if(!(cvc.length() == 3)) {
        Alert alert1 = new Alert(Alert.AlertType.NONE, "Incorrect CVC Number", ButtonType.OK);
        alert1.showAndWait();

      }



    }

    if (tillsCheckBox.isSelected()){
      Alert alert = new Alert(Alert.AlertType.NONE, "Table Number  "+ table_number + " will pay at tills", ButtonType.OK);
      alert.showAndWait();



    }


  }

  @FXML
  void handleTills(){
    if (tillsCheckBox.isSelected()){
      electronicPayment.setSelected(false);
      cardNumber.setDisable(true);
      holderName.setDisable(true);
      cvcBox.setDisable(true);


    }
  }

  @FXML
  void handleElectronic(){
    if (electronicPayment.isSelected()){
      tillsCheckBox.setSelected(false);
      cardNumber.setDisable(false);
      holderName.setDisable(false);
      cvcBox.setDisable(false);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.connection = DatabaseConnection.getInstance();

    ObservableList<String> list = FXCollections.observableArrayList("01", "02", "03","04","05",
            "06", "07","08", "09", "10","11","12");
    date.setItems(list);
    ObservableList<String> listYear = FXCollections.observableArrayList("2022", "2023", "2024","2025",
            "2026", "2027","2028", "2029", "2030","2031","2032","2033","2034","2035","2036",
            "2037","2038","2039","2040");
    year.setItems(listYear);


  }

}
