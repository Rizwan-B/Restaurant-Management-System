package uk.ac.rhul.rms;

public class SeatNumber {
    private int table_number;
    private int seats;

    public SeatNumber(int table_number, int seats){
        this.table_number = table_number;
        this.seats = seats;

    }
    public int getTableNumber(){
        return this.table_number;
    }
    public int getSeatNumber(){
        return this.seats;
    }
    @Override
    public String toString() {
        String stringForm = this.table_number+ ", " + this.seats;
        return stringForm;
    }

    public static SeatNumber toSeatNumber(String seatNumber) throws ToSeatNumberFormatException {
        String[] splitValues = seatNumber.split(", ");
        try {
            return new SeatNumber(Integer.parseInt(splitValues[0]), Integer.parseInt(splitValues[1]));
        } catch (Exception e) {
            throw new ToSeatNumberFormatException();
        }
    }
}
