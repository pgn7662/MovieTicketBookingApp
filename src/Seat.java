public class Seat {
    private String seatNumber;
    private SeatStatus seatStatus;
    private double cost;

    public Seat(String seatNumber) {
        this.seatNumber = seatNumber;
        seatStatus = SeatStatus.AVAILABLE;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getSeatStatus() {
        return seatStatus.getEmoji();
    }
}
