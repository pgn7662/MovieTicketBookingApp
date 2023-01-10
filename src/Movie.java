import java.time.LocalDate;
import java.util.ArrayList;

public class Movie {
    private String movieName;
    private ArrayList<String> castList;
    private ArrayList<String> crewList;
    private Genre genre;
    private Certificate certificate;
    private Language language;
    private DimensionType dimensionType;
    private double rating;
    private LocalDate releaseDate;
    private int totalDuration;

    public Movie(String movieName, ArrayList<String> castList, ArrayList<String> crewList, Genre genre, Certificate certificate, Language language,DimensionType dimensionType, LocalDate releaseDate, int totalDuration) {
        this.movieName = movieName;
        this.castList = castList;
        this.crewList = crewList;
        this.genre = genre;
        this.certificate = certificate;
        this.language = language;
        this.dimensionType = dimensionType;
        this.releaseDate = releaseDate;
        this.totalDuration = totalDuration;
    }

    public String getMovieName() {
        return movieName;
    }

    public ArrayList<String> getCastList() {
        return castList;
    }

    public ArrayList<String> getCrewList() {
        return crewList;
    }

    public Genre getGenre() {
        return genre;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public double getRating() {
        return rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public int getTotalDuration() {
        return totalDuration;
    }
}
