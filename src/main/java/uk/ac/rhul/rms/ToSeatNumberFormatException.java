package uk.ac.rhul.rms;

public class ToSeatNumberFormatException extends Exception{
    public ToSeatNumberFormatException(){
        super("Values inserted is not in the correct format for the table seat_no.");
    }
}
