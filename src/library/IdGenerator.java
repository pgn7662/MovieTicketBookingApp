package library;

public class IdGenerator {
    private static int theatreId = 0;
    private static int movieId = 0;
    private static int bookingId = 0;

    private IdGenerator() {

    }

    static int getTheatreId(){
       return ++theatreId;
    }

    static int getMovieId(){
       return ++movieId;
    }

    static int getBookingId(){
        return ++bookingId;
    }

}
