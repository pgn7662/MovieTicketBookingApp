public class InvalidNewPassword extends Exception {
    protected InvalidNewPassword(int exceptionNumber) {
        super(NewPasswordChecker.printMessage(exceptionNumber));
    }
}
