package library;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Customer extends User {
    private String name;
    private long phoneNumber;
    private String emailAddress;
    private final HashMap<Integer,Ticket> myTickets;
    private Address address;
    private final HashMap<Movie,Integer> myRatings;
    private String myLocation;
    public Customer(String name,String emailAddress,long phoneNumber,String password) {
        super(emailAddress, password);
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        myTickets = new HashMap<>();
        myRatings = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    long getPhoneNumber() {
        return phoneNumber;
    }

    String getEmailAddress() {
        return emailAddress;
    }

    public ArrayList<Ticket> getMyTickets() {
        return new ArrayList<>(myTickets.values());
    }

    public void addTicket(int bookingId, Ticket ticket){
        myTickets.put(bookingId,ticket);
    }

    public Ticket getTicket(int bookingId){
        for (Map.Entry<Integer,Ticket> map: myTickets.entrySet()){
            if(map.getValue().getShowDate().isEqual(LocalDate.now()) && (map.getValue().getEndTime().isBefore(LocalTime.now()) || map.getValue().getEndTime().equals(LocalTime.now())) || map.getValue().getShowDate().isBefore(LocalDate.now()))
                map.getValue().setTicketStatus(TicketStatus.FINISHED,this);
        }
        return myTickets.get(bookingId);
    }

    public Seat selectSeat(Show show,String seatNumber) throws InvalidBookingException {
        Theatre theatre = Retriever.getInstance().getTheatre(show.getTheatreId());
        Screen screen = theatre.getScreen(show.getScreenName());
        for(Seat seat : show.getBookedSeats()){
            if(seat.getSeatNumber().equals(seatNumber))
                throw new InvalidBookingException("The seat you selected is already booked");
        }
        for(int rowIndex = 0; rowIndex < screen.getSeats().length; rowIndex++){
            for(int columnIndex = 0; columnIndex < screen.getSeats()[0].length;columnIndex++){
                if(screen.getSeats()[rowIndex][columnIndex].getSeatNumber().equals(seatNumber))
                    return screen.getSeats()[rowIndex][columnIndex];
            }
        }
        throw new InvalidBookingException("The seat you selected is not present inside the screen");
    }

    public void rateMovie(int rating,Movie movie){
        myRatings.put(movie,rating);
        movie.updateRating(rating);
    }

    public int getMyRating(Movie movie){
        if(myRatings.containsKey(movie))
            return myRatings.get(movie);
        return 0;
    }

    public void setPhoneNumber(long phoneNumber) throws InvalidDataException{
        if(phoneNumber == this.phoneNumber)
            throw new InvalidDataException("The phone number is same as the old phone number");
        else if(Database.getInstance().isPhoneNumberAvailable(phoneNumber)){
            throw new InvalidDataException("The phone number is not available");
        }
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(String emailAddress) throws InvalidDataException {
        if(emailAddress.equals(this.emailAddress))
            throw new InvalidDataException("The email is same as the old email");
        else if(Database.getInstance().isEmailAvailable(emailAddress)){
            throw new InvalidDataException("The email is not available");
        }
        this.emailAddress = emailAddress;
        setUserName(emailAddress,this);
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(String myLocation) {
        this.myLocation = myLocation;
    }

    @Override
    public String toString() {
        return  "Name          :"+name+"\n"+
                "Email         :"+emailAddress+"\n"+
                "Mobile Number :"+phoneNumber+"\n"+
                "Address       :"+((address!=null)?address:"   -   ");
    }
}
