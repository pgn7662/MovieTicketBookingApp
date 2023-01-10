public enum SeatStatus {
    AVAILABLE("◻︎"),
    BOOKED("◼︎");
   private String emoji;

    SeatStatus(String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }
}
