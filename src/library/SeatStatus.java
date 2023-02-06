package library;

public enum SeatStatus {
    AVAILABLE("[_]"),
    BOOKED("[0]");

    private final String icon;
    SeatStatus(String icon) {
        this.icon = icon;
    }

    String getIcon() {
        return icon;
    }
}
