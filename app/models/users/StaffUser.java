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
    private String email;

    @Required(message="Campo \"nome\" é obrigatório.")
    @MaxLength(value=50)
    private String name;

    @Required(message="Campo \"senha\" inválido.")
    private String password;

    //**********************************************************
    // getters and setters
    //**********************************************************

    public String getEmail() {
        return email;
    }

    public StaffUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public StaffUser setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public StaffUser setPassword(String password) {
        this.password = createPassword(password);
        return this;
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
