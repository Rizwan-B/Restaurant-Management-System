package uk.ac.rhul.rms;

/**
 * Deals with seat numbers.
 *
 * @author Unknown
 */
public class SeatNumber {
  private int table_number;
  private int seats;

  /**
   * Constructor - initialises table number and seats.
   *
   * @param table_number Initialises the table number.
   * @param seats Initialises the seats.
   */
  public SeatNumber(int table_number, int seats) {
    this.table_number = table_number;
    this.seats = seats;

  }

  public int getTableNumber() {
    return this.table_number;
  }

  public int getSeatNumber() {
    return this.seats;
  }

  @Override
  public String toString() {
    String stringForm = this.table_number + ", " + this.seats;
    return stringForm;
  }

  /**
   * Returns the seat number in into form.
   *
   * @param seatNumber Seat number.
   * @return The seat number in integer form.
   * @throws ToSeatNumberFormatException Seat number exception
   */
  public static SeatNumber toSeatNumber(String seatNumber) throws ToSeatNumberFormatException {
    String[] splitValues = seatNumber.split(", ");
    try {
      return new SeatNumber(Integer.parseInt(splitValues[0]), Integer.parseInt(splitValues[1]));
    } catch (Exception e) {
      throw new ToSeatNumberFormatException();
    }
  }
}
