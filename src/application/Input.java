package application;

import library.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

 class Input{
    private final Scanner scanner;
     Input() {
        scanner = new Scanner(System.in);
     }

     int getInteger(){
        int number;
        while(true){
            try {
                number = Integer.parseInt(getString());
                if(number>=0)
                    return number;
                else{
                    System.out.print("Enter a non negative number: ");
                }
            } catch (Exception e) {
                System.out.print("Enter a valid number:");
            }
        }
     }

     String getString(){
        return scanner.nextLine();
     }

     long getLong() {
        while(true)
        {
            try{
                return Long.parseLong(getString());
            }
            catch (Exception e){
                System.out.print("Enter a valid number:");
            }
        }
     }

     long getPhoneNumber(){
        while (true) {
            String phoneNumber = getString();
            if(phoneNumber.length()==10)
            {
                try {
                    return Long.parseLong(phoneNumber);
                }
                catch (Exception e){
                    System.out.print("Enter a valid phone number: ");
                }
            }
        }
     }


     String getName(){
        while (true){
            try {
                String name = getString();
                NameChecker.checkName(name);
                return name;
            }
            catch (InvalidName invalidName) {
                System.out.println(invalidName.getMessage());
                System.out.print("Enter a valid name:");
            }
        }
     }

     double getDouble(){
        while(true){
            try{
                return Double.parseDouble(getString());
            }
            catch (Exception e){
                System.out.print("Enter a valid number:");
            }
        }
     }

     LocalDate getDate(){
        while (true){
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                return LocalDate.parse(getString(), formatter);
            }
            catch (Exception e){
                System.out.print("Enter the date in the correct format dd-mm-yyyy Example :01-01-2000:");
            }
        }
     }
     Genre getGenre() {
        while(true)
        {
            Genre[] genres = Genre.values();

            try {
                String genre = getString().toUpperCase().trim().replace(" ","_");
                return Genre.valueOf(genre);
            }
            catch(Exception e)
            {
                int c = 1;
                for(Genre i:genres)
                {
                    System.out.print(i.name()+"\t   ");
                    if(c%3==0)
                        System.out.println();
                    c++;
                }
                System.out.print("\nEnter a valid genre from the list:");
            }
        }
     }

    String getEmailId() {
        while(true)
        {
            String emailAddress = getString();
            if(isValidEmail(emailAddress))
                return emailAddress;
            else
                System.out.print("Enter a valid email address:");
        }
     }

     boolean isValidEmail(String emailId) {
        return Pattern.compile(("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"))
                .matcher(emailId)
                .matches();
     }

     long getPinCode(){
        while (true){
            long pinCode = getLong();
            if ((pinCode + "").length() == 6)
                return pinCode;
            else
                System.out.print("Enter a valid pin code:");
        }
     }
     Address getAddress(){
        System.out.print("Enter building number and name:");
        String buildingName = getString();
        System.out.print("Enter the area/locality name:");
        String areaName = getString();
        System.out.print("Enter the city:");
        String city = getString();
        System.out.print("Enter the pin code:");
        long pinCode = getPinCode();
        System.out.print("Enter the state:");
        State state = getState();
        return new Address(buildingName,areaName,city,pinCode,state);
     }

     State getState(){
        while (true){
            State[] states = State.values();
            String stateName = getString().toUpperCase().replaceAll(" ","_");
            try{
                return State.valueOf(stateName);
            }
            catch (Exception e){
                for(State state: states)
                    System.out.println(state.ordinal()+1+" "+state.name());
                System.out.print("Enter a valid state name from the list:");
            }
        }
     }
     Certificate getCertificate(){
        while(true)
        {
            Certificate[] certificates = Certificate.values();

            try {
                String certificate = getString().toUpperCase().trim().replace(" ","");
                return Certificate.valueOf(certificate);
            }
            catch(Exception e)
            {
                for(Certificate i:certificates)
                    System.out.println(i.ordinal()+1+" "+i.name());
                System.out.print("Enter a valid certificate from the list:");
            }
        }
     }

     Language getLanguage(){
        while(true)
        {
            Language[] languages = Language.values();

            try {
                String language = getString().toUpperCase().trim().replace(" ","");
                return Language.valueOf(language);
            }
            catch(Exception e)
            {
                for(Language i:languages)
                    System.out.println(i.ordinal()+1+" "+i.name());
                System.out.print("Enter a valid language from the list:");
            }
        }
     }

     DimensionType getDimensionType(){
        while(true)
        {
            DimensionType[] dimensionTypes = DimensionType.values();
            String dimension = getString().toUpperCase();
            for (DimensionType dimensionType : dimensionTypes) {
                if (dimensionType.getDimension().equals(dimension))
                    return dimensionType;
            }
            for(DimensionType i:dimensionTypes)
                System.out.println(i.ordinal()+1+" "+i.getDimension());
            System.out.print("Enter a valid dimension from the list:");
        }
     }

     LocalTime getTime()  {
        System.out.print("Enter the hour(0 - 23):");
        int hour = getHour();
        System.out.print("Enter the minute (0 - 59):");
        int minute = getMinute();
        return LocalTime.of(hour,minute);
     }

     int getHour(){
        while (true){
            int hour = getInteger();
            if(hour> -1 && hour< 24)
                return hour;
            System.out.print("Enter a valid hour (0 - 23):");
        }
     }

     int getMinute(){
        while (true){
            int minute  = getInteger();
            if(minute > -1 && minute < 60)
                return minute;
            System.out.print("Enter a valid minute (0-59):");
        }
     }
     String getNewPassword() {
        while(true){
            try{
                String password = getString();
                NewPasswordChecker.findExceptionType(password);
                return password;
            }
            catch (InvalidNewPassword e) {
                System.out.println(e.getMessage());
                System.out.print("Enter a valid password:");
            }
        }
     }

     String getSeatNumber(){
        while(true){
            String seatNumber = getString().toUpperCase();
            if(seatNumber.length() == 3){
                if (Character.isUpperCase(seatNumber.charAt(0)) && Character.isDigit(seatNumber.charAt(1)) && Character.isDigit(seatNumber.charAt(2))) {
                    return seatNumber;
                }
            }
            System.out.print("Enter a valid seat number in the format rowName ColumnNumber Eg - A11 :");
        }
     }

     int getRating(){
        while(true){
            int rating = getInteger();
            if(rating >= 1 && rating <= 10)
                return rating;
            System.out.print("Enter a valid rating between 1 to 10 :");
        }
     }

     int getNumberOfSeats(){
        while(true){
            int numberOfSeats = getInteger();
            if(numberOfSeats >= 1 && numberOfSeats <= 10)
                return numberOfSeats;
            System.out.print("The number of seats can be selected is 1 to 10\nEnter the number of seats:");
        }
     }

     int getNumberOfRows(){
        while(true){
            int numberOfRows = getInteger();
            if(numberOfRows >=6 && numberOfRows <= 26)
                return numberOfRows;
            System.out.print("The number of rows can be added is 6 to 26\nEnter the number of rows:");
        }
     }

     int getNumberOfColumns(){
        while(true){
            int numberOfColumns = getInteger();
            if(numberOfColumns >= 6  && numberOfColumns <= 50)
                return numberOfColumns;
            System.out.print("The number of columns can be added is 6 to 50\nEnter the number of columns:");
        }
     }

     double getSeatCost(){
        while(true){
            double seatCost = getDouble();
            if(seatCost >= 30  && seatCost <= 2000)
                return seatCost;
            System.out.print("The seat cost will be in the range Rs.30 to Rs.2000\nEnter the seat cost:");
        }
     }

     int getIntermissionTime(){
        while(true){
            int intermissionTime = getInteger();
            if(intermissionTime >= 0  && intermissionTime <= 25)
                return intermissionTime;
            System.out.print("The intermission time will be in the 0 to 25 minutes\nEnter the intermission time:");
        }
     }

     int getNumberOfScreens(){
        while(true){
            int numberOfScreens = getInteger();
            if(numberOfScreens > 0  && numberOfScreens <= 30)
                return numberOfScreens;
            System.out.print("The number of screens must be greater than 0 and less than 30\nEnter the number of screens:");
        }
     }

    int getDurationOfMovie(){
         while (true){
             int numberOfScreens = getInteger();
             if(numberOfScreens >= 50)
                 return numberOfScreens;
             System.out.print("Enter the duration of the movie above 50 minutes:");
         }
     }
}
