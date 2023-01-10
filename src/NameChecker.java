public class NameChecker {
    public static void checkName(String name) throws InvalidName{
        for(char i:name.toCharArray())
        {
            if(Character.isDigit(i))
                throw new InvalidName(2);
            if(((int)i>=33 && (int)i<=47) ||((int)i>=58 && (int)i<=64) || ((int)i>=91 && (int)i<=96) || ((int)i>=123 && (int)i<=126))
                throw new InvalidName(3);
        }
        if(name.length()<3)
            throw new InvalidName(1);
    }

    protected static String getMessage(int exceptionNumber)
    {
        return switch (exceptionNumber) {
            case 1 -> "Name is too short at least contain 3 characters";
            case 2 -> "Name should not contain numbers";
            case 3 -> "Name should not contain any special characters";
            default -> null;
        };
    }
}
