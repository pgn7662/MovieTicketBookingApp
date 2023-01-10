import java.util.ArrayList;

public class Theatre {
    private String name;
    private Address address;
    private int numberOfScreens;
    private ArrayList<Screen> screens;
    private ArrayList<Movie> movieList;

    public Theatre(String name, Address address, int numberOfScreens) {
        this.name = name;
        this.address = address;
        this.numberOfScreens = numberOfScreens;
        screens = new ArrayList<>();
        movieList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public int getNumberOfScreens() {
        return numberOfScreens;
    }

    public ArrayList<Screen> getScreens() {
        return screens;
    }

    public ArrayList<Movie> getMovieList() {
        return movieList;
    }

    public void addScreen(String screenName,int rows,int columns){
        screens.add(new Screen(screenName,rows,columns));
    }
}
