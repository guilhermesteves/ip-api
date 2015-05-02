package models.users;

import com.fasterxml.jackson.databind.JsonNode;
import models.common.Validator;
import models.common.constants.Constants;
import models.common.constants.FIELDS;
import models.common.db.factory.SimpleDAOFactory;
import models.common.json.JsonContext;
import models.common.json.JsonHelper;
import models.common.json.JsonSerializable;
import models.history.Traceable;
import org.joda.time.DateTime;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * All methods belonging the Users access and business is here
 */
public class User extends BaseUser implements JsonSerializable, Traceable<User> {

    //**********************************************************
    // properties
    //**********************************************************

    @Id
    @ObjectId
    protected String id;

    private String ip;

    private String visualId;

    private Integer karma;

    private String identifier;

    //**********************************************************
    // getters and setters
    //**********************************************************

    @Override
    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public User setActive(Boolean active) {
        this.active = active;
        return this;
    }

    //**********************************************************

    public String getIp() {
        return ip;
    }

    public User setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getVisualId() {
        return visualId;
    }

    public User setVisualId(String visualId) {
        this.visualId = visualId;
        return this;
    }

    public Integer getKarma() {
        return karma;
    }

    public User setKarma(Integer karma) {
        this.karma = karma;
        return this;
    }

    public String getIdentifier() {
        return identifier;
    }

    public User setIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    //**********************************************************
    // dao
    //**********************************************************

    public String generateVisualId() {
        String visualId = "";

        // TODO: Use IP

        return visualId;
    }

    //**********************************************************
    // dao
    //**********************************************************

    public void create() {
        Validator.validate(this);

        this.setDateCreation(new DateTime());

        this.setVisualId(generateVisualId());
        SimpleDAOFactory.getInstance().getUserDAO().create(this);
    }

    public static User load(String id) {
        return SimpleDAOFactory.getInstance().getUserDAO().load(id);
    }

    public void update(User user) {
        Validator.validate(this);

        if (this.getActive() != user.getActive())
            this.setActive(user.getActive());

        if (!this.getIp().equals(user.getIp())) {
            this.setIp(user.getIp());
            this.setVisualId(generateVisualId());
        }

        if (!this.getKarma().equals(user.getKarma()))
            this.setKarma(user.getKarma());

        SimpleDAOFactory.getInstance().getUserDAO().update(this);
    }

    public static void delete(String id) {
        SimpleDAOFactory.getInstance().getUserDAO().delete(id);
    }

    // Other methods

    public static User loadByVisualId(String visualId) {
        return SimpleDAOFactory.getInstance().getUserDAO().loadByVisualId(visualId);
    }

    public static User loadByIdentifier(String identifier) {
        return SimpleDAOFactory.getInstance().getUserDAO().loadByIdentifier(identifier);
    }

    @Override
    public void activate() {
        SimpleDAOFactory.getInstance().getUserDAO().activate(getId());
    }

    @Override
    public void deactivate() {
        SimpleDAOFactory.getInstance().getUserDAO().deactivate(getId());
    }



    //**********************************************************
    // changes
    //**********************************************************

    @Override
    public String getDetails() {
        return "Usuário. Id: "+ getVisualId();
    }

    public List<String> getChanges(User user) {

        List<String> changes = new ArrayList<>();

        if (this.getActive() != user.getActive())
            changes.add(Constants.getFieldChange("Ativo", this.getActive(), user.getActive()));

        if (!this.getIp().equals(user.getIp()))
            changes.add(Constants.getFieldChange("IP", this.getIp(), user.getIp()));

        if (!this.getVisualId().equals(user.getVisualId()))
            changes.add(Constants.getFieldChange("Visual ID", this.getVisualId(), user.getVisualId()));

        if (!this.getKarma().equals(user.getKarma()))
            changes.add(Constants.getFieldChange("Karma", this.getKarma(), user.getKarma()));

        if (!this.getIdentifier().equals(user.getIdentifier()))
            changes.add(Constants.getFieldChange("Identifier", this.getIdentifier(), user.getIdentifier()));

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
                j.addInteger(FIELDS.BASE_USER.LOYALTY, getLoyalty())
                        .addString(FIELDS.BASE_USER.USER.VISUAL_ID, getVisualId());
            };break;

            default : {
                j.addInteger(FIELDS.BASE_USER.LOYALTY, getLoyalty())
                        .addString(FIELDS.BASE_USER.USER.VISUAL_ID, getVisualId());

                j.addInteger(FIELDS.BASE_USER.USER.KARMA, getKarma())
                        .addString(FIELDS.BASE_USER.USER.IDENTIFIER, getIdentifier());
            }
        }

        return j.getJSON();
    }

    public static User fromJson(JsonNode json){

        JsonHelper j = new JsonHelper(json);
        User user = new User();

        user.setId(j.getString(FIELDS.BASE_USER.ID))
                .setActive(j.getBoolean(FIELDS.BASE_USER.ACTIVE));

        user.setKarma(j.getInteger(FIELDS.BASE_USER.USER.KARMA))
                .setIdentifier(FIELDS.BASE_USER.USER.IDENTIFIER);

        return user;
    }
}
