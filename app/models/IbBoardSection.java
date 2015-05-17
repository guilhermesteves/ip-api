package models;

import com.fasterxml.jackson.databind.JsonNode;
import models.common.BaseModel;
import models.common.constants.Constants;
import models.common.constants.FIELDS;
import models.common.db.factory.SimpleDAOFactory;
import models.common.json.JsonHelper;
import models.common.json.JsonSerializable;
import models.history.Traceable;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * May the build success be with you.
 * With great problems, comes great help from @guilhermesteves
 */
public class IbBoardSection implements BaseModel, JsonSerializable, Traceable<IbBoardSection> {

    //**********************************************************
    // properties
    //**********************************************************

    @Id
    @ObjectId
    private String id;

    private String name;

    private Integer order;

    //**********************************************************
    // getters and setters
    //**********************************************************


    @Override
    public String getId() {
        return id;
    }

    public IbBoardSection setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public IbBoardSection setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getOrder() {
        return order;
    }

    public IbBoardSection setOrder(Integer order) {
        this.order = order;
        return this;
    }

    //**********************************************************
    // validation
    //**********************************************************

    private void ensureOrderExists() {
        if (getOrder() == null) {
            Integer lastOrder = IbBoardSection.loadLastOrder().getOrder();
            setOrder(lastOrder != null ? lastOrder+1 : 0);
        }
    }

    //**********************************************************
    // business
    //**********************************************************

    //**********************************************************
    // dao
    //**********************************************************

    public void create() {
        ensureOrderExists();
        SimpleDAOFactory.getInstance().getIbBoardSectionDAO().create(this);
    }

    public static IbBoardSection load(String id) {
        return SimpleDAOFactory.getInstance().getIbBoardSectionDAO().load(id);
    }

    public void update(IbBoardSection ibBoardSection) {

        if (!this.getName().equals(ibBoardSection.getName()))
            this.setName(ibBoardSection.getName());

        if (!this.getOrder().equals(ibBoardSection.getOrder()))
            this.setOrder(ibBoardSection.getOrder());

        ensureOrderExists();

        SimpleDAOFactory.getInstance().getIbBoardSectionDAO().update(this);
    }

    public static void delete(String id) {
        SimpleDAOFactory.getInstance().getIbBoardSectionDAO().delete(id);
    }

    @Override
    public String getDetails() {
        return "Board. Nome: "+ getName() +" | Ordem: "+ getOrder();
    }

    @Override
    public List<String> getChanges(IbBoardSection ibBoardSection) {

        List<String> changes = new ArrayList<>();

        if (!this.getName().equals(ibBoardSection.getName()))
            changes.add(Constants.getFieldChange("Nome", this.getName(), ibBoardSection.getName()));

        if (!this.getOrder().equals(ibBoardSection.getOrder()))
            changes.add(Constants.getFieldChange("Ordem", this.getOrder(), ibBoardSection.getOrder()));

        return changes;
    }

    //**********************************************************

    public static IbBoardSection loadLastOrder() {
        return SimpleDAOFactory.getInstance().getIbBoardSectionDAO().loadLastOrder();
    }

    //**********************************************************
    // json
    //**********************************************************

    @Override
    public JsonNode toJson(String context) {

        JsonHelper j = new JsonHelper();

        j.addString(FIELDS.BOARD_SECTION.ID, getId());

        j.addString(FIELDS.BOARD_SECTION.NAME, getName())
                .addInteger(FIELDS.BOARD_SECTION.ORDER, getOrder());

        return j.getJSON();
    }

    public static IbBoardSection fromJson(JsonNode json) {

        JsonHelper j = new JsonHelper(json);
        IbBoardSection ibBoardSection = new IbBoardSection();

        ibBoardSection.setId(j.getString(FIELDS.BOARD_SECTION.ID));

        ibBoardSection.setName(j.getString(FIELDS.BOARD_SECTION.NAME))
                .setOrder(j.getInteger(FIELDS.BOARD_SECTION.ORDER));

        return ibBoardSection;
    }
}
