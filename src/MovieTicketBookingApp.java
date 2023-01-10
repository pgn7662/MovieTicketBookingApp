import java.util.ArrayList;

public class MovieTicketBookingApp {
    Input input = new Input();
    public Movie getMovieFromList(ArrayList<Movie> movieList){
        Movie selectedMovie = null;
        if(movieList.size() == 0)
            System.out.println("Search not found");
        else{
            for(int counter = 0; counter < movieList.size() ; counter++){
                System.out.println(String.format("%2d",(counter+1))+" "+movieList.get(counter).getMovieName());
            }
            System.out.print("Enter the serial number to select a movie:");
            while(true){
                int index = input.getInteger() - 1;
                if (index < movieList.size() && index > 0) {
                    selectedMovie = movieList.get(index);
                    break;
                }
                else
                    System.out.print("Enter a valid serial number:");
            }
        }
        return selectedMovie;
    }

    public Theatre getTheatreFromList(ArrayList<Theatre> theatreList){
        Theatre selectedTheatre = null;
        if(theatreList.size() == 0)
            System.out.println("Search not found");
        else {
            for(int counter = 0 ; counter < theatreList.size() ; counter++){
                System.out.println(String.format("%2d",(counter+1))+" "+theatreList.get(counter).getName()+" ,"+theatreList.get(counter).getAddress().getArea());
            }
            while (true){
                System.out.print("Enter the serial number to select the theatre:");
                int index = input.getInteger() - 1;
                if (index < theatreList.size() && index > 0) {
                    selectedTheatre = theatreList.get(index);
                    break;
                }
                else
                    System.out.print("Enter a valid serial number from the above list");
            }
        }
        return selectedTheatre;
    }


}
