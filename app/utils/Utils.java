package utils;

import org.joda.time.DateTime;

import java.util.List;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * A lot of methods to be used in the Application
 */
public class Utils {

    public static boolean isNullOrEmpty(String value) {
        return (value == null || value.trim().isEmpty());
    }

    public static boolean isBeforeOrEqual(DateTime d1, DateTime d2){
        d1 = d1.withTimeAtStartOfDay();
        d2 = d2.withTimeAtStartOfDay();
        return d1.isEqual(d2) || d1.isBefore(d2);
    }

    public static boolean isAfterOrEqual(DateTime d1, DateTime d2){
        d1 = d1.withTimeAtStartOfDay();
        d2 = d2.withTimeAtStartOfDay();
        return d1.isEqual(d2) || d1.isAfter(d2);
    }

    public static String getCollectionFromClass(Class _class) {
        return _class.getSimpleName().toLowerCase();
    }
}
