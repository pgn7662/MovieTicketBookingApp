package application;

import library.*;
import library.Record;
import java.time.LocalDate;

public class CustomerMenu {
    private final Input input = new Input();
    protected void displayCustomerMenu( MovieTicketBookingApp app, Customer customer,BookingController bookingController){
        String location = customer.getMyLocation();
        if(location == null) {
            location = app.selectLocation();
            customer.setMyLocation(location);
        }
        System.out.println("\n\n___________________________________________________________________");
        System.out.println("Hello! "+customer.getName()+"\nLocation : "+location);
        System.out.println("___________________________________________________________________");
        loop :while (true){
            System.out.println("Enter 1 to book ticket");
            System.out.println("Enter 2 to view my bookings");
            System.out.println("Enter 3 to view profile");
            System.out.println("Enter 4 to change location");
            System.out.println("Enter 5 to log out");
            switch (input.getInteger()) {
                case 1 -> {
                    if (location != null) {
                        bookTicket(app, customer, bookingController, location);
                    }
                    else
                        System.out.println("Not able to book tickets");
                }
                case 2 -> viewMyBookings(customer, bookingController);
                case 3 -> viewProfile(customer);
                case 4 -> {
                    location = app.selectLocation();
                    customer.setMyLocation(location);
                }
                case 5 ->{
                    break loop;
                }
                default -> System.out.print("Enter a valid choice:");
            }
        }
    }


    private void bookTicket( MovieTicketBookingApp app,Customer customer,BookingController bookingController,String location){
        loop: while (true){
            System.out.println("Enter 1 to search a movie");
            System.out.println("Enter 2 to search a theatre");
            System.out.println("Enter any other number to return home");
            int choice = input.getInteger();
            if (choice == 1) {
                System.out.print("Enter the movie name:");
                String movieName = input.getString();
                Movie searchedMovie = app.searchMovie(movieName.toLowerCase());
                if(searchedMovie == null) {
                    System.out.println("Sorry! No Results Found");
                    continue;
                }
                System.out.println(searchedMovie);
                LocalDate showDate = LocalDate.now();
                Show selectedShow;
                while (true){
                    if (showDate.isBefore(searchedMovie.getReleaseDate()))
                        showDate = searchedMovie.getReleaseDate();
                    System.out.println("Enter 1 to view shows for " + showDate);
                    System.out.println("Enter 2 to return");
                    System.out.println("Enter any other number to change date");
                    choice = input.getInteger();
                    if(choice == 2)
                        continue loop;
                    if (choice != 1) {
                        while (true) {
                            System.out.print("Enter the date in the format dd-mm-yyyy:");
                            showDate = input.getDate();
                            if ((showDate.isAfter(searchedMovie.getReleaseDate()) || showDate.isEqual(searchedMovie.getReleaseDate()) )&& !showDate.isBefore(LocalDate.now()))
                                break;
                            System.out.println("The date you selected is before the release of the movie\nPlease choose a different show date");
                        }
                    }
                    selectedShow = app.selectShowForMovie(searchedMovie, showDate,location);
                    if (selectedShow == null) {
                        showDate = showDate.plusDays(1);
                        continue;
                    }
                    break;
                }
                Screen selectedScreen;
                Theatre selectedTheatre = Retriever.getInstance().getTheatre(selectedShow.getTheatreId());
                selectedScreen = selectedTheatre.getScreen((selectedShow.getScreenName()));
                app.viewSeats(selectedScreen,selectedShow);
                System.out.print("Enter the number of seats:");
                int numberOfSeats = input.getNumberOfSeats();
                Record<Seat> selectedSeats = selectSeats(selectedShow,numberOfSeats,customer);
                if (selectedSeats.size() != 0)
                    buyTickets(selectedScreen, selectedShow, selectedTheatre, showDate, customer,selectedSeats,bookingController);
                return;
            }

            if (choice == 2) {
                System.out.print("Enter the Theatre name:");
                String theatreName = input.getString();
                Theatre searchedTheatre = app.searchTheatre(theatreName.toLowerCase(),location);
                if (searchedTheatre == null) {
                    System.out.println("Sorry! No result found");
                    continue;
                }
                System.out.println(searchedTheatre);
                LocalDate showDate = LocalDate.now();
                Show showFromTheatre;
                while (true){
                    System.out.println("Enter 1 to view shows for " + showDate);
                    System.out.println("Enter 2 to return");
                    System.out.println("Enter any other number to change date");
                    choice = input.getInteger();
                    if(choice == 2)
                        continue loop;
                    if (choice != 1) {
                        while (true) {
                            System.out.print("Enter the date in the format dd-mm-yyyy:");
                            showDate = input.getDate();
                            if (showDate.isAfter(LocalDate.now()))
                                break;
                            System.out.println("The date you selected is already over\nPlease choose a different show date");
                        }
                    }
                    showFromTheatre = app.selectShowFromTheatre(searchedTheatre, showDate);
                    if (showFromTheatre == null) {
                        showDate = showDate.plusDays(1);
                        continue;
                    }
                    break;
                }
                Screen selectedScreen = searchedTheatre.getScreen(showFromTheatre.getScreenName());
                app.viewSeats(selectedScreen,showFromTheatre);
                System.out.print("Enter the number of seats: ");
                int numberOfSeats = input.getNumberOfSeats();
                Record<Seat> selectedSeats = selectSeats(showFromTheatre,numberOfSeats,customer);
                if (selectedSeats.size() != 0)
                    buyTickets(selectedScreen, showFromTheatre, searchedTheatre, showDate,customer, selectedSeats,bookingController);
                return;
            }
            break;
        }
    }

