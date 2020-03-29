package com.vjay.libararymanagement;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtil {
    public static String getTodayDateString() {
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDate.format(new Date());
    }
}
