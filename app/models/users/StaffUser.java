package models.users;

import org.mindrot.jbcrypt.BCrypt;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * This is used to define common properties to Mods and
 * Admins, as well as their shared functions
 */
public abstract class StaffUser extends BaseUser {

    //**********************************************************
    // properties
    //**********************************************************

    @Required(message="Campo \"email\" é obrigatório.")
    @MaxLength(value=60)
    protected String email;

    @Required(message="Campo \"nome\" é obrigatório.")
    @MaxLength(value=50)
    protected String name;

    @Required(message="Campo \"senha\" inválido.")
    protected String password;

    //**********************************************************
    // getters and setters
    //**********************************************************

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    //**********************************************************
    // validation
    //**********************************************************

    public abstract void checkEmail();

    //**********************************************************
    // business
    //**********************************************************

    public abstract void updatePassword(String password);

    protected String createPassword(String clearString) {
        return clearString != null ? BCrypt.hashpw(clearString, BCrypt.gensalt()) : null;
    }

    public boolean isPasswordCorrect(String candidate) {
        return !(candidate == null || getPassword() == null) && BCrypt.checkpw(candidate, getPassword());
    }
}
