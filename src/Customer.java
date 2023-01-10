public class Customer extends User{
    private long phoneNumber;
    private long emailAddress;
    private Address address;


    public Customer(String userName, String password) {
        super(userName, password);
    }
}
