package uk.ac.rhul.rms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import uk.ac.rhul.screenmanager.ControlledScreen;
import uk.ac.rhul.screenmanager.ScreensController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChangeMenuScreenController implements ControlledScreen, Initializable {
    private ScreensController screensController;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screensController = screenParent;
    }

    @FXML
    private Text changeMenu;

    @FXML
    private Text dessert;

    @FXML
    private ListView<String> dessertListView;

    @FXML
    private Text main;

    @FXML
    private ListView<String> mainListView;

    @FXML
    private Text starter;

    @FXML
    private ListView<String> starterListView;

    @FXML
    private Button Delete_dessert;

    @FXML
    private Button Delete_main;

    @FXML
    private Button Delete_starter;

    /**
     * This method shows the whole menu.
     */
    public void fullMenu() {
        try {
            ArrayList<MenuItem> nonVegStarter = DatabaseController.getMenuItems(DatabaseConnection.getInstance(), "Starters");
            for (MenuItem v : nonVegStarter) {
                System.out.println(v.getName());
                this.starterListView.getItems().add(v.getName());
            }
            ArrayList<MenuItem> nonVegMain = DatabaseController.getMenuItems(DatabaseConnection.getInstance(), "Main");
            for (MenuItem v : nonVegMain) {
                System.out.println(v.getName());
                this.mainListView.getItems().add(v.getName());
            }
            ArrayList<MenuItem> nonVegDessert = DatabaseController.getMenuItems(DatabaseConnection.getInstance(), "Dessert");
            for (MenuItem v : nonVegDessert) {
                System.out.println(v.getName());
                this.dessertListView.getItems().add(v.getName());
            }
        } catch(Exception e) {
            System.out.println("oops");
        }
    }

    @FXML
    void deleteStarter(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.NONE, "Are you sure you want to delete this order?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            try {

                int index_starter = this.starterListView.getSelectionModel().getSelectedIndex();
                String selected_item=this.starterListView.getSelectionModel().getSelectedItem();
                if (index_starter >= 0) {
                    this.starterListView.getItems().remove(index_starter);
                    DatabaseConnection.getInstance().createStatement().execute("DELETE FROM menu WHERE item_name = ' "+ selected_item + " ';" );

                }




            } catch (Exception e) {
                System.out.println("ERROR: SQL connection error, or you did not select an item to delete.");
            }
        }

    }

    @FXML
    void deleteMain(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.NONE, "Are you sure you want to delete this order?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            try {

                int index_main= this.mainListView.getSelectionModel().getSelectedIndex();
                String selected_item=this.mainListView.getSelectionModel().getSelectedItem();
                if (index_main >= 0) {
                    this.mainListView.getItems().remove(index_main);
                    DatabaseConnection.getInstance().createStatement().execute("DELETE FROM menu WHERE item_name = ' "+ selected_item + " ';" );
                }


            } catch (Exception e) {
                System.out.println("ERROR: SQL connection error, or you did not select an item to delete.");
            }
        }

    }

    @FXML
    void deleteDessert(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.NONE, "Are you sure you want to delete this order?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            try {

                int index_dessert = this.dessertListView.getSelectionModel().getSelectedIndex();
                String selected_item=this.dessertListView.getSelectionModel().getSelectedItem();
                if (index_dessert >= 0) {
                    this.dessertListView.getItems().remove(index_dessert);
                    DatabaseConnection.getInstance().createStatement().execute("DELETE FROM menu WHERE item_name = ' "+ selected_item + " ';" );
                }


            } catch (Exception e) {
                System.out.println("ERROR: SQL connection error, or you did not select an item to delete.");
            }
        }

    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fullMenu();


    }

}
