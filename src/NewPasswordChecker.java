public class NewPasswordChecker {
        public static int findExceptionType(String password) throws InvalidNewPassword {
            if (!(password.length()>=8 && password.length()<=15))
                throw new InvalidNewPassword(1);
            int numberOfLowerCaseCharacters = 0;
            int numberOfUpperCaseCharacters = 0;
            int numberOfSpecialCharacters = 0;
            int numberOfDigits = 0;
            for(char i : password.toCharArray())
            {
                if(Character.isLowerCase(i))
                    numberOfLowerCaseCharacters++;
                if(Character.isUpperCase(i))
                    numberOfUpperCaseCharacters++;
                if(((int)i>=33 && (int)i<=47) ||((int)i>=58 && (int)i<=64) || ((int)i>=91 && (int)i<=96) || ((int)i>=123 && (int)i<=126))
                    numberOfSpecialCharacters++;
                if(Character.isDigit(i))
                    numberOfDigits++;
                if(i==' ')
                    throw new InvalidNewPassword(2);
            }
            if(numberOfUpperCaseCharacters == 0)
                throw new InvalidNewPassword(3);
            if(numberOfLowerCaseCharacters == 0)
                throw new InvalidNewPassword(4);
            if(numberOfDigits == 0)
                throw new InvalidNewPassword(5);
            if(numberOfSpecialCharacters == 0)
                throw new InvalidNewPassword(6);
            return 0;

        }
        protected static String printMessage(int exceptionNumber) {
            return switch (exceptionNumber) {
                case 1 -> "The password must be in the range of (8-15) characters only";
                case 2 -> "The password should not contain a space";
                case 3 -> "The password must contain at least one upper case character (A-Z)";
                case 4 -> "The password must contain at least one lower case character (a-z)";
                case 5 -> "The password must contain at least one digit (0-9)";
                case 6 -> "The password must contain at least one special character (@,&,....)";
                default -> null;
            };
        }
    }

