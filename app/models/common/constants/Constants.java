package models.common.constants;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * All Constants used in application
 */
public class Constants {

    //**********************************************************
    // properties
    //**********************************************************

    // Application Default Date Pattern
    public static final String DATE_PATTERN = "dd/MM/yyyy HH:mm:ss";

    // Auth constants
    public static final String BASIC = "Basic";
    public static final String BEARER = "Bearer";

    public static final String USER_AUTH = "userAuth";
    public static final String MOD_AUTH = "modAuth";
    public static final String ADMIN_AUTH = "adminAuth";

    public static final String AUTH_CODE = "authCode";
    public static final String TOKEN = "token";
    public static final String REFRESH_TOKEN = "refreshToken";
    public static final String EXPIRES_IN = "expiresIn";

    //**********************************************************
    // format text
    //**********************************************************
    // TODO: PUT IN A RESOURCE BUNDLE
    public static String formatYesNo(Boolean value) {
        return value ? "Sim" : "Não";
    }

    public static String getFieldChange(String field, String oldValue, String newValue) {
        return field +": "+ oldValue +" -> "+ newValue + Constants.NEW_LINE;
    }

    public static String getFieldChange(String field, Boolean oldValue, Boolean newValue) {
        return field +": "+ Constants.formatYesNo(oldValue) +" -> "+ Constants.formatYesNo(newValue) + Constants.NEW_LINE;
    }

    public static String getFieldChange(String field, Integer oldValue, Integer newValue) {
        return field +": "+ String.valueOf(oldValue) +" -> "+ String.valueOf(newValue) + Constants.NEW_LINE;
    }

    public static final String NEW_LINE = "\n";
}
