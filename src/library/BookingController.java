package library;


import java.time.LocalDate;
import java.time.LocalTime;

public class BookingController {
    public int bookTicket(Record<Seat> selectedSeats, Show show, LocalDate showDate,Customer customer){
        Record<Seat> bookedSeats = new Record<>();
        for (Seat selectedSeat : selectedSeats)
            bookedSeats.add(new Seat(selectedSeat));
        show.addBookedSeats(bookedSeats);
        int bookingId = IdGenerator.getBookingId();
        Ticket ticket = new Ticket(bookingId,showDate,show.getStartTime(),selectedSeats,bookedSeats.size()*show.getCostPerSeat(),Retriever.getInstance().getTheatre(show.getTheatreId()).getName(),Retriever.getInstance().getTheatre(show.getTheatreId()).getAddress(),show.getMovieShowing(),show.getScreenName(),show.getEndTime());
        customer.addTicket(bookingId,ticket);
        return bookingId;
    }

    public void cancelTicket(int bookingId,Customer customer) throws InvalidBookingException{
        Ticket ticketToBeCancelled = customer.getTicket(bookingId);
        if(ticketToBeCancelled.getShowTime().isAfter(LocalTime.now().plusHours(2)))
            throw new InvalidBookingException("The ticket must be cancelled only 2 hours before the show time");
        Theatre theatreSelected = null;
        for(Theatre theatre: Retriever.getInstance().getTheatreList()) {
            if (theatre.getName().equals(ticketToBeCancelled.getTheatreName()) && theatre.getAddress().equals(ticketToBeCancelled.getTheatreAddress())) {
                theatreSelected = theatre;
                break;
            }
        }
        Screen screen = null;
        if (theatreSelected != null) {
            screen = theatreSelected.getScreen(ticketToBeCancelled.getScreenName());
        }
        Show bookedShow = null;
        if (screen != null) {
            for(Show show:screen.getShowsPerDay().get(ticketToBeCancelled.getShowDate())){
                if(show.getStartTime().equals(ticketToBeCancelled.getShowTime())) {
                    bookedShow = show;
                    break;
                }
            }
        }
        if (bookedShow != null) {
            for(Seat seat: ticketToBeCancelled.getBookedSeats()){
                bookedShow.removeBookedSeat(seat.getSeatNumber());
            }
        }
        ticketToBeCancelled.setTicketStatus(TicketStatus.CANCELLED,customer);
    }

}
