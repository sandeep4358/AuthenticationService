package com.javafruit.AuthenticationService.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtility {
    public static String getCurrentDateTime(){
        SimpleDateFormat formatter =   new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        return formatter.format(new Date());
    }
}
