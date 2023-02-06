package library;

public class Seat implements Comparable<Seat>{
    private final String seatNumber;
    private final SeatStatus seatStatus;

    Seat(String seatNumber) {
        this.seatNumber = seatNumber;
        seatStatus = SeatStatus.AVAILABLE;
    }

    Seat(Seat seat) { //Copy Constructor to add the seats to the show
        this.seatNumber = seat.seatNumber;
        this.seatStatus = SeatStatus.BOOKED;
    }

    public String getSeatStatus() {
        return seatStatus.getIcon();
    }

    public String getSeatNumber() {
        return seatNumber;
    }
    public int compareTo(Seat o) {
        for(int counter = 0; counter < this.seatNumber.length() && counter < o.seatNumber.length();counter++){
            int character1 =  this.seatNumber.charAt(counter);
            int character2 = o.seatNumber.charAt(counter);
            if(character1 > character2)
                return 1;
            else if(character1 < character2)
                return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return seatNumber;
    }
}
