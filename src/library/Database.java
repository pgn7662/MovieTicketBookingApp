package library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

class Database {
    private final HashMap<Integer,Movie> movieList;
    private final HashMap<Integer,Theatre> theatreList;
    private final ArrayList<Customer> customerList;
    private final Admin admin;
    private static Database database;
    private Database() {
        movieList = new HashMap<>();
        theatreList = new HashMap<>();
        customerList = new ArrayList<>();
        admin = new Admin();
    }

    ArrayList<Customer> getCustomerList(Authenticator authenticator) {
        return customerList;
    }


    Admin getAdmin(Authenticator authenticator) {
        return admin;
    }
    ArrayList<Theatre> getTheatres(Retriever retriever){
        return new ArrayList<>(theatreList.values());
    }

    void addCustomer(Customer customer, Authenticator authenticator) {
        customerList.add(customer);
    }
    void addTheatre(Theatre theatre,Admin admin){
        theatreList.put(theatre.getId(),theatre);
    }


    void addMovie(Movie movie, Admin admin){
        movieList.put(movie.getId(), movie);
    }
    Movie getMovie(int movieId,Retriever retriever){
        return movieList.get(movieId);
    }
    ArrayList<Movie> getMovieList(){
        return new ArrayList<>(movieList.values());
    }

    void removeTheatre(Theatre theatre, Admin admin){
        theatreList.values().remove(theatre);
    }

    void removeMovie(Movie movie, Admin admin){
        movieList.values().remove(movie);
    }

    Theatre getTheatre(int theatreId,Retriever retriever){
        return theatreList.get(theatreId);
    }

    static Database getInstance(){
        if(database == null)
            database = new Database();
        return database;
    }

    boolean isTheatreAvailable(String theatreName,Address theatreAddress){
        for(Theatre theatre:theatreList.values()){
            if(theatre.getName().replaceAll(" ","").equalsIgnoreCase(theatreName.replaceAll(" ","")) && theatre.getAddress().getPinCode() == theatreAddress.getPinCode() && theatre.getAddress().getArea().replaceAll(" ","").equalsIgnoreCase(theatreAddress.getArea().replaceAll(" ","")))
                return false;
        }
        return true;
    }

    boolean isMovieAvailable(String movieName, LocalDate releaseDate,DimensionType dimensionType,Language language){
        for(Movie movie:movieList.values()){
            if(movie.getMovieName().replaceAll(" ","").equalsIgnoreCase(movieName.replaceAll(" ","")) && movie.getLanguage().equals(language) && movie.getDimensionType().equals(dimensionType) && movie.getReleaseDate().equals(releaseDate))
                return false;
        }
        return true;
    }

    boolean isPhoneNumberAvailable(long phoneNumber){
        for(Customer customer : customerList ){
            if(customer.getPhoneNumber() == phoneNumber)
                return false;
        }
        return true;
    }

    boolean isEmailAvailable(String email){
        for(Customer customer : customerList ){
            if(customer.getEmailAddress().equals(email))
                return false;
        }
        return true;
    }

}
