package library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Screen {
    private final String screenName;
    private final Seat[][] seats;
    private final int numberOfRows;
    private final int numberOfColumns;
    private final HashMap<LocalDate,ArrayList<Show>> showsPerDay;

    Screen(String screenName, int numberOfRows, int numberOfColumns) {
        this.screenName = screenName;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        showsPerDay = new HashMap<>();
        seats = new Seat[numberOfRows][numberOfColumns];
        initializeSeats();
    }

    private void initializeSeats(){
        String seatNumber;
        for(int rowCounter = 0; rowCounter < numberOfRows; rowCounter++){
            seatNumber = (char)('A'+ rowCounter)+"";
            for(int columnCounter = 0; columnCounter < numberOfColumns; columnCounter++){
                String columnNumber = (columnCounter+1)+"";
                if(columnCounter < 9)
                    columnNumber = "0"+ columnNumber;
                seats[rowCounter][columnCounter] = new Seat( seatNumber+(columnNumber));
            }
        }
    }

    public HashMap<LocalDate, ArrayList<Show>> getShowsPerDay() {
        return showsPerDay;
    }

    public String getScreenName() {
        return screenName;
    }

    public Seat[][] getSeats() {
        return seats;
    }

    public int getTotalNumberOfSeats() {
        return numberOfRows*numberOfColumns;
    }

    void removeShow(Show show, LocalDate showDate, Admin admin){
        showsPerDay.get(showDate).remove(show);
    }

    void addShow(LocalDate date, Show show, Admin admin) {
        if (showsPerDay.containsKey(date)) {
            showsPerDay.get(date).add(show);
        } else {
            ArrayList<Show> shows = new ArrayList<>();
            shows.add(show);
            showsPerDay.put(date,shows);
        }
    }
}
