package services.pojo.responses;

public class GetBookingIdsResponse {
    private int bookingid;

    public int getBookingid() {
        return bookingid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    @Override
    public String toString() {
        return String.valueOf(bookingid);
    }
}
