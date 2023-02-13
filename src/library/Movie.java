package library;

import java.time.LocalDate;

public class Movie implements Comparable<Movie>{
    private final int id;
    private final String movieName;
    private final Record<String> castList;
    private final Record<String> crewList;
    private final Record<Genre> genreList;
    private final Certificate certificate;
    private final Language language;
    private final DimensionType dimensionType;
    private final Rating rating;
    private final LocalDate releaseDate;
    private final int totalDuration;
    private final String synopsis;

    Movie(int id,String movieName, Record<String> castList, Record<String> crewList, Record<Genre> genreList, Certificate certificate, Language language, DimensionType dimensionType, LocalDate releaseDate, int totalDuration,String synopsis) {
        this.id = id;
        this.movieName = movieName;
        this.castList = castList;
        this.crewList = crewList;
        this.genreList = genreList;
        this.certificate = certificate;
        this.language = language;
        this.dimensionType = dimensionType;
        this.releaseDate = releaseDate;
        this.totalDuration = totalDuration;
        this.synopsis = synopsis;
        this.rating = new Rating();
    }

   Movie(Movie movie,int id, Language language, DimensionType dimensionType) {  //Copy Constructor to create a movie with an existing movie details and different language/ dimension
        this.id = id;
        this.movieName = movie.movieName;
        this.castList = movie.castList;
        this.crewList = movie.crewList;
        this.genreList = movie.genreList;
        this.certificate = movie.certificate;
        this.language = language;
        this.dimensionType = dimensionType;
        this.totalDuration = movie.totalDuration;
        this.releaseDate = movie.releaseDate;
        this.synopsis = movie.synopsis;
        this.rating = new Rating();
    }

    public int getId() {
        return id;
    }

    public String getMovieName() {
        return movieName;
    }
    Certificate getCertificate() {
        return certificate;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    int getTotalDuration() {
        return totalDuration;
    }

    public Language getLanguage() {
        return language;
    }

    public DimensionType getDimensionType() {
        return dimensionType;
    }


    void updateRating(int rating) {
        this.rating.addRating(rating);
    }
    @Override
    public String toString() {
        return "\n"+movieName.toUpperCase()+"\n"+
                dimensionType.getDimension()+" | "+language+"\n"+
                totalDuration+" minutes • "+ genreList+" • "+certificate+" • "+releaseDate+"\n"+
                (rating.getTotalRating() != 0 ?(String.format("%.2f",rating.getTotalRating()) +" / 10 \n"):" ")+
                synopsis+"\n"+
                "Cast\n"+
                castList+"\n"+
                "Crew\n"+
                crewList+"\n";
    }

    @Override
    public int compareTo(Movie o) {
        if(o.id < this.id)
            return 1;
        else if (o.id > this.id) {
            return -1;
        }
        return 0;
    }
}
