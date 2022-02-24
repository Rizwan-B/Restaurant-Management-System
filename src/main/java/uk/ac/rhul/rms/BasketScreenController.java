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
import java.sql.SQLException;
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
  private ListView<String> orderItems;

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
  private Text responseText;

  @FXML
  void backBtnPressed(ActionEvent event) {
    this.screensController.loadScreen(Main.menuScreenID, Main.menuScreenFile);
    this.screensController.setScreen(Main.menuScreenID);
  }

  @FXML
  void getPayment(ActionEvent event){
    String tableNumber = tableNo.getText();
    String card_no = cardNumber.getText();
    String holder_no = holderName.getText();
    String cvc = cvcBox.getText();

    if(electronicPayment.isSelected()) {

      if(tableNumber.isEmpty() || card_no.isEmpty() || holder_no.isEmpty() ||
              cvc.isEmpty() || date.getSelectionModel().isEmpty() ||
              year.getSelectionModel().isEmpty()){
        Alert alert1 = new Alert(Alert.AlertType.NONE, "Enter Missing Information", ButtonType.OK);
        alert1.showAndWait();

      } else {

        if (!(card_no.length() == 16) || (!(cvc.length() == 3)) ) {
          Alert alert = new Alert(Alert.AlertType.NONE, "Incorrect Card Information!", ButtonType.OK);
          alert.showAndWait();
        }
        Alert alert = new Alert(Alert.AlertType.NONE, "Payment complete, Thank You for visiting!", ButtonType.OK);
        alert.showAndWait();
        this.screensController.setScreen(Main.startScreenID);
      }
    }

    else if (tillsCheckBox.isSelected()){

      if(tableNumber.isEmpty()){
        Alert alert1 = new Alert(Alert.AlertType.NONE, "Enter table Number", ButtonType.OK);
        alert1.showAndWait();
      } else {
        Alert alert = new Alert(Alert.AlertType.NONE, "Please head to the tills, Thank you for visiting!", ButtonType.OK);
        alert.showAndWait();
        this.screensController.setScreen(Main.startScreenID);
      }
    }

    else {
      Alert alert1 = new Alert(Alert.AlertType.NONE, "Select a payment option.", ButtonType.OK);
      alert1.showAndWait();
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

  @FXML
  void getItems() throws SQLException, InvalidMenuIdException {
    String[] items = DatabaseController.getTempOrder(DatabaseConnection.getInstance()).split("-");
    for (int i = 0; i < items.length; i++) {
      orderItems.getItems().add(items[i]);
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
