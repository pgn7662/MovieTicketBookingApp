import java.time.LocalDate;
import java.time.LocalTime;

public class Ticket {
    private int id;
    private LocalDate date;
    private LocalTime showTime;
    private int numberOfSeats;
    private double cost;
    private String theatreName;

    public Ticket(int id, LocalDate date, LocalTime showTime, int numberOfSeats, double cost, String theatreName) {
        this.id = id;
        this.date = date;
        this.showTime = showTime;
        this.numberOfSeats = numberOfSeats;
        this.cost = cost;
        this.theatreName = theatreName;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getShowTime() {
        return showTime;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public double getCost() {
        return cost;
    }

    public String getTheatreName() {
        return theatreName;
    }
}
