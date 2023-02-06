package library;

import java.time.LocalTime;

public class Show implements Comparable<Show> {
    private final LocalTime startTime;
    private final Record<Seat> bookedSeats;
    private final Movie movieShowing;
    private final int intermissionTime;//in minutes
    private final LocalTime endTime;
    private final double costPerSeat;
    private final String screenName;
    private final int theatreId;


    Show(LocalTime startTime, Movie movieShowing, int intermissionTime, double costPerSeat, String screenName, int theatreId) {
        this.startTime = startTime;
        this.movieShowing = movieShowing;
        this.intermissionTime = intermissionTime;
        this.costPerSeat = costPerSeat;
        this.screenName = screenName;
        this.theatreId = theatreId;
        this.endTime = startTime.plusMinutes(movieShowing.getTotalDuration()+this.intermissionTime+20);
        this.bookedSeats = new Record<>();
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public Record<Seat> getBookedSeats() {
        return bookedSeats;
    }

    public Movie getMovieShowing() {
        return movieShowing;
    }

    LocalTime getEndTime() {
        return endTime;
    }

    void addBookedSeats(Record<Seat> seats){
        bookedSeats.addAll(seats);
    }

    void removeBookedSeat(String seatNumber){
        bookedSeats.removeIf(seat -> seat.getSeatNumber().equals(seatNumber));
    }

    public double getCostPerSeat() {
        return costPerSeat;
    }

    public String getScreenName() {
        return screenName;
    }

    public int getTheatreId() {
        return theatreId;
    }

    @Override
    public int compareTo(Show comparedShow) {
        if(this.startTime.isBefore(comparedShow.startTime))
            return -1;
        else if(this.startTime.isAfter(comparedShow.startTime))
            return 1;
        return 0;
    }

}

