import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Input {
    Scanner s = new Scanner(System.in);
    public int getInteger(){
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

    public String getString(){
        return s.nextLine();
    }

    public long getLong() {
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

    public long getPhoneNumber(){
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

    public String getName(){
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

    public double getDouble(){
        while(true){
            try{
                return Double.parseDouble(getString());
            }
            catch (Exception e){
                System.out.print("Enter a valid number:");
            }
        }
    }

    public LocalDate getDate(){
        while (true){
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(getString(),formatter);
                if(LocalDate.now().isAfter(date) || LocalDate.now().isEqual(date)){
                    return date;
                }
                else
                    System.out.print("The date you entered is already over.\nPlease enter a valid date");
            }
            catch (Exception e){
                System.out.print("Enter the date in the correct format dd-mm-yyyy Example :01-01-2000:");
            }
        }
    }
    public Genre getGenre()
    {
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
                    System.out.print(i.ordinal()+1+" "+i.name()+"\t\t");
                    if(c%3==0)
                        System.out.println();
                    c++;
                }
                System.out.print("\nEnter a valid genre from the list:");
            }
        }
    }

    public String getEmailId()
    {
        while(true)
        {
            String emailAddress = getString();
            if(isValidEmail(emailAddress))
                return emailAddress;
            else
                System.out.print("Enter a valid email address:");
        }
    }
    public boolean isValidEmail(String emailId)
    {
        return Pattern.compile(("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"))
                .matcher(emailId)
                .matches();
    }

    public long getPinCode(){
        while (true){
            long pinCode = getLong();
            if ((pinCode + "").length() == 6)
                return pinCode;
            else
                System.out.print("Enter a valid pin code:");
        }
    }

    public Address getAddress(){
        System.out.print("Enter building number and name:");
        String buildingName = getString();
        System.out.print("Enter the street name:");
        String streetName = getString();
        System.out.println("Enter the area/locality name");
        String areaName = getString();
        System.out.print("Enter the landmark nearby:");
        String landmark = getString();
        System.out.print("Enter the city:");
        String city = getString();
        System.out.print("Enter the pin code:");
        long pinCode = getPinCode();
        System.out.print("Enter the state:");
        String state = getString();
        return new Address(buildingName,streetName,areaName,landmark,city,pinCode,state);
    }

    public Certificate getCertificate(){
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

    public Language getLanguage(){
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

    public DimensionType getDimensionType(){
        while(true)
        {
            DimensionType[] dimensionTypes = DimensionType.values();

            try {
                String dimension = getString().toUpperCase().trim().replace(" ","");
                return DimensionType.valueOf(dimension);
            }
            catch(Exception e)
            {
                for(DimensionType i:dimensionTypes)
                    System.out.println(i.ordinal()+1+" "+i.name());
                System.out.print("Enter a valid dimension from the list:");
            }
        }
    }

    public LocalTime getTime()  {
        System.out.print("Enter the hour(0 - 23):");
        int hour = getHour();
        System.out.print("Enter the minute (0 - 59):");
        int minute = getMinute();
        return LocalTime.of(hour,minute);
    }

    public int getHour(){
        while (true){
            int hour = getInteger();
            if(hour> -1 && hour< 24)
                return hour;
            System.out.print("Enter a valid hour (0 - 23):");
        }
    }

    public int getMinute(){
        while (true){
            int minute  = getInteger();
            if(minute > -1 && minute < 60)
                return minute;
            System.out.print("Enter a valid minute (0-59):");
        }
    }

}
