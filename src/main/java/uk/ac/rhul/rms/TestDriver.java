package uk.ac.rhul.rms;

import java.sql.SQLException;
import java.util.ArrayList;

public class TestDriver {
  public static void main(String[] args) throws SQLException, InvalidMenuIdException {
    ArrayList<Order> ordersList;
    ordersList = DatabaseController.getInstance().getOrders(DatabaseConnection.getInstance());
    for (Order item : ordersList) {
      System.out.println(item.toString());
    }
  }
}
