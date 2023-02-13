package library;
public class Authenticator {
    public User login(String userName, String password)
    {
        if(userName.equals(Database.getInstance().getAdmin(this).getUserName()) && password.equals(Database.getInstance().getAdmin(this).getPassword()))
            return Database.getInstance().getAdmin(this);
        else{
            for(Customer customer:Database.getInstance().getCustomerList(this)){
                if(customer.getUserName().equals(userName) && customer.getPassword().equals(password))
                    return customer;
            }
        }
        return null;
    }

    public Customer signUp(String name, String emailId,long phoneNumber ,String password)
    {
        Customer customer = new Customer(name,emailId,phoneNumber,password);
        Database.getInstance().addCustomer(customer,this);
        return customer;
    }

    public boolean isEmailAvailable(String emailAddress){
        return Database.getInstance().isEmailAvailable(emailAddress);
    }

    public boolean isPhoneNumberAvailable(long phoneNumber){
        return Database.getInstance().isPhoneNumberAvailable(phoneNumber);
    }

}
