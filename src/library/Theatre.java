package library;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

public class Theatre {
    private final int id;
    private String name;
    private Address address;
    private final LinkedHashMap<String,Screen> screens;
    private final ArrayList<Movie> movieList;

    Theatre(int id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
        movieList = new ArrayList<>();
        screens = new LinkedHashMap<>();
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Screen getScreen(String screenName){
        return screens.get(screenName);
    }

    public ArrayList<String> getScreens(){
        return new ArrayList<>(screens.keySet());
    }
    public ArrayList<Movie> getMovieList(){
        return movieList;
    }
    void addMovie(Movie movie,Admin admin){
        movieList.add(movie);
    }

    void setName(String name,Admin admin){
        this.name = name;
    }

    void setAddress(Address address,Admin admin){
        this.address = address;
    }

    void addScreen(Screen screen,Admin admin){
        this.screens.put(screen.getScreenName(),screen);
    }

    void removeScreen(String screenName,Admin admin){
        this.screens.remove(screenName);
    }

    public ArrayList<Show> getAvailableShows(Movie movie, LocalDate showDate){
        ArrayList<Show> availableShows = new ArrayList<>();
        for(Screen screen:screens.values()){
            if(screen.getShowsPerDay().containsKey(showDate)){
                for(Show show : screen.getShowsPerDay().get(showDate) ) {
                    if(show.getMovieShowing().equals(movie) && ((showDate.equals(LocalDate.now()) && !show.getStartTime().isBefore(LocalTime.now())) || !showDate.equals(LocalDate.now())))
                        availableShows.add(show);
                }
            }
        }
        Collections.sort(availableShows);
        return availableShows;
    }

    public ArrayList<Show> getAvailableShows(LocalDate showDate){
        ArrayList<Show> availableShows = new ArrayList<>();
//        for(Screen screen:screens.values()){
//            if(screen.getShowsPerDay().containsKey(showDate)){
//                for(Show show : screen.getShowsPerDay().get(showDate) ) {
//                    if((showDate.equals(LocalDate.now()) && !show.getStartTime().isBefore(LocalTime.now())) || !showDate.equals(LocalDate.now()))
//                        availableShows.add(show);
//                }
//            }
//        }
        for(Movie movie : movieList){
            availableShows.addAll(getAvailableShows(movie,showDate));
        }
        Collections.sort(availableShows);
        return availableShows;
    }

    void removeMovie(Movie movie,Admin admin){
        movieList.remove(movie);
    }

    void removeAllShows(LocalDate date){
        for(Screen screen : screens.values()){
            screen.getShowsPerDay().remove(date);
        }
    }

    @Override
    public String toString() {
       return  name.toUpperCase()+"\n"+
               address+"\n"+
               screens.size()+" Screen(s)"+"\n";
    }
}
