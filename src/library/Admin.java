package library;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Admin extends User {

    public Admin(){
        super("admin", "admin");
    }

    public int addTheatre(String theatreName, Address theatreAddress) {
        if (Database.getInstance().isTheatreAvailable(theatreName, theatreAddress)) {
            int theatreId = IdGenerator.getTheatreId();
            Theatre newTheatre = new Theatre(theatreId, theatreName, theatreAddress);
            Database.getInstance().addTheatre(newTheatre, this);
            return theatreId;
        }
        return 0;
    }

    public int addMovie(String movieName, Record<String> castMembers, Record<String> crewMembers, int totalDuration, Record<Genre> genreList, Certificate certificate, Language language, DimensionType dimensionType, LocalDate releaseDate,String synopsis) {
        if (Database.getInstance().isMovieAvailable(movieName, releaseDate, dimensionType, language)) {
            int movieId = IdGenerator.getMovieId();
            Movie newMovie = new Movie(movieId, movieName, castMembers, crewMembers, genreList, certificate, language, dimensionType, releaseDate, totalDuration,synopsis);
            Database.getInstance().addMovie(newMovie, this);
            return movieId;
        }
        return 0;
    }

    public int addMovie(Movie existingMovie,Language language,DimensionType dimensionType){
        if (Database.getInstance().isMovieAvailable(existingMovie.getMovieName(), existingMovie.getReleaseDate(), dimensionType, language)) {
            int movieId = IdGenerator.getMovieId();
            Movie newMovie = new Movie(existingMovie,movieId,language,dimensionType);
            Database.getInstance().addMovie(newMovie, this);
            return movieId;
        }
        return 0;
    }

    public void addShow(Theatre theatre, Movie selectedMovie, String screenName, LocalDate showDate, LocalTime showTime, int intermissionTime,double seatCost) throws InvalidShowException {
        Screen theatreScreen = theatre.getScreen(screenName);
        if(showDate.isEqual(LocalDate.now())){
            if(showTime.isBefore(LocalTime.now()) || showTime.equals(LocalTime.now()))
                throw new InvalidShowException("The time you entered is already over");
        }

        if (theatreScreen.getShowsPerDay().get(showDate) != null) {
            for (int counter = 0; counter < theatreScreen.getShowsPerDay().get(showDate).size(); counter++) {
                Show selectedShow = theatreScreen.getShowsPerDay().get(showDate).get(counter);
                Show nextShow = null;
                if (counter + 1 != theatreScreen.getShowsPerDay().get(showDate).size())
                    nextShow = theatreScreen.getShowsPerDay().get(showDate).get(counter + 1);
                if (((selectedShow.getEndTime().isAfter(showTime) && selectedShow.getStartTime().isBefore(showTime)) || (selectedShow.getStartTime().isBefore(showTime.plusMinutes(selectedMovie.getTotalDuration() + intermissionTime + 20))) && showTime.isBefore(selectedShow.getStartTime())) || selectedShow.getStartTime().equals(showTime) ) {
                    if (nextShow != null && nextShow.getStartTime().isBefore(showTime.plusMinutes(selectedMovie.getTotalDuration()+intermissionTime+20)))
                        throw new InvalidShowException("The show time you entered is clashing with the next show");
                    throw new InvalidShowException("The show time is not available");
                }
            }
        }

        for(Show show:theatre.getAvailableShows(selectedMovie,showDate)) {
            if(show.getStartTime().equals(showTime))
                throw new InvalidShowException("The time you entered is already present in the another screen with this same film");
        }

        if(LocalDate.now().minusDays(7).isBefore(selectedMovie.getReleaseDate()))
            throw new InvalidShowException("It is too early to add shows for the movie");

        if(showDate.isAfter(LocalDate.now().plusDays(6))){
            throw new InvalidShowException("The shows can be added only within 7 days");
        }

        Show newShow = new Show(showTime,selectedMovie,intermissionTime, seatCost,screenName,theatre.getId());
        if(!theatre.getMovieList().contains(selectedMovie))
            theatre.addMovie(selectedMovie,this);
        theatreScreen.addShow(showDate,newShow,this);
        Collections.sort(theatreScreen.getShowsPerDay().get(showDate));
    }

    public void removeMovieFromTheatre(Movie movie,Theatre theatre) throws InvalidShowException {
        if(!theatre.getMovieList().contains(movie))
            throw new InvalidShowException("The theatre does not have movie in it");
        for (Screen screen : theatre.getScreens()) {
            for (Map.Entry<LocalDate, ArrayList<Show>> map : screen.getShowsPerDay().entrySet()) {
                if (!map.getKey().isBefore(LocalDate.now()) ){
                    for (Show show : map.getValue()) {
                        if (show.getMovieShowing().equals(movie) && ((map.getKey().isEqual(LocalDate.now()) && !show.getStartTime().isBefore(LocalTime.now())) || map.getKey().isAfter(LocalDate.now())))
                            throw new InvalidShowException("The movie you selected is already running in theatres");
                    }
                }
            }
        }
        Database.getInstance().removeMovie(movie,this);
    }

    public void removeShow(Show selectedShow,LocalDate showDate) throws InvalidShowException{
        if(selectedShow.getBookedSeats().size() != 0)
            throw new InvalidShowException("The seats are already booked in this show");
        Theatre theatre = Retriever.getInstance().getTheatre(selectedShow.getTheatreId());
        theatre.getScreen(selectedShow.getScreenName()).removeShow(selectedShow,showDate,this);
    }

    public void removeMovieFromDatabase(Movie movie) throws InvalidDataException{
        for(Theatre theatre:Retriever.getInstance().getTheatreList()){
            if(theatre.getMovieList().contains(movie))
                throw new InvalidDataException("The movie is already present in a theatre");
        }
        Database.getInstance().removeMovie(movie,this);
    }

    public void removeTheatre(Theatre theatre) throws InvalidShowException{
        for(Screen screen : theatre.getScreens()) {
            for(Map.Entry<LocalDate,ArrayList<Show>> map:screen.getShowsPerDay().entrySet()){
                if(!map.getKey().isBefore(LocalDate.now())) {
                    for (Show show : map.getValue()) {
                        if((!show.getStartTime().isBefore(LocalTime.now()) && map.getKey().isEqual(LocalDate.now())) || map.getKey().isAfter(LocalDate.now()))
                            throw new InvalidShowException("The theatre has shows running");
                    }
                }
            }
        }
        Database.getInstance().removeTheatre(theatre,this);
    }

    public void editTheatre(String theatreName,Theatre theatre) throws InvalidDataException {
        if(Database.getInstance().isTheatreAvailable(theatreName,theatre.getAddress()))
            theatre.setName(theatreName,this);
        else
            throw new InvalidDataException("The Theatre is already present with the same name and address");

    }

    public void editTheatre(Address address,Theatre theatre) throws InvalidDataException{
        if(Database.getInstance().isTheatreAvailable(theatre.getName(),address))
            theatre.setAddress(address,this);
        else
            throw new InvalidDataException("The theatre is already present with the same name and address");
    }

    public void addScreen(String screenName, Theatre theatre, int numberOfColumns, int numberOfRows) throws InvalidDataException {
        Screen newScreen = new Screen(screenName,numberOfRows,numberOfColumns);
        for(Screen screen:theatre.getScreens()){
            if(screen.getScreenName().replaceAll(" ","").equalsIgnoreCase(screenName.replaceAll(" ","")))
                throw new InvalidDataException("A screen is already present with the same name");
        }
        theatre.addScreen(newScreen,this);
    }

    public void removeScreen(String screenName,Theatre theatre) throws InvalidDataException{
        if(theatre.getScreens().size() == 1)
            throw new InvalidDataException("There is only one screen in this theatre");
        Screen selectedScreen = theatre.getScreen(screenName);
        for(Map.Entry<LocalDate,ArrayList<Show>> map: selectedScreen.getShowsPerDay().entrySet() ){
            if(map.getKey().isEqual(LocalDate.now()) || map.getKey().isAfter(LocalDate.now()))
                throw new InvalidDataException("The screen has shows present");
        }
        theatre.removeScreen(screenName,this);
    }

    public void initialize() throws InvalidDataException{
        //Movies

        //Thunivu
         Record<String> thunivuCast = new  Record<>();
        thunivuCast.add("Ajith Kumar");
        thunivuCast.add("Manju Warrior");
         Record<String> thunivuCrew = new  Record<>();
        thunivuCrew.add("H Vinoth");
        thunivuCrew.add("Ghibran");
         Record<Genre> thunivuGenre = new  Record<>();
        thunivuGenre.add(Genre.ACTION);
        thunivuGenre.add(Genre.THRILLER);
        addMovie("Thunivu",thunivuCast,thunivuCrew,145,thunivuGenre,Certificate.UA,Language.TAMIL,DimensionType.TWO_DIMENSION,LocalDate.parse("11-01-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy")),"A bank robbery failure movie");

        //Varisu
         Record<String> varisuCast = new  Record<>();
        varisuCast.add("Vijay");
        varisuCast.add("Rashmika Mandana");
        varisuCast.add("Yogi Babu");
         Record<String> varisuCrew = new  Record<>();
        varisuCrew.add("Vamshi");
        varisuCrew.add("Thaman S");
         Record<Genre> varisuGenre = new  Record<>();
        varisuGenre.add(Genre.DRAMA);
        varisuGenre.add(Genre.ACTION);
        addMovie("Varisu",varisuCast,varisuCrew,170,varisuGenre,Certificate.U,Language.TAMIL,DimensionType.TWO_DIMENSION,LocalDate.parse("11-01-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy")),"A business man's family confusion");

        //Avatar 2: The Way Of Water
        Record<String> avatarCast = new  Record<>();
        avatarCast.add("Sam Worthington");
        avatarCast.add("Zoe Saldana");
        avatarCast.add("Stephen Lang");
        Record<String> avatarCrew = new  Record<>();
        avatarCrew.add("James Cameron");
        avatarCrew.add("Amanda Silver");
        avatarCrew.add("Rick Jaffa");
        Record<Genre> avatarGenre = new  Record<>();
        avatarGenre.add(Genre.SCI_FI);
        avatarGenre.add(Genre.ACTION);
        avatarGenre.add(Genre.ADVENTURE);
        avatarGenre.add(Genre.FANTASY);
        int avatar2d_tamId = addMovie("Avatar : The Way Of Water",avatarCast,avatarCrew,192,avatarGenre,Certificate.UA,Language.TAMIL,DimensionType.TWO_DIMENSION,LocalDate.parse("16-12-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")),"A Fantasy world with a beautiful environment");
        Movie avatar2d_tam = Retriever.getInstance().getMovie(avatar2d_tamId);
        if(avatar2d_tamId != 0){
            addMovie(avatar2d_tam, Language.TAMIL, DimensionType.THREE_DIMENSION);
            addMovie(avatar2d_tam, Language.ENGLISH, DimensionType.TWO_DIMENSION);
            addMovie(avatar2d_tam, Language.ENGLISH, DimensionType.THREE_DIMENSION);
        }

        //Michael
        Record<String> michealCast = new Record<>();
        michealCast.add("Sundeep Kishan");
        michealCast.add("Vijay Sethupathi");
        michealCast.add("Varalakshmi SarahKumar");
        Record<String> michealCrew = new Record<>();
        michealCrew.add("Ranjith Jeyakodi");
        michealCrew.add("Bharath Chowdary");
        Record<Genre> michealGenre = new Record<>();
        michealGenre.add(Genre.ACTION);
        michealGenre.add(Genre.DRAMA);
        michealGenre.add(Genre.THRILLER);
        addMovie("Micheal",michealCast,michealCrew,155,michealGenre,Certificate.UA,Language.TAMIL,DimensionType.TWO_DIMENSION,LocalDate.parse("03-02-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")),"A 1990s gangster's life story");


        //Run Baby Run
        Record<String> runCast = new Record<>();
        runCast.add("R J Balaji");
        runCast.add("Isha Talwar");
        runCast.add("Aishwarya Rajesh");
        Record<String> runCrew = new Record<>();
        runCrew.add("Jiyen KrishnaKumar");
        Record<Genre> runGenre = new Record<>();
        runGenre.add(Genre.DRAMA);
        runGenre.add(Genre.THRILLER);
        addMovie("Run Baby Run",runCast,runCrew,133,runGenre,Certificate.UA,Language.TAMIL,DimensionType.TWO_DIMENSION,LocalDate.parse("03-02-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")),"A thrilling kidnapping story");


        //Pathaan
        Record<String> pathaanCast = new Record<>();
        pathaanCast.add("Sharukh Khan");
        pathaanCast.add("John Abraham");
        pathaanCast.add("Deepika Padukone");
        Record<String> pathaanCrew = new Record<>();
        pathaanCrew.add("Siddharth Anand");
        Record<Genre> pathaanGenre = new Record<>();
        pathaanGenre.add(Genre.ACTION);
        pathaanGenre.add(Genre.THRILLER);
        int pathaanId = addMovie("Pathaan",pathaanCast,pathaanCrew,146,pathaanGenre,Certificate.UA,Language.TAMIL,DimensionType.TWO_DIMENSION,LocalDate.parse("25-12-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")),"A usual bollywood action movie");
        Movie pathaanTam = Retriever.getInstance().getMovie(pathaanId);
        if(pathaanId != 0){
            addMovie(pathaanTam, Language.HINDI, DimensionType.TWO_DIMENSION);
        }

        //Theatres

        //AGS
        int agsId = addTheatre("AGS Cinemas",new Address("Vivira Mall","Navalur,OMR","Chennai",603103,State.TAMIL_NADU));
        if(agsId != 0){
            Theatre ags = Retriever.getInstance().getTheatre(agsId);
            addScreen("SCREEN 1", ags, 15, 18);
            addScreen("SCREEN 2", ags, 13, 16);
            addScreen("SCREEN 3", ags, 14, 20);
            addScreen("SCREEN 4", ags, 12, 15);
        }

        //Mayajal
        int mayajalId = addTheatre("MAYAJAL Multiplex",new Address("No 34/1","Kanathur, ECR","Chennai",603112,State.TAMIL_NADU));
        if(mayajalId != 0){
            Theatre mayajal = Retriever.getInstance().getTheatre(mayajalId);
            addScreen("SCREEN 1", mayajal, 12, 15);
            addScreen("SCREEN 2", mayajal, 15, 18);
            addScreen("SCREEN 3", mayajal, 13, 16);
            addScreen("SCREEN 4", mayajal, 14, 20);
            addScreen("SCREEN 5", mayajal, 12, 15);
        }
        //PVR
        int pvrId = addTheatre("PVR Cinemas",new Address("No:137, Grand Square Mall","Doctor Seetaram Nagar","Chennai",600042,State.TAMIL_NADU));
        if(pvrId != 0){
            Theatre pvr = Retriever.getInstance().getTheatre(pvrId);
            addScreen("SCREEN 1", pvr, 12, 15);
            addScreen("SCREEN 2", pvr, 15, 18);
            addScreen("SCREEN 3", pvr, 13, 16);
            addScreen("SCREEN 4", pvr, 14, 20);
        }
    }



}
