import java.time.LocalTime;
import java.util.ArrayList;

public class Show  {
    private LocalTime startTime;
    private ArrayList<Seat> bookedSeats;
    private Movie movieShowing;
    private int intermissionTime;//in minutes

    public Show(LocalTime startTime, ArrayList<Seat> bookedSeats, Movie movieShowing, int intermissionTime) {
        this.startTime = startTime;
        this.bookedSeats = bookedSeats;
        this.movieShowing = movieShowing;
        this.intermissionTime = intermissionTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public ArrayList<Seat> getBookedSeats() {
        return bookedSeats;
    }

    public Movie getMovieShowing() {
        return movieShowing;
    }

    public int getIntermissionTime() {
        return intermissionTime;
    }
}
