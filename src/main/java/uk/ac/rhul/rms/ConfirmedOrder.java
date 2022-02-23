package uk.ac.rhul.rms;

/**
 * A class representing a confirmed order.
 */
public class ConfirmedOrder {

  private Order confirmedOrder;
  private int userId;

  /**
   * The public constructor for creating a confirmed order class.
   *
   * @param confirmedOrder The order object of the order that has been confirmed.
   * @param userId The userId of the user who has claimed that order.
   */
  public ConfirmedOrder(Order confirmedOrder, int userId) {
    this.confirmedOrder = confirmedOrder;
    this.userId = userId;
  }

  /**
   * A getter that returns the order.
   *
   * @return The order contained.
   */
  public Order getOrder() {
    return this.confirmedOrder;
  }

  /**
   * Returns the userId of of the kitchen staff who confirmed this order.
   * @return An int corresponding to the user id of the staff who confirmed this order.
   */
  public int getUserId() {
    return this.userId;
  }
}
