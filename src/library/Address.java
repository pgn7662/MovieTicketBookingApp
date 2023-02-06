package library;

public class Address {
    private final String buildingNameAndNumber;
    private final String area;
    private final String city;
    private final long pinCode;
    private final State state;

    public Address(String buildingNameAndNumber, String area,String city, long pinCode, State state) {
        this.buildingNameAndNumber = buildingNameAndNumber;
        this.area = area;
        this.city = city;
        this.pinCode = pinCode;
        this.state = state;
    }

    public String getBuildingNameAndNumber() {
        return buildingNameAndNumber;
    }

    public String getArea() {
        return area;
    }

    public String getCity() {
        return city;
    }

    public long getPinCode() {
        return pinCode;
    }

    @Override
    public String toString() {
        return buildingNameAndNumber+", "+area+"\n"+
                city+" - "+pinCode+"\n"+
                state;
    }
}
