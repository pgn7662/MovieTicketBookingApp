import java.time.LocalDate;
import java.util.ArrayList;

public class CustomerMenu {
    private final Input input = new Input();
    public void displayCustomerMenu(Database database){
        System.out.println("Enter 1 to book ticket");
        System.out.println("Enter 2 to view my bookings");
        System.out.println("Enter 3 to view profile");
        System.out.println("Enter 4 to log out");
        switch(input.getInteger()){
            case 1 ->{

            }
        }
    }

    private void bookTicket(Database database,MovieTicketBookingApp app){
        System.out.println("Enter 1 to search a movie");
        System.out.println("Enter 2 to search a theatre");
        System.out.println("Enter any other number to return home");
        if(input.getInteger() == 1){
            System.out.print("Enter the movie name:");
            String movieName = input.getString();
            ArrayList<Movie> searchedMovieList  = new ArrayList<>();
            for(Movie movieToBeChecked: database.getMovieList()){
                if(movieToBeChecked.getMovieName().contains(movieName))
                    searchedMovieList.add(movieToBeChecked);
            }
            Movie searchedMovie = app.getMovieFromList(searchedMovieList);
            System.out.println("Enter the date in the format dd-mm-yyyy");
        }

        if(input.getInteger() == 2){
            System.out.print("Enter the Theatre name:");
            String theatreName = input.getString();
            ArrayList<Theatre> searchedTheatreList = new ArrayList<>();
            for(Theatre theatreToBeChecked : database.getTheatreList()){
                if(theatreToBeChecked.getName().contains(theatreName))
                    searchedTheatreList.add(theatreToBeChecked);
            }
            Theatre searchedTheatre = app.getTheatreFromList(searchedTheatreList);
        }
    }

}
