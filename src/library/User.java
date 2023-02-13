package library;

public abstract class User {
    private  String userName;
    private final String password;

    User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    String getUserName() {
        return userName;
    }

    String getPassword() {
        return password;
    }

    void setUserName(String userName,Customer customer){
        this.userName = userName;
    }
}
