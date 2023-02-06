package application;

import library.*;
import library.Record;
import java.time.LocalDate;
import java.time.LocalTime;


public class AdminMenu {
    private final Input input = new Input();

    protected void displayAdminMenu(MovieTicketBookingApp app, Admin admin) {
        try {
            admin.initialize();
        }catch (InvalidDataException e)
        {
            System.out.println("The movies are initialized");
        }
        loop:
        while (true) {
            System.out.println("1.Movie\n2.Theatre\n3.Logout");
            System.out.print("Enter your choice to select:");
            switch (input.getInteger()) {
                case 1 -> viewMovieOptions(app, admin);
                case 2 -> viewTheatreOptions(app, admin);
                case 3 -> {
                    break loop;
                }
            }
        }
    }

    private void viewMovieOptions(MovieTicketBookingApp app, Admin admin) {
        loop1:
        while (true) {
            System.out.println("1.Add a movie");
            System.out.println("2.Search a movie");
            System.out.println("3.HOME");
            System.out.print("Enter your choice:");
            switch (input.getInteger()) {
                case 1 -> addMovie(admin,app);
                case 2 -> {
                    System.out.print("Enter a movie name:");
                    String movieName = input.getString();
                    Movie searchedMovie = app.searchMovie(movieName.toLowerCase());
                    if (searchedMovie == null) {
                        System.out.println("Sorry! No result found");
                        continue;
                    }
                    loop2:
                    while (true) {
                        System.out.println(searchedMovie);
                        System.out.println("1.Add Movie to a Show\n2.View Shows\n3.Remove Movie\n4.Return");
                        System.out.print("Enter your choice to select:");
                        switch (input.getInteger()) {
                            case 1 -> {
                                while (true) {
                                    System.out.println("Enter 1 to search a theatre");
                                    System.out.println("Enter any other number to go back");
                                    if (input.getInteger() != 1)
                                        continue loop2;
                                    System.out.print("Enter the theatre name to search:");
                                    String theatreName = input.getString();
                                    String location = app.selectLocation();
                                    Theatre selectedTheatre = app.searchTheatre(theatreName.toLowerCase(),location);
                                    if (selectedTheatre == null) {
                                        System.out.println("Sorry! No result found");
                                        continue;
                                    }
                                    System.out.print("Enter the cost of each seat:");
                                    double seatCost = input.getSeatCost();
                                    do {
                                        int counter = 0;
                                        for (Screen screen : selectedTheatre.getScreens()) {
                                            System.out.println(++counter + " " + screen.getScreenName());
                                        }
                                        System.out.print("Select a screen by entering the serial number:");
                                        String screenName;
                                        while (true) {
                                            int index = input.getInteger() - 1;
                                            if (index > -1 && index <= selectedTheatre.getScreens().size() - 1) {
                                                screenName = selectedTheatre.getScreens().get(index).getScreenName();
                                                break;
                                            }
                                            System.out.print("Enter a valid number from the list:");
                                        }
                                        do {
                                            LocalDate showDate = null;
                                            System.out.println("Enter 1 to add date");
                                            if (searchedMovie.getReleaseDate().isAfter(LocalDate.now())) {
                                                showDate = searchedMovie.getReleaseDate();
                                                System.out.println("Enter 2 to add show on release date - " + showDate);
                                            }
                                            System.out.println("Enter any other number to go back");
                                            int choice = input.getInteger();
                                            if (choice == 1) {
                                                while (true) {
                                                    System.out.print("Enter the show date in the format dd-mm-yyyy:");
                                                    showDate = input.getDate();
                                                    if (showDate.isAfter(searchedMovie.getReleaseDate()) || showDate.isEqual(searchedMovie.getReleaseDate()))
                                                        break;
                                                    System.out.println("The date you entered is before the release date of the movie\nPlease select a different date");
                                                }
                                            }else if(choice != 2 || showDate == null)
                                                continue loop2;
                                            do {
                                                System.out.print("Enter the show time:");
                                                LocalTime showTime = input.getTime();
                                                System.out.print("Enter the intermission time in minutes:");
                                                int intermissionTime = input.getIntermissionTime();
                                                try {
                                                    admin.addShow(selectedTheatre, searchedMovie, screenName, showDate, showTime, intermissionTime, seatCost);
                                                    System.out.println("Show has been successfully added in " + screenName + " at " + showTime);
                                                } catch (InvalidShowException e) {
                                                    System.out.println(e.getMessage() + "\nEnter a different show time");
                                                }
                                                System.out.println("Enter 1 to add another show for the same day");
                                                System.out.println("Enter any other number to return");
                                            } while (input.getInteger() == 1);
                                            System.out.println("Enter 1 to add show for different date");
                                            System.out.println("Enter any other number to return");
                                        } while (input.getInteger() == 1);
                                        System.out.println("Enter 1 to add show for a different screen");
                                        System.out.println("Enter any other number to return");
                                    } while (input.getInteger() == 1);
                                }
                            }
                            case 2 -> {
                                    LocalDate showDate = LocalDate.now();
                                    if (showDate.isBefore(searchedMovie.getReleaseDate()))
                                        showDate = searchedMovie.getReleaseDate();
                                    while (true){
                                        System.out.println("Enter 1 to view shows for " + showDate);
                                        System.out.println("Enter 2 to return");
                                        System.out.println("Enter any other number to change date");
                                        int choice = input.getInteger();
                                        if(choice == 2)
                                            continue loop2;
                                        if (choice != 1) {
                                            while (true) {
                                                System.out.print("Enter the date in the format dd-mm-yyyy:");
                                                showDate = input.getDate();
                                                if (showDate.isAfter(searchedMovie.getReleaseDate()) || showDate.isEqual(searchedMovie.getReleaseDate()))
                                                    break;
                                                System.out.println("The date you selected is before the release of the movie\nPlease choose a different show date");
                                            }
                                        }
                                        String location = app.selectLocation();
                                        Show selectedShow = app.selectShowForMovie(searchedMovie, showDate,location);
                                        if (selectedShow != null) {
                                            System.out.println("Enter 1 to view seats");
                                            System.out.println("Enter any other number to return");
                                            Theatre theatre = Retriever.getInstance().getTheatre(selectedShow.getTheatreId());
                                            Screen selectedScreen = theatre.getScreen(selectedShow.getScreenName());
                                            if (input.getInteger() != 1)
                                                break;
                                            app.viewSeats(selectedScreen, selectedShow);
                                            break;
                                        } else {
                                            showDate = showDate.plusDays(1);
                                        }
                                    }
                            }
                            case 3 -> {
                                removeMovie(searchedMovie, app, admin);
                                break loop2;
                            }
                            case 4 -> {
                                break loop2;
                            }
                            default -> System.out.print("Enter a valid choice : ");
                        }
                    }
                }
                case 3 -> {
                    break loop1;
                }
                default -> System.out.println("Enter a valid choice");
            }
        }
    }

