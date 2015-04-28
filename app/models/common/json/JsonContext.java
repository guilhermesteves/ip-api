package models.common.json;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Annotation to describe possibles
 * scenarios to prepare JSON from Model
 */
public class JsonContext {

    // By type
    public static final String DEFAULT = "default";
    public static final String REF = "ref"; // used in lists

    // By permissions
    public static final String USER = "user";
    public static final String MOD = "mod";
    public static final String ADMIN = "admin";
}
