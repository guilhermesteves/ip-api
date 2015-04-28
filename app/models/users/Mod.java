package models.users;

import com.fasterxml.jackson.databind.JsonNode;
import models.IbBoardList;
import models.IbBoard;
import models.common.DisplayableException;
import models.common.json.JsonContext;
import models.common.json.JsonHelper;
import models.common.json.JsonSerializable;
import models.history.Traceable;
import models.common.constants.Constants;
import models.common.constants.FIELDS;
import models.common.constants.MESSAGES;
import models.common.db.factory.SimpleDAOFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * All methods belonging the Mods access and business is here
 */
public class Mod extends StaffUser implements JsonSerializable, Traceable<Mod> {

    //**********************************************************
    // properties
    //**********************************************************

    private IbBoardList moderatingBoards;

    private ModLevel accessLevel;

    //**********************************************************
    // getters and setters
    //**********************************************************

    public IbBoardList getModeratingBoards() {
        return moderatingBoards;
    }

    public Mod setModeratingBoards(IbBoardList moderatingBoards) {
        this.moderatingBoards = moderatingBoards;
        return this;
    }

    public ModLevel getAccessLevel() {
        return accessLevel;
    }

    public Mod setAccessLevel(ModLevel accessLevel) {
        this.accessLevel = accessLevel;
        return this;
    }

    //**********************************************************
    // validation
    //**********************************************************
    
    public void checkEmail() {
        Mod mod = loadByEmail(getEmail());
        if(mod != null && (getId() == null || !getId().equals(mod.getId())))
            throw new DisplayableException(MESSAGES.MOD.EMAIL_ALREADY_EXISTS);
    }

    //**********************************************************
    // business
    //**********************************************************

    public void updatePassword(String password) {
        setPassword(password);
        SimpleDAOFactory.getInstance().getModDAO().update(this);
    }

    //**********************************************************
    // dao
    //**********************************************************
    
    public void create() {
        checkEmail();
        SimpleDAOFactory.getInstance().getModDAO().create(this);
    }

    public static Mod load(String id) {
        return SimpleDAOFactory.getInstance().getModDAO().load(id, Mod.class);
    }

    public void update(Mod mod) {

        if (this.getActive() != mod.getActive())
            this.setActive(mod.getActive());

        if (!this.getName().equals(mod.getName()))
            this.setName(mod.getName());

        if (!this.getEmail().equals(mod.getEmail()))
            this.setEmail(mod.getEmail());

        if (!this.getModeratingBoards().equals(mod.getModeratingBoards()))
            this.setModeratingBoards(mod.getModeratingBoards());

        if (!this.getAccessLevel().equals(mod.getAccessLevel()))
            this.setAccessLevel(mod.getAccessLevel());

        checkEmail();

        SimpleDAOFactory.getInstance().getModDAO().update(this);
    }

    public static void delete(String id) {
        SimpleDAOFactory.getInstance().getModDAO().delete(id, Mod.class);
    }

    //**********************************************************

    public static Mod loadByEmail(String email) {
        return SimpleDAOFactory.getInstance().getModDAO().loadByEmail(email);
    }

    //**********************************************************

    @Override
    public void activate() {
        SimpleDAOFactory.getInstance().getModDAO().activate(getId());
    }

    @Override
    public void deactivate() {
        SimpleDAOFactory.getInstance().getModDAO().deactivate(getId());
    }

    //**********************************************************
    // changes
    //**********************************************************

    @Override
    public String getDetails() {
        return "Mod. Nome: "+ getName() +" | Nível: "+ getAccessLevel().getDescription();
    }

    @Override
    public List<String> getChanges(Mod mod) {

        List<String> changes = new ArrayList<>();

        if (this.getActive() != mod.getActive())
            changes.add(Constants.getFieldChange("Ativo", this.getActive(), mod.getActive()));

        if (!this.getName().equals(mod.getName()))
            changes.add(Constants.getFieldChange("Nome", this.getName(), mod.getName()));

        if (!this.getEmail().equals(mod.getEmail()))
            changes.add(Constants.getFieldChange("Email", this.getEmail(), mod.getEmail()));

        if (!this.getModeratingBoards().equals(mod.getModeratingBoards()))
            changes.add(Constants.getFieldChange("Boards:", this.getModeratingBoardsNames(), mod.getModeratingBoardsNames()));

        if (!this.getAccessLevel().equals(mod.getAccessLevel()))
            changes.add(Constants.getFieldChange("Level:", this.getAccessLevel().getDescription(), mod.getAccessLevel().getDescription()));

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
                j.addString(FIELDS.BASE_USER.STAFF.EMAIL, getEmail())
                        .addString(FIELDS.BASE_USER.STAFF.NAME, getName());

                j.add(FIELDS.BASE_USER.STAFF.MOD.MODERATING_BOARDS, getModeratingBoards().toJson(context))
                        .add(FIELDS.BASE_USER.STAFF.MOD.ACCESS_LEVEL, getAccessLevel().toJson(context))
                        .addInteger(FIELDS.BASE_USER.LOYALTY, getLoyalty());
            }
        }

        return j.getJSON();
    }

    public static Mod fromJson(JsonNode json) {

        JsonHelper j = new JsonHelper(json);
        Mod mod = new Mod();

        mod.setId(j.getString(FIELDS.BASE_USER.ID))
                .setActive(j.getBoolean(FIELDS.BASE_USER.ACTIVE));

        mod.setEmail(j.getString(FIELDS.BASE_USER.STAFF.EMAIL))
                .setName(j.getString(FIELDS.BASE_USER.STAFF.NAME));

        mod.setModeratingBoards(IbBoardList.fromJson(j.get(FIELDS.BASE_USER.STAFF.MOD.MODERATING_BOARDS)))
                .setAccessLevel(ModLevel.fromJson(FIELDS.BASE_USER.STAFF.MOD.ACCESS_LEVEL, json));

        return mod;
    }

    //**********************************************************
    // helpers
    //**********************************************************

    private String getModeratingBoardsNames() {
        String result = "";
        IbBoardList boards = getModeratingBoards();

        for (IbBoard b : boards)
            result += b.getName() + ", ";

        return !("").equals(result) ? result.substring(0, result.length()-1) : "";
    }
}