    private void addMovie(Admin admin, MovieTicketBookingApp app) {
        System.out.println("Enter 1 to add new movie");
        System.out.println("Enter 2 to add an existing movie with different language / dimension");
        System.out.println("Enter any other number to return");
        int choice = input.getInteger();
        if(choice == 1){
            System.out.print("Enter the movie name:");
            String movieName = input.getString();
            Record<Genre> genreList = new Record<>();
            do {
                System.out.print("Enter the genre:");
                genreList.add(input.getGenre());
                System.out.println("Enter 1 to add another genre");
                System.out.println("Enter any other number to continue next option");
            } while (input.getInteger() == 1);
            System.out.print("Enter the film certificate:");
            Certificate certificate = input.getCertificate();
            Record<String> casts = new Record<>();
            do {
                System.out.print("Enter the cast member name:");
                casts.add(input.getString());
                System.out.println("Enter 1 to add another cast member");
                System.out.println("Enter any other number to go to next option");
            } while (input.getInteger() == 1);
            Record<String> crews = new Record<>();
            do {
                System.out.print("Enter the crew member name:");
                crews.add(input.getString());
                System.out.println("Enter 1 to add another crew member");
                System.out.println("Enter any other number to go to next option");
            } while (input.getInteger() == 1);
            System.out.print("Enter the total duration of the film in minutes:");
            int totalDuration = input.getDurationOfMovie();
            System.out.print("Enter the release date of the film in the format dd-mm-yyyy :");
            LocalDate releaseDate = input.getDate();
            System.out.print("Enter the language:");
            Language language = input.getLanguage();
            System.out.print("Enter the dimension of the film:");
            DimensionType dimensionType = input.getDimensionType();
            System.out.print("Enter the synopsis for the movie:");
            String synopsis = input.getString();
            int movieId = admin.addMovie(movieName, casts, crews, totalDuration, genreList, certificate, language, dimensionType, releaseDate, synopsis);
            if (movieId != 0)
                System.out.println("Movie has been successfully added with the movie id - " + movieId);
            else
                System.out.println("The movie is already present");
        }
        else if(choice == 2){
            while (true){
                System.out.print("Enter the movie name:");
                String movieName = input.getString();
                Movie existingMovie = app.searchMovie(movieName.toLowerCase());
                if (existingMovie == null)
                    continue;
                System.out.print("Enter the language:");
                Language language = input.getLanguage();
                System.out.print("Enter the dimension of the film:");
                DimensionType dimensionType = input.getDimensionType();
                int movieId = admin.addMovie(existingMovie, language, dimensionType);
                if (movieId == 0)
                    System.out.println("The movie is already present with the same language and dimension");
                else
                    System.out.println("The movie has been successfully added with the movie id - " + movieId);
                break;
            }
        }
    }

