package com.vjay.libararymanagement.model;

public class BookHistoryItem {

    BookingHistory bookingHistory;
    String itemKey;

    public BookingHistory getBookingHistory() {
        return bookingHistory;
    }

    public void setBookingHistory(BookingHistory bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }
}