    private void viewMyBookings(Customer customer, BookingController bookingController){
        if(customer.getMyTickets().size() == 0) {
            System.out.println("No Previous Bookings");
            return;
        }
        for(Ticket ticket: customer.getMyTickets())
            System.out.println(ticket.getDetails());
        System.out.print("Enter the id of the ticket to view full details:");
        int bookingId;
        Ticket ticketSelected;
        while (true){
            bookingId = input.getInteger();
            ticketSelected = customer.getTicket(bookingId);
            if(ticketSelected != null)
                break;
            System.out.print("Enter a valid booking id to select the ticket : ");
        }
        System.out.println(ticketSelected);
        System.out.println("Enter 1 to add rating");
        if( ticketSelected.getTicketStatus() == TicketStatus.BOOKED){
            System.out.println("Enter 2 to cancel the ticket");
        }
        System.out.println("Enter any other number to return");
        int choice = input.getInteger();
        if(choice == 1) {
            int rating = customer.getMyRating(ticketSelected.getMovieShowing());
            if(rating == 0) {
                System.out.print("Enter the rating for the movie between 1 - 10 : ");
                rating  = input.getRating();
                customer.rateMovie(rating,ticketSelected.getMovieShowing());
            }
            System.out.println("My rating:\n"+rating+"/10");
        }
        else if(choice == 2 && ticketSelected.getTicketStatus() == TicketStatus.BOOKED)
            cancelTicket(bookingId,customer,bookingController);
    }

    private void viewProfile(Customer customer){
        System.out.println("PROFILE\n");
        System.out.println(customer);
        System.out.println("Enter 1 to edit profile");
        System.out.println("Enter any other number to return");
        if(input.getInteger() != 1)
            return;
        System.out.println("1. Edit Name\n2. Edit phone number\n3. Edit email address\n4. Edit Address");
        System.out.println("Enter any other number to go back");
        switch (input.getInteger()){
            case 1->{
                System.out.print("Enter a new name:");
                String name = input.getName();
                customer.setName(name);
            }
            case 2 ->{
                System.out.print("Enter your phone number:");
                long phoneNumber = input.getPhoneNumber();
                try {
                    customer.setPhoneNumber(phoneNumber);
                }catch (InvalidDataException e) {
                    System.out.println(e.getMessage()+"\nUnable to change the phone number ");
                }
            }
            case 3 ->{
                System.out.print("Enter your email address:");
                String emailAddress = input.getEmailId();
                try {
                    customer.setEmailAddress(emailAddress);
                    System.out.println("The username has been changed to "+emailAddress);
                }catch (InvalidDataException e){
                    System.out.println(e.getMessage()+"\nUnable to change the email address");
                }

            }
            case 4 ->{
                System.out.println("Enter your address\n");
                Address address = input.getAddress();
                customer.setAddress(address);
            }
            default ->{
                return;
            }
        }
        System.out.println("Profile has been saved successfully");
    }


    private Record<Seat> selectSeats(Show selectedShow,int numberOfSeats,Customer customer){
        Record<Seat> selectedSeats = new Record<>();
        for(int counter = 0;counter < numberOfSeats; counter++) {
            System.out.print("Enter the seat number to select seat "+(counter+1)+" (Example: A10) : ");
            Seat seat;
            while (true){
                String seatNumber = input.getSeatNumber();
                try {
                    seat = customer.selectSeat(selectedShow, seatNumber);
                    break;
                } catch (InvalidBookingException e) {
                    System.out.print(e.getMessage() + "\nPlease choose a different seat:");
                }
            }
            if(seat!=null && !selectedSeats.contains(seat))
                selectedSeats.add(seat);
            else{
                System.out.println("The seat is already selected by you\nPlease choose a different seat");
                counter--;
            }
        }
        return selectedSeats;
    }

    private void buyTickets(Screen selectedScreen, Show showSelected, Theatre theatre, LocalDate showDate, Customer customer, Record<Seat> seats, BookingController bookingController){
        System.out.println("\n"+showSelected.getMovieShowing().getMovieName().toUpperCase() + "\n" +
                showSelected.getMovieShowing().getLanguage() + "," + showSelected.getMovieShowing().getDimensionType().getDimension() + "\n" +
                showDate + "|" + showSelected.getStartTime() +"   ("+selectedScreen.getScreenName()+")\n" +
                theatre.getName() + "," +theatre.getAddress().getBuildingNameAndNumber()+ "," +  theatre.getAddress().getArea());
        System.out.println("Number of tickets = " + seats.size() +"\n" +
                "Cost per seat     = Rs." + showSelected.getCostPerSeat() + "\n" +
                "Total cost        = Rs." + seats.size()*showSelected.getCostPerSeat());
        System.out.println("Enter 1 to pay");
        System.out.println("Enter any other number to cancel");
        if (input.getInteger() != 1)
            return;
        int bookingId = bookingController.bookTicket(seats,showSelected,showDate,customer);
        System.out.println("The ticket(s) has been successfully booked with the booking id - "+ bookingId);
    }

    private void cancelTicket(int bookingId,Customer customer,BookingController bookingController){
        try {
            bookingController.cancelTicket(bookingId,customer);
            System.out.println("The ticket with the booking id - "+bookingId +" has been cancelled");
        }
        catch (InvalidBookingException e){
            System.out.println(e.getMessage()+"\nNot able to cancel tickets");
        }
    }


}
