import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Screen {
    private String screenName;
    private Seat[][] seats;
    private int totalNumberOfSeats;
    private int rows;
    private int columns;
    private HashMap<LocalDate,ArrayList<Show>> showsPerDay;

    public Screen(String screenName, int rows, int columns) {
        this.screenName = screenName;
        this.rows = rows;
        this.columns = columns;
        showsPerDay = new HashMap<>();
        seats = new Seat[rows][columns];
        this.totalNumberOfSeats = rows * columns;
        initializeSeats();
    }

    private void initializeSeats(){
        String seatNumber;
        for(int rowCounter = 0; rowCounter < rows; rowCounter++){
            seatNumber = (char)('A'+ rowCounter)+"";
            for(int columnCounter = 0 ; columnCounter < columns ; columnCounter++){
                seats[rowCounter][columnCounter] = new Seat( seatNumber+(columnCounter+1));
//                System.out.print(seats[rowCounter][columnCounter].getSeatStatus()+"  ");
            }
//            System.out.println();
        }
    }

    public HashMap<LocalDate, ArrayList<Show>> getShowsPerDay() {
        return showsPerDay;
    }

    public String getScreenName() {
        return screenName;
    }

    public void addShow(LocalDate date,ArrayList<Show> shows,Admin admin){
        showsPerDay.put(date,shows);
    }
}
