import java.time.LocalDate;
import java.util.ArrayList;

public class AdminMenu {
    private Input input = new Input();
    public void displayAdminMenu(Database database,MovieTicketBookingApp app,Admin admin){
        System.out.println("Enter 1 to add a Theatre");
        System.out.println("Enter 2 to add a Movie");
        System.out.println("Enter 3 to remove a Theatre");
        System.out.println("Enter 4 to remove a Movie");
        System.out.println("Enter 5 to edit a movie");
        System.out.println("Enter 6 to edit a theatre");
        System.out.println("Enter 7 to log out");
        switch (input.getInteger()){
            case 1 -> {

            }
        }
    }

    private void addTheatre(Database database,MovieTicketBookingApp app,Admin admin){
        System.out.print("Enter the Theatre name:");
        String theatreName = input.getString();
        System.out.print("Enter the address\n");
        Address address = input.getAddress();
        System.out.print("Enter the number of screens in the theatre:");
        int numberOfScreens = input.getInteger();
        Theatre theatre = new Theatre(theatreName,address,numberOfScreens);
        for (int counter = 1 ; counter <= numberOfScreens ; counter++ ){
            System.out.print("Enter the name for the screen "+counter+" :");
            String screenName = input.getString();
            System.out.print("Enter the number of rows:");
            int rows = input.getInteger();
            System.out.print("Enter the number of columns:");
            int columns = input.getInteger();
            theatre.addScreen(screenName,rows,columns);
        }
        database.addTheatre(theatre,admin);
        System.out.println("Theatre has been successfully added");
    }

    private void addMovie(Database database,MovieTicketBookingApp app,Admin admin){
        System.out.print("Enter the movie name:");
        String movieName = input.getString();
        System.out.print("Enter the genre:");
        Genre movieGenre = input.getGenre();
        System.out.print("Enter the film certificate:");
        Certificate certificate = input.getCertificate();
        ArrayList<String> casts = new ArrayList<>();
        while(true) {
            System.out.println("Enter 1 to add cast members");
            System.out.println("Enter any other number to go to next option");
            if(input.getInteger() == 1) {
                System.out.print("Enter the cast name:");
                casts.add(input.getString());
            }
            else
                break;
        }
        ArrayList<String> crews = new ArrayList<>();
        while(true) {
            System.out.println("Enter 1 to add crew members");
            System.out.println("Enter any other number to go to next option");
            if(input.getInteger() == 1) {
                System.out.print("Enter the cast name:");
                crews.add(input.getString());
            }
            else
                break;
        }
        System.out.print("Enter the total duration of the film in minutes:");
        int totalDuration = input.getInteger();
        System.out.print("Enter the release date of the film in the format dd-mm-yyyy :");
        LocalDate releaseDate = input.getDate();
        System.out.print("Enter the language:");
        Language language = input.getLanguage();
        System.out.println("Enter the dimension of the film:");
        DimensionType dimensionType = input.getDimensionType();
        Movie movie = new Movie(movieName,casts,crews,movieGenre,certificate,language,dimensionType,releaseDate,totalDuration);
        database.addMovie(movie,admin);
    }
}
