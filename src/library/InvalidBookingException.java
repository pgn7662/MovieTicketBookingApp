package library;

public class InvalidBookingException extends Throwable{
    public InvalidBookingException(String exceptionText){
        super(exceptionText);
    }
}
