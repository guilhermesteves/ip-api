package models.history;

import com.fasterxml.jackson.databind.JsonNode;
import models.common.json.JsonSerializable;
import models.common.db.factory.SimpleDAOFactory;
import models.users.StaffUser;

import java.util.List;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 */
public class StaffHistory extends BaseTraceable implements JsonSerializable {

    //**********************************************************
    // properties
    //**********************************************************

    private StaffUser author;

    private ActionType action;

    private String actionDescription;

    //**********************************************************
    // getters and setters
    //**********************************************************

    public StaffUser getAuthor() {
        return author;
    }

    public StaffHistory setAuthor(StaffUser author) {
        this.author = author;
        return this;
    }

    public ActionType getAction() {
        return action;
    }

    public StaffHistory setAction(ActionType action) {
        this.action = action;
        return this;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public StaffHistory setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
        return this;
    }

    //**********************************************************
    // constructor
    //**********************************************************

    private StaffHistory(StaffUser author, ActionType action, String modelId, String collection, String details) {
        super(modelId, collection, details);
        this.author = author;
        this.action = action;
        this.actionDescription = author +" "+ action.getDescription() +" "+ details +" em "+ timestamp;
    }

    private StaffHistory(StaffUser author, ActionType action, String modelId, String collection, String details, List<String> changes) {
        super(modelId, collection, details, changes);
        this.author = author;
        this.action = action;
        this.actionDescription = author +" "+ action.getDescription() +" "+ details +" em "+ timestamp;
    }

    //**********************************************************
    // business
    //**********************************************************

    public static void create(StaffUser author, String modelId, String collection, String details) {
        StaffHistory sah = new StaffHistory(author, ActionType.CREATE, modelId, collection, details);
        sah.create();
    }

    public static void update(StaffUser author, String modelId, String collection, String details, List<String> changes) {
        StaffHistory sah = new StaffHistory(author, ActionType.UPDATE, modelId, collection, details, changes);
        sah.create();
    }

    public static void delete(StaffUser author, String modelId, String collection, String details) {
        StaffHistory sah = new StaffHistory(author, ActionType.DELETE, modelId, collection, details);
        sah.create();
    }

    public static void activate(StaffUser author, String modelId, String collection, String details) {
        StaffHistory sah = new StaffHistory(author, ActionType.ACTIVATE, modelId, collection, details);
        sah.create();
    }

    public static void deactivate(StaffUser author, String modelId, String collection, String details) {
        StaffHistory sah = new StaffHistory(author, ActionType.DEACTIVATE, modelId, collection, details);
        sah.create();
    }

    public static void changePassword(StaffUser author, String modelId, String collection, String details) {
        StaffHistory sah = new StaffHistory(author, ActionType.PASSWORD_CHANGE, modelId, collection, details);
        sah.create();
    }

    public static void ban(StaffUser author, String modelId, String collection, String details) {
        StaffHistory sah = new StaffHistory(author, ActionType.BAN, modelId, collection, details);
        sah.create();
    }

    public static void archive(StaffUser author, String modelId, String collection, String details) {
        StaffHistory sah = new StaffHistory(author, ActionType.ARCHIVE, modelId, collection, details);
        sah.create();
    }

    //**********************************************************
    // dao
    //**********************************************************

    private void create() {
        SimpleDAOFactory.getInstance().getStaffHistoryDAO().create(this);
    }

    public static StaffHistory load(String id) {
        return SimpleDAOFactory.getInstance().getStaffHistoryDAO().load(id);
    }

    public static StaffHistory loadByAuthor(String author) {
        return SimpleDAOFactory.getInstance().getStaffHistoryDAO().loadByAuthor(author);
    }

    //**********************************************************
    // json
    //**********************************************************

    @Override
    public JsonNode toJson(String context) {
        // TODO: JSON
        return null;
    }

    public static StaffHistory fromJson(JsonNode json) {
        // TODO: JSON
        return null;
    }
}