    private void removeMovie(Movie searchedMovie, MovieTicketBookingApp app, Admin admin) {
        System.out.println("1. Remove from database\n2. Remove from theatre\n3. Remove from a show");
        System.out.print("Enter your option to remove a movie:");
        options: switch (input.getInteger()) {
            case 1 -> {
                try {
                    admin.removeMovieFromDatabase(searchedMovie);
                    System.out.println("The movie has been removed successfully");
                    System.out.println();
                } catch (InvalidDataException e) {
                    System.out.println(e.getMessage() + "\nRemove the movie from the theatres and then try to remove the movie");
                }
            }
            case 2 -> {
                System.out.print("Enter the theatre name to search:");
                String theatreName = input.getString();
                String location = app.selectLocation();
                Theatre theatre = app.searchTheatre(theatreName.toLowerCase(),location);
                if (theatre == null)
                    break;
                try {
                    admin.removeMovieFromTheatre(searchedMovie, theatre);
                    System.out.println("The movie has been removed successfully from the theatre");
                } catch (InvalidShowException e) {
                    System.out.println(e.getMessage() + "\nRemove the movie from the shows and then try to remove movie from the theatre");
                }
            }
            case 3 -> {
                String location = app.selectLocation();
                LocalDate showDate = LocalDate.now();
                while (true){
                    if (showDate.isBefore(searchedMovie.getReleaseDate()))
                        showDate = searchedMovie.getReleaseDate();
                    System.out.println("Enter 1 to view shows for " + showDate);
                    System.out.println("Enter 2 to return");
                    System.out.println("Enter any other number to change date");
                    int choice  = input.getInteger();
                    if(choice == 2)
                        break options;
                    if (choice != 1) {
                        while (true) {
                            System.out.print("Enter the date in the format dd-mm-yyyy:");
                            showDate = input.getDate();
                            if ((showDate.isAfter(searchedMovie.getReleaseDate()) || showDate.isEqual(searchedMovie.getReleaseDate()) )&& !showDate.isBefore(LocalDate.now()))
                                break;
                            System.out.println("The date you selected is before the release of the movie\nPlease choose a different show date");
                        }
                    }
                    Show show = app.selectShowForMovie(searchedMovie,showDate,location);
                    if (show == null) {
                        showDate = showDate.plusDays(1);
                        continue;
                    }
                    try {
                        admin.removeShow(show, showDate);
                        System.out.println("The show has been removed successfully");
                        break;
                    } catch (InvalidShowException e) {
                        System.out.println(e.getMessage() + "\nNot able to remove the show");
                    }

                }
            }
        }

    }


    private void viewTheatreOptions(MovieTicketBookingApp app, Admin admin) {
        loop1:
        while (true) {
            System.out.println("1.Add a theatre");
            System.out.println("2.Search a theatre");
            System.out.println("3.HOME");
            System.out.print("Enter your choice:");
            switch (input.getInteger()) {
                case 1 -> addTheatre(admin);
                case 2 -> {
                    while (true) {
                        System.out.println("Enter 1 to search a theatre");
                        System.out.println("Enter any other number to return");
                        if (input.getInteger() != 1)
                            break;
                        System.out.print("Enter a theatre name:");
                        String theatreName = input.getString();
                        String location = app.selectLocation();
                        Theatre searchedTheatre = app.searchTheatre(theatreName.toLowerCase(),location);
                        if (searchedTheatre == null) {
                            continue;
                        }
                        loop3:
                        while (true) {
                            System.out.println(searchedTheatre.getName() + "," + searchedTheatre.getAddress().getBuildingNameAndNumber()+ "," + searchedTheatre.getAddress().getArea());
                            System.out.println("1.View Shows\n2.Edit Theatre\n3.Remove Theatre\n4.Return");
                            System.out.print("Enter your choice:");
                            switch (input.getInteger()) {
                                case 1 -> {
                                    LocalDate showDate = LocalDate.now();
                                    while (true){
                                        System.out.println("Enter 1 to view shows for " + showDate);
                                        System.out.println("Enter 2 to return");
                                        System.out.println("Enter any other number to change date");
                                        int choice = input.getInteger();
                                        if(choice == 2)
                                            break;
                                        if (choice != 1) {
                                            while (true) {
                                                System.out.print("Enter the date in the format dd-mm-yyyy:");
                                                showDate = input.getDate();
                                                if (showDate.isAfter(LocalDate.now()) || showDate.isEqual(LocalDate.now()))
                                                    break;
                                                System.out.println("The date you selected is already over\nPlease choose a different show date");
                                            }
                                        }
                                        Show show = app.selectShowFromTheatre(searchedTheatre, showDate);
                                        if (show != null) {
                                            System.out.println("Enter 1 to view seats");
                                            System.out.println("Enter any other number to return");
                                            if (input.getInteger() != 1)
                                                break;
                                            Screen screen = searchedTheatre.getScreen(show.getScreenName());
                                            app.viewSeats(screen, show);
                                            break;
                                        }
                                        else{
                                            showDate = showDate.plusDays(1);
                                        }
                                    }
                                }
                                case 2 -> editTheatre(searchedTheatre, admin);
                                case 3 -> {
                                    try {
                                        admin.removeTheatre(searchedTheatre);
                                        System.out.println("Theatre has been removed");
                                        return;
                                    }
                                    catch (InvalidShowException e){
                                        System.out.println(e.getMessage()+"\nUnable to remove the theatre");
                                    }
                                }
                                case 4 -> {
                                    break loop3;
                                }
                            }
                        }
                    }
                }
                case 3 -> {
                    break loop1;
                }
                default -> System.out.print("Enter a valid choice:");
            }
        }

    }

