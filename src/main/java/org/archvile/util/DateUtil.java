package org.archvile.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");

    public static String nowPlus(long durationInMilliseconds) {
        return dtf.print(new DateTime().plus(durationInMilliseconds));
    }

}
