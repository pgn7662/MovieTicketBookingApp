package library;

public class InvalidName extends Exception{
    protected InvalidName(int exceptionNumber)
    {
        super(NameChecker.getMessage(exceptionNumber));
    }
}
