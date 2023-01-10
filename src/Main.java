import java.time.LocalTime;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Input input = new Input();
        LocalTime time = input.getTime();
        System.out.println(time);
    }
}