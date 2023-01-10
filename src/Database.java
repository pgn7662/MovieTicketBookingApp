import java.util.ArrayList;

public class Database {
    private final ArrayList<Movie> movieList;
    private final ArrayList<Theatre> theatreList;
    private final ArrayList<Customer> customerList;
    private final Admin admin;

    public Database() {
        movieList = new ArrayList<>();
        theatreList = new ArrayList<>();
        customerList = new ArrayList<>();
        admin = new Admin();
    }

    public ArrayList<Movie> getMovieList() {
        return movieList;
    }

    public ArrayList<Theatre> getTheatreList() {
        return theatreList;
    }

    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void addTheatre(Theatre theatre,Admin admin){
        theatreList.add(theatre);
    }

    public void addMovie(Movie movie,Admin admin){
        movieList.add(movie);
    }
}
