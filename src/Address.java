public class Address {
    private String buildingNameAndNumber;
    private String streetName;
    private String area;
    private String landmark;
    private String city;
    private long pinCode;
    private String state;

    public Address(String buildingNameAndNumber, String streetName, String area, String landmark, String city, long pinCode, String state) {
        this.buildingNameAndNumber = buildingNameAndNumber;
        this.streetName = streetName;
        this.area = area;
        this.landmark = landmark;
        this.city = city;
        this.pinCode = pinCode;
        this.state = state;
    }

    public String getArea() {
        return area;
    }
}