    private void addTheatre(Admin admin) {
        System.out.print("Enter the Theatre name:");
        String theatreName = input.getString();
        System.out.println("Enter the address");
        Address address = input.getAddress();
        int theatreId = admin.addTheatre(theatreName,address);
        if(theatreId == 0) {
            System.out.println("The theatre is already present in the same area");
            return;
        }
        System.out.print("Enter the number of screens in the theatre:");
        int numberOfScreens = input.getNumberOfScreens();
        for(int i = 0;i<numberOfScreens;i++){
            while (true){
                try {
                    System.out.print("Enter the screen name for the screen " + (i + 1) + " :");
                    String screenName = input.getString();
                    System.out.print("Enter the number of rows for the screen " + (i + 1) + " :");
                    int numberOfRows = input.getNumberOfRows();
                    System.out.print("Enter the number of columns for the screen " + (i + 1) + " :");
                    int numberOfColumns = input.getNumberOfColumns();
                    admin.addScreen(screenName, Retriever.getInstance().getTheatre(theatreId), numberOfRows, numberOfColumns);
                    break;
                } catch (InvalidDataException e) {
                    System.out.println(e.getMessage() + "\nSelect a different screen name");
                }
            }
        }
        System.out.println("Theatre has been successfully added with the theatre id - "+theatreId);
    }

    private void editTheatre(Theatre theatreSelected,Admin admin){
        System.out.println(theatreSelected);
        System.out.println("1.Edit Name\n2.Edit Address\n3. Add new Screen\n4. Remove screen");
        System.out.print("Enter the choice to edit the details of the theatre:");
        switch (input.getInteger()){
            case 1 ->{
                System.out.print("Enter the name of the theatre:");
                String theatreName = input.getString();
                try {
                    admin.editTheatre(theatreName,theatreSelected);
                    System.out.println("The theatre name has been changed successfully");
                }catch (InvalidDataException e){
                    System.out.println(e.getMessage()+"\nUnable to change the theatre name");
                }
            }
            case 2 ->{
                System.out.print("Enter the address of the theatre:");
                Address address = input.getAddress();
                try {
                    admin.editTheatre(address,theatreSelected);
                    System.out.println("The theatre address has been changed successfully");
                }catch (InvalidDataException e){
                    System.out.println(e.getMessage()+"\nUnable to change the theatre address");
                }
            }
            case 3 ->{
                System.out.print("Enter name for the screen :");
                String screenName = input.getString();
                System.out.print("Enter the number of rows:");
                int numberOfRows = input.getInteger();
                System.out.print("Enter the number of columns: ");
                int numberOfColumns = input.getInteger();
                try{
                    admin.addScreen(screenName,theatreSelected,numberOfRows,numberOfColumns);
                    System.out.println("The screen has been added successfully");
                }
                catch (InvalidDataException e) {
                    System.out.println(e.getMessage()+"\nUnable to add screen");
                }
                System.out.println("Screen has been successfully added");
            }
            case 4 ->{
                Screen selectedScreen;
                int screenIndex = 0;
                for(Screen screen : theatreSelected.getScreens()){
                    System.out.println(String.format("%2d",++screenIndex)+" "+screen.getScreenName());
                }
                System.out.print("Enter the serial number to select the screen:");
                while (true){
                    int choice = input.getInteger() - 1;
                    if (choice < screenIndex && choice >= 0) {
                        selectedScreen = theatreSelected.getScreens().get(choice);
                        break;
                    }
                    System.out.print("Enter a valid serial number from the list:");
                }
                try {
                    admin.removeScreen(selectedScreen.getScreenName(),theatreSelected);
                    System.out.println("The screen has been removed successfully");
                }
                catch (InvalidDataException e){
                    System.out.println(e.getMessage()+"\nUnable to remove screen");
                }

            }
        }
    }

}
