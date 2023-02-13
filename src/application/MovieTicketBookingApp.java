package application;

import library.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class MovieTicketBookingApp {
    private final Input input;

    private final AdminMenu adminMenu;
    private final CustomerMenu customerMenu;
    private User currentUser;
    private final Authenticator authenticator;
    private final BookingController bookingController;


    public MovieTicketBookingApp() {
        input = new Input();
        adminMenu = new AdminMenu();
        customerMenu = new CustomerMenu();
        authenticator = new Authenticator();
        bookingController = new BookingController();
    }

    public void displayMainMenu(){
        loop: while (true){
            System.out.println("Enter 1 to Log in");
            System.out.println("Enter 2  to register");
            System.out.println("Enter 3 to exit");
            String userName;
            String password;
            switch (input.getInteger()) {
                case 1 -> {
                    System.out.print("Enter your username/email address:");
                    userName = input.getString();
                    System.out.print("Enter your password:");
                    password = input.getString();
                    currentUser = authenticator.login(userName, password);
                    if (currentUser instanceof Customer)
                        customerMenu.displayCustomerMenu(this, (Customer) currentUser, bookingController);
                    else if (currentUser instanceof Admin)
                        adminMenu.displayAdminMenu(this, (Admin) currentUser);
                    else
                        System.out.println("Invalid user name or password");
                }
                case 2 -> {
                    System.out.print("Enter your name:");
                    String name = input.getName();
                    System.out.print("Enter your phone number:");
                    long phoneNumber;
                    while (true) {
                        phoneNumber = input.getPhoneNumber();
                        if (authenticator.isPhoneNumberAvailable(phoneNumber))
                            break;
                        System.out.print("The phone number you entered is already present\nPlease enter a different phone number:");
                    }
                    System.out.print("Enter your email address:");
                    String emailAddress;
                    while (true) {
                        emailAddress = input.getEmailId();
                        if (authenticator.isEmailAvailable(emailAddress))
                            break;
                        System.out.print("The email address you entered is already present\nPlease enter a different email:");
                    }
                    System.out.print("Create a new password:");
                    password = input.getNewPassword();
                    currentUser = authenticator.signUp(name, emailAddress, phoneNumber, password);

                    System.out.println("Your account has been created successfully with the user name " + emailAddress + " and logged in to the app");
                    customerMenu.displayCustomerMenu(this, (Customer) currentUser, bookingController);
                }
                case 3 ->{
                    break loop;
                }
                default -> System.out.println("Enter a valid choice");
            }
        }
    }

    protected String selectLocation(){
        int counter = 0;
        if(Retriever.getInstance().getTheatreLocations().size() == 0) {
            System.out.println("No Theatres available");
            return null;
        }
        for(String location : Retriever.getInstance().getTheatreLocations()){
            System.out.println(String.format("%2d",++counter)+" "+location.toUpperCase());
        }
        System.out.print("Enter the serial number to select location:");
        int index;
        while (true){
            index = input.getInteger()-1;
            if (index >= 0 && index < counter)
                break;
            System.out.print("Enter a valid serial number from the list: ");
        }
        return Retriever.getInstance().getTheatreLocations().get(index);
    }

    protected Movie searchMovie(String movieName){
        boolean isMovieNameAvailable = false;
        ArrayList<Movie> searchedMovies = new ArrayList<>();
        for(Movie movie:Retriever.getInstance().getMovieList()){
            if(movie.getMovieName().toLowerCase().contains(movieName)){
                isMovieNameAvailable = true;
                System.out.println(String.format("%2d", movie.getId()) + " " + movie.getMovieName() + " • " + movie.getLanguage() + " • " + movie.getDimensionType().getDimension());
                searchedMovies.add(movie);
            }
        }
        if(!isMovieNameAvailable) {
            System.out.println("Search not found");
            return null;
        }
        System.out.print("Enter the id to select a movie: ");
        while (true){
            int id = input.getInteger();
            Movie movie = Retriever.getInstance().getMovie(id);
            if (movie != null && searchedMovies.contains(movie))
                return movie;
            else
                System.out.print("Enter a valid number from the above list: ");
        }
    }

    protected Theatre searchTheatre(String theatreName,String location){
        boolean isTheatreNameAvailable = false;
        ArrayList<Theatre> searchedTheatreList = new ArrayList<>();
        for(Theatre theatre:Retriever.getInstance().getTheatreList()){
            if(theatre.getName().toLowerCase().contains(theatreName) && theatre.getAddress().getCity().equalsIgnoreCase(location)) {
                isTheatreNameAvailable = true;
                System.out.println(String.format("%2d", theatre.getId()) + " " + theatre.getName() + ", " +theatre.getAddress().getBuildingNameAndNumber()+", " + theatre.getAddress().getArea());
                searchedTheatreList.add(theatre);
            }
        }
        if(!isTheatreNameAvailable) {
            System.out.println("Search not found");
            return null;
        }
        System.out.print("Enter the id to select a theatre: ");
        while (true){
            int id = input.getInteger();
            Theatre theatre = Retriever.getInstance().getTheatre(id);
            if (theatre != null && searchedTheatreList.contains(theatre))
                return theatre;
            else
                System.out.print("Enter a valid number from the above list: ");
        }
    }
    protected Show selectShowFromTheatre(Theatre selectedTheatre,LocalDate showDate){
        TreeMap<Movie, ArrayList<Show>> movieShows = new TreeMap<>();
        ArrayList<Show> availableShows = selectedTheatre.getAvailableShows(showDate);
        for(Show show : availableShows){
            if (movieShows.containsKey(show.getMovieShowing())) {
                movieShows.get(show.getMovieShowing()).add(show);
                Collections.sort(movieShows.get(show.getMovieShowing()));
            } else {
                ArrayList<Show> shows = new ArrayList<>();
                shows.add(show);
                movieShows.put(show.getMovieShowing(), shows);
            }
        }
        if(movieShows.size() == 0) {
            System.out.println("NO SHOWS AVAILABLE FOR - "+showDate);
            return null;
        }
        for (Map.Entry<Movie, ArrayList<Show>> map : movieShows.entrySet()) {
            int lineBreaker = 1;
            System.out.println(String.format("%2d", map.getKey().getId()) + " " + map.getKey().getMovieName() + " • " + map.getKey().getLanguage() + " • " + map.getKey().getDimensionType().getDimension());
            for (int counter = 0; counter < map.getValue().size(); counter++) {
                System.out.print(map.getValue().get(counter).getStartTime() + " | ");
                if (lineBreaker++ % 3 == 0)
                    System.out.println();
            }
            System.out.println();
        }
        System.out.print("Enter the serial number to select the movie:");
        int movieId;
        Movie selectedMovie;
        while (true) {
            movieId = input.getInteger();
            selectedMovie = Retriever.getInstance().getMovie(movieId);
            if (selectedMovie != null && movieShows.containsKey(selectedMovie))
                break;
            System.out.print("Enter a valid movie id from the list:");
        }
        System.out.println(selectedMovie);
        System.out.print("Enter the show timing :");
        Show selectedShow = null;
        while (true) {
            LocalTime showTime = input.getTime();
            for (Show show : movieShows.get(selectedMovie)) {
                if (show.getStartTime().equals(showTime))
                    selectedShow = show;
            }
            if (selectedShow != null)
                break;
            System.out.print("Enter a valid show time for the selected movie:");
        }
        return selectedShow;
    }

    protected Show selectShowForMovie(Movie selectedMovie,LocalDate showDate,String location){
        LinkedHashMap<Theatre,ArrayList<Show>> theatreShowList = new LinkedHashMap<>();
        for (Theatre theatre : Retriever.getInstance().getTheatreList()) {
            if (theatre.getMovieList().contains(selectedMovie) && theatre.getAddress().getCity().equalsIgnoreCase(location)) {
                ArrayList<Show> shows = theatre.getAvailableShows(selectedMovie,showDate);
                if(shows.size() != 0)
                    theatreShowList.put(theatre,shows);
            }
        }
        if(theatreShowList.size() == 0) {
            System.out.println("NO SHOWS AVAILABLE! FOR "+ showDate);
            return null;
        }
        for(Map.Entry<Theatre,ArrayList<Show>> map: theatreShowList.entrySet()){
            System.out.println(String.format("%2d",map.getKey().getId())+" "+map.getKey().getName()+", "+map.getKey().getAddress().getArea());
            int nextLineCounter = 1;
            for(Show show:map.getValue()){
                System.out.print(show.getStartTime()+" | ");
                if (nextLineCounter % 3 == 0)
                    System.out.println();
                nextLineCounter++;
            }
            System.out.println();
        }
        Theatre selectedTheatre;
        System.out.print("\nEnter the number to select the theatre:");
        while (true) {
            int theatreId = input.getInteger();
            selectedTheatre = Retriever.getInstance().getTheatre(theatreId);
            if (selectedTheatre != null && theatreShowList.containsKey(selectedTheatre))
                break;
            System.out.print("Enter a valid number from the list:");
        }
        System.out.println(selectedTheatre.getName() + "," +selectedTheatre.getAddress().getBuildingNameAndNumber()+ "," + selectedTheatre.getAddress().getArea());
        System.out.println("\nEnter the show time for the selected theatre");
        while (true){
            LocalTime showTime = input.getTime();
            for(Show show : theatreShowList.get(selectedTheatre)) {
                if(show.getStartTime().equals(showTime)) {
                    return show;
                }
            }
            System.out.println("Enter a valid show time for the selected theatre");
        }
    }

    protected void viewSeats(Screen screenSelected, Show showSelected){
        Collections.sort(showSelected.getBookedSeats());
        System.out.println();
        for (int counter = 0; counter < (screenSelected.getSeats()[0].length*4)+2; counter++)
            System.out.print("_");
        System.out.println("\n");
        System.out.printf("%" + (((screenSelected.getSeats()[0].length*4))/2) + "s", "SCREEN THIS WAY");
        System.out.print("\n\n    ");
        for(int column = 1; column <= screenSelected.getSeats()[0].length;column++)
            System.out.print(String.format("%02d",column)+"  ");
        System.out.println();
        int bookedSeatCounter = 0;
        for (int rowIndex = 0; rowIndex < screenSelected.getSeats().length; rowIndex++) {
            System.out.print((char) (65 + rowIndex) + "  ");
            for (int columnIndex = 0; columnIndex < screenSelected.getSeats()[0].length; columnIndex++) {
                if(showSelected.getBookedSeats().size()!=0){
                    Seat bookedSeat = showSelected.getBookedSeats().get(bookedSeatCounter);
                    if(screenSelected.getSeats()[rowIndex][columnIndex].getSeatNumber().equals(bookedSeat.getSeatNumber())) {
                        System.out.print(bookedSeat.getSeatStatus() + " ");
                        if(bookedSeatCounter < showSelected.getBookedSeats().size()-1){
                            bookedSeatCounter++;
                        }
                    }
                    else
                        System.out.print(screenSelected.getSeats()[rowIndex][columnIndex].getSeatStatus()+" ");
                }
                else
                    System.out.print(screenSelected.getSeats()[rowIndex][columnIndex].getSeatStatus()+" ");
            }
            System.out.println();
        }
        System.out.println("\n\t\t\t\t[_] - Available\n\t\t\t\t[0] - Sold\n");
        System.out.println("Available seats - "+(screenSelected.getTotalNumberOfSeats()-showSelected.getBookedSeats().size()));
        System.out.println("Seat cost = Rs."+showSelected.getCostPerSeat()+"\n");
    }

}
