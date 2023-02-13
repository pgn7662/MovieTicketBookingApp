package library;

import java.util.ArrayList;

public class Retriever {
    private static Retriever retriever;
    private Retriever(){
    }
    public Theatre getTheatre(int theatreId){
        return Database.getInstance().getTheatre(theatreId,this);
    }

    public ArrayList<Theatre> getTheatreList(){
        return Database.getInstance().getTheatres(this);
    }

    public ArrayList<Movie> getMovieList(){
        return Database.getInstance().getMovieList();
    }

    public Movie getMovie(int movieId){
        return Database.getInstance().getMovie(movieId,this);
    }

    public Record<String> getTheatreLocations(){
        Record<String> locations = new Record<>();
        for(Theatre theatre: getTheatreList()){
            if(!locations.contains(theatre.getAddress().getCity().toLowerCase()))
                locations.add(theatre.getAddress().getCity().toLowerCase());
        }
        return locations;
    }

    public static Retriever getInstance(){
        if(retriever == null)
            retriever = new Retriever();
        return retriever;
    }
}
