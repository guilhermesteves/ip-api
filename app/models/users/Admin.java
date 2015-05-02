package models.users;

import com.fasterxml.jackson.databind.JsonNode;
import models.common.Validator;
import models.history.Traceable;
import models.common.DisplayableException;
import models.common.json.JsonContext;
import models.common.json.JsonHelper;
import models.common.json.JsonSerializable;
import models.common.constants.Constants;
import models.common.constants.FIELDS;
import models.common.constants.MESSAGES;
import models.common.db.factory.SimpleDAOFactory;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * All methods belonging the Admins access and business is here
 */
public class Admin extends StaffUser implements JsonSerializable, Traceable<Admin> {

    //**********************************************************
    // properties
    //**********************************************************

    // This is here only to remind everyone thatit exists,
    // but all super admin must be setted directly in
    // database. This will, hopefully, any security breach.
    private Boolean superAdmin = false;

    //**********************************************************
    // getters and setters
    //**********************************************************

    public Admin setId(String id) {
        this.id = id;
        return this;
    }

    public Admin setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public Admin setEmail(String email) {
        this.email = email;
        return this;
    }

    public Admin setName(String name) {
        this.name = name;
        return this;
    }

    public Admin setPassword(String password) {
        this.password = password;
        return this;
    }

    //**********************************************************

    public Boolean getSuperAdmin() {
        return superAdmin;
    }

    //**********************************************************
    // validation
    //**********************************************************

    public void checkEmail() {
        Admin admin = loadByEmail(getEmail());
        if(admin != null && (getId() == null || !getId().equals(admin.getId())))
            throw new DisplayableException(MESSAGES.ADMIN.EMAIL_ALREADY_EXISTS);
    }

    //**********************************************************
    // business
    //**********************************************************

    public void updatePassword(String password) {
        setPassword(password);
        SimpleDAOFactory.getInstance().getAdminDAO().update(this);
    }

    //**********************************************************
    // dao
    //**********************************************************

    public void create() {
        checkEmail();

        Validator.validate(this);

        SimpleDAOFactory.getInstance().getAdminDAO().create(this);
    }

    public static Admin load(String id) {
        return SimpleDAOFactory.getInstance().getAdminDAO().load(id, Admin.class);
    }

    public void update(Admin admin) {

        if (this.getActive() != admin.getActive())
            this.setActive(admin.getActive());

        if (!this.getName().equals(admin.getName()))
            this.setName(admin.getName());

        if (!this.getEmail().equals(admin.getEmail()))
            this.setEmail(admin.getEmail());

        checkEmail();

        SimpleDAOFactory.getInstance().getAdminDAO().update(this);
    }

    public static void delete(String id) {
        SimpleDAOFactory.getInstance().getAdminDAO().delete(id, Admin.class);
    }

    //**********************************************************

    public static Admin loadByEmail(String email) {
        return SimpleDAOFactory.getInstance().getAdminDAO().loadByEmail(email);
    }

    public static Admin loadByName(String name) {
        return SimpleDAOFactory.getInstance().getAdminDAO().loadByName(name);
    }

    //**********************************************************

    @Override
    public void activate() {
        SimpleDAOFactory.getInstance().getAdminDAO().activate(getId());
    }

    @Override
    public void deactivate() {
        SimpleDAOFactory.getInstance().getAdminDAO().deactivate(getId());
    }

    //**********************************************************
    // changes
    //**********************************************************

    @Override
    public String getDetails() {
        return "Admin. Nome: "+ getName() +" | SuperAdmin: "+ getSuperAdmin();
    }

    @Override
    public List<String> getChanges(Admin admin) {

        List<String> changes = new ArrayList<>();

        if (this.getActive() != admin.getActive())
            changes.add(Constants.getFieldChange("Ativo", this.getActive(), admin.getActive()));

        if (!this.getName().equals(admin.getName()))
            changes.add(Constants.getFieldChange("Nome", this.getName(), admin.getName()));

        if (!this.getEmail().equals(admin.getEmail()))
            changes.add(Constants.getFieldChange("Email", this.getEmail(), admin.getEmail()));

        if (this.getSuperAdmin() != admin.getSuperAdmin())
            changes.add(Constants.getFieldChange("Super Admin", this.getSuperAdmin(), admin.getSuperAdmin()));

        return changes;
    }

    //**********************************************************
    // json
    //**********************************************************



    @Override
    public JsonNode toJson(String context) {

        JsonHelper j = new JsonHelper();

        j.addString(FIELDS.BASE_USER.ID, getId())
                .addBoolean(FIELDS.BASE_USER.ACTIVE, getActive());

        switch (context) {

            case JsonContext.REF : {
                j.addInteger(FIELDS.BASE_USER.LOYALTY, getLoyalty());
            };break;

            default : {
                j.addString(FIELDS.BASE_USER.ID, getId())
                        .addBoolean(FIELDS.BASE_USER.ACTIVE, getActive());

                j.addString(FIELDS.BASE_USER.STAFF.EMAIL, getEmail())
                        .addString(FIELDS.BASE_USER.STAFF.NAME, getName());

                j.addBoolean(FIELDS.BASE_USER.STAFF.ADMIN.IS_SUPER_ADMIN, getSuperAdmin())
                        .addInteger(FIELDS.BASE_USER.LOYALTY, getLoyalty());
            }
        }

        return j.getJSON();
    }

    public static Admin fromJson(JsonNode json) {

        JsonHelper j = new JsonHelper(json);
        Admin admin = new Admin();

        admin.setId(j.getString(FIELDS.BASE_USER.ID))
                .setActive(j.getBoolean(FIELDS.BASE_USER.ACTIVE));

        admin.setEmail(j.getString(FIELDS.BASE_USER.STAFF.EMAIL))
                .setName(j.getString(FIELDS.BASE_USER.STAFF.NAME));

        return admin;
    }
}
