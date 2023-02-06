package library;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Ticket {
   private final int bookingId;
   private final LocalDate showDate;
   private final String bookedTime;
   private TicketStatus ticketStatus;
   private final LocalTime showTime;
   private final Record<Seat> bookedSeats;
   private final double cost;
   private final String theatreName;
   private final Address theatreAddress;
   private final Movie movie;
   private final String screenName;
   private final LocalTime endTime;



   Ticket(int bookingId, LocalDate showDate,LocalTime showTime, Record<Seat> bookedSeats, double cost, String theatreName, Address theatreAddress, Movie movie, String screenName,LocalTime endTime) {
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:a");
       this.bookedTime = LocalDateTime.now().format(formatter);
       this.bookingId = bookingId;
       this.showDate = showDate;
       this.showTime = showTime;
       this.bookedSeats = bookedSeats;
       this.cost = cost;
       this.theatreName = theatreName;
       this.theatreAddress = theatreAddress;
       this.movie = movie;
       this.screenName = screenName;
       this.endTime = endTime;
       this.ticketStatus = TicketStatus.BOOKED;
    }
    Record<Seat> getBookedSeats(){
        return bookedSeats;
    }

    public Movie getMovieShowing(){
        return movie;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    LocalDate getShowDate(){
        return showDate;
    }

    LocalTime getShowTime(){
        return showTime;
    }

    void setTicketStatus(TicketStatus ticketStatus,Customer customer) {
        this.ticketStatus = ticketStatus;
    }

    LocalTime getEndTime(){
        return endTime;
    }

    String getTheatreName() {
        return theatreName;
    }

    Address getTheatreAddress() {
        return theatreAddress;
    }

    String getScreenName() {
        return screenName;
    }


    @Override
    public String toString() {
        String statusText = this.ticketStatus.equals(TicketStatus.CANCELLED)?"The ticket has been cancelled\nThe amount is refunded":"";
        return "\nTICKET\n_____________________________________________________" +
                "\nBooked on "+bookedTime+"\n"+
                getMovieShowing().getMovieName()+"("+getMovieShowing().getCertificate()+")\n"
                +getMovieShowing().getLanguage()+","+getMovieShowing().getDimensionType().getDimension()+
                "\n"+showDate+" | "+showTime+"\n"+
                theatreName+
                "\n"+theatreAddress+"\n"+
                bookedSeats.size()+" Seat(s)"+"\t\t"+
                screenName.toUpperCase()+"\n"+
                bookedSeats+"\n"+
                "Booking Id : "+bookingId+"\n"+
                "Total Amount   =   Rs."+cost+"\n"+
                this.ticketStatus+"\n"+
                statusText+"\n_____________________________________________________";
    }

    public String getDetails(){
       return String.format("%2d",this.bookingId)+" "+getMovieShowing().getMovieName()+"\n"+
               "   "+getMovieShowing().getLanguage()+" • "+getMovieShowing().getDimensionType().getDimension()+" • "+getMovieShowing().getTotalDuration()+" minutes\n"+
               "   "+getShowDate()+" | "+getShowTime()+"\n"+
               "   "+theatreName+", "+theatreAddress.getArea()+"\n"+
               "   "+bookedSeats.size()+" Seat/s"+"\t\t"+screenName+"\n"+
               "   Booked on : "+this.bookedTime+"\n"+
               "   "+ticketStatus;
    }


}
