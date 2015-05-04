package models;

import com.fasterxml.jackson.databind.JsonNode;
import models.common.BaseModel;
import models.common.DisplayableException;
import models.common.db.factory.SimpleDAOFactory;
import models.common.json.JsonHelper;
import models.common.json.JsonSerializable;
import models.common.constants.Constants;
import models.common.constants.FIELDS;
import models.common.constants.MESSAGES;
import models.history.Traceable;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;
import play.data.validation.Constraints;

import java.util.ArrayList;
import java.util.List;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * All methods belonging the Boards access and business is here
 */
public class IbBoard implements BaseModel, JsonSerializable, Traceable<IbBoard> {

    //**********************************************************
    // properties
    //**********************************************************

    @Id
    @ObjectId
    private String id;

    private Boolean active;
    private String name;

    @Constraints.Required(message="\"Slug\" é obrigatório.")
    @Constraints.MinLength(value=1,message="\"Slug\" deve possuir no mínimo 1 caracteres.")
    @Constraints.MaxLength(value=3,message="\"Slug\" deve possuir no máximo 15 caracteres.")
    private String slug;
    private String longSlug;

    private Integer maxThreads = 10;
    private Boolean hasCatalog = true;

    private IbThreadList threads;

    //**********************************************************
    // getters and setters
    //**********************************************************

    public String getId() {
        return id;
    }

    public IbBoard setId(String id) {
        this.id = id;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public IbBoard setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public String getName() {
        return name;
    }

    public IbBoard setName(String name) {
        this.name = name;
        return this;
    }

    public String getSlug() {
        return slug;
    }

    public IbBoard setSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public String getLongSlug() {
        return longSlug;
    }

    public IbBoard setLongSlug(String longSlug) {
        this.longSlug = longSlug;
        return this;
    }

    public Integer getMaxThreads() {
        return maxThreads;
    }

    public IbBoard setMaxThreads(Integer maxThreads) {
        this.maxThreads = maxThreads;
        return this;
    }

    public Boolean getHasCatalog() {
        return hasCatalog;
    }

    public IbBoard setHasCatalog(Boolean hasCatalog) {
        this.hasCatalog = hasCatalog;
        return this;
    }

    //**********************************************************
    // validation
    //**********************************************************

    public void checkSlug() {
        IbBoard ibBoard = loadBySlug(getSlug());
        if(ibBoard != null && (getId() == null || !getId().equals(ibBoard.getId())))
            throw new DisplayableException(MESSAGES.BOARD.SLUG_ALREADY_EXISTS);
    }

    //**********************************************************
    // business
    //**********************************************************

    //**********************************************************
    // dao
    //**********************************************************

    public void create() {
        checkSlug();
        SimpleDAOFactory.getInstance().getBoardDAO().create(this);
    }

    public static IbBoard load(String id) {
        return SimpleDAOFactory.getInstance().getBoardDAO().load(id);
    }

    public void update(IbBoard ibBoard) {

        if (this.getActive() != ibBoard.getActive())
            this.setActive(ibBoard.getActive());

        if (!this.getName().equals(ibBoard.getName()))
            this.setName(ibBoard.getName());

        if (!this.getSlug().equals(ibBoard.getSlug()))
            this.setSlug(ibBoard.getSlug());

        if (!this.getLongSlug().equals(ibBoard.getLongSlug()))
            this.setLongSlug(ibBoard.getLongSlug());

        if (!this.getMaxThreads().equals(ibBoard.getMaxThreads()))
            this.setMaxThreads(ibBoard.getMaxThreads());

        if (!this.getHasCatalog().equals(ibBoard.getHasCatalog()))
            this.setHasCatalog(ibBoard.getHasCatalog());

        checkSlug();

        SimpleDAOFactory.getInstance().getBoardDAO().update(this);
    }

    public static void delete(String id) {
        SimpleDAOFactory.getInstance().getBoardDAO().delete(id);
    }

    //**********************************************************

    public static IbBoard loadBySlug(String slug) {
        return SimpleDAOFactory.getInstance().getBoardDAO().loadBySlug(slug);
    }

    public static IbBoard loadByLongSlug(String longSlug) {
        return SimpleDAOFactory.getInstance().getBoardDAO().loadByLongSlug(longSlug);
    }

    public static IbThreadList loadCatalogById(String id) {
        IbBoard ibBoard = IbBoard.load(id);

        if (ibBoard != null && ibBoard.getHasCatalog())
            return SimpleDAOFactory.getInstance().getBoardDAO().loadCatalog(ibBoard.getId());
        else
            return null;
    }

    public static IbThreadList loadCatalogBySlug(String slug) {
        IbBoard ibBoard = IbBoard.loadBySlug(slug);

        if (ibBoard != null && ibBoard.getHasCatalog())
            return SimpleDAOFactory.getInstance().getBoardDAO().loadCatalog(ibBoard.getId());
        else
            return null;
    }

    //**********************************************************

    public void activate() {
        SimpleDAOFactory.getInstance().getBoardDAO().activate(getId());
    }

    public void deactivate() {
        SimpleDAOFactory.getInstance().getBoardDAO().deactivate(getId());
    }

    //**********************************************************
    // changes
    //**********************************************************

    @Override
    public String getDetails() {
        return "Admin. Nome: "+ getName() +" | Slug: "+ getSlug();
    }

    @Override
    public List<String> getChanges(IbBoard ibBoard) {

        List<String> changes = new ArrayList<>();

        if (this.getActive() != ibBoard.getActive())
            changes.add(Constants.getFieldChange("Ativo", this.getActive(), ibBoard.getActive()));

        if (!this.getName().equals(ibBoard.getName()))
            changes.add(Constants.getFieldChange("Nome", this.getName(), ibBoard.getName()));

        if (!this.getSlug().equals(ibBoard.getSlug()))
            changes.add(Constants.getFieldChange("Slug", this.getSlug(), ibBoard.getSlug()));

        if (!this.getLongSlug().equals(ibBoard.getLongSlug()))
            changes.add(Constants.getFieldChange("Slug longa", this.getLongSlug(), ibBoard.getLongSlug()));

        if (!this.getMaxThreads().equals(ibBoard.getMaxThreads()))
            changes.add(Constants.getFieldChange("Max. Threads:", this.getMaxThreads(), ibBoard.getMaxThreads()));

        if (!this.getHasCatalog().equals(ibBoard.getHasCatalog()))
            changes.add(Constants.getFieldChange("Tem catálogo:", this.getHasCatalog(), ibBoard.getHasCatalog()));

        return changes;
    }

    //**********************************************************
    // json
    //**********************************************************

    @Override
    public JsonNode toJson(String context) {

        JsonHelper j = new JsonHelper();

        j.addString(FIELDS.BOARD.ID, getId())
                .addBoolean(FIELDS.BOARD.ACTIVE, getActive());

        j.addString(FIELDS.BOARD.NAME, getName());

        j.addString(FIELDS.BOARD.SLUG, getSlug())
                .addString(FIELDS.BOARD.LONG_SLUG, getLongSlug());

        j.addInteger(FIELDS.BOARD.MAX_THREADS, getMaxThreads())
                .addBoolean(FIELDS.BOARD.HAS_CATALOG, getHasCatalog());

        return j.getJSON();
    }

    public static IbBoard fromJson(JsonNode json) {

        JsonHelper j = new JsonHelper(json);
        IbBoard ibBoard = new IbBoard();

        ibBoard.setId(j.getString(FIELDS.BOARD.ID))
                .setActive(j.getBoolean(FIELDS.BOARD.ACTIVE));

        ibBoard.setName(j.getString(FIELDS.BOARD.NAME));

        ibBoard.setSlug(j.getString(FIELDS.BOARD.SLUG))
                .setLongSlug(j.getString(FIELDS.BOARD.LONG_SLUG));

        ibBoard.setMaxThreads(j.getInteger(FIELDS.BOARD.MAX_THREADS))
                .setHasCatalog(j.getBoolean(FIELDS.BOARD.HAS_CATALOG));

        return ibBoard;
    }
}
