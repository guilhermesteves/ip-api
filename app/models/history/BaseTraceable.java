package models.history;

import models.common.BaseModel;
import models.common.constants.Constants;
import org.joda.time.DateTime;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;
import play.data.format.Formats;

import java.util.List;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 */
public abstract class BaseTraceable implements BaseModel {

    //**********************************************************
    // properties
    //**********************************************************

    @Id
    @ObjectId
    protected String id;

    protected String modelId;

    protected String collection;

    protected String details;

    protected List<String> changes;

    @Formats.DateTime(pattern = Constants.DATE_PATTERN)
    protected DateTime timestamp;

    //**********************************************************
    // getters and setters
    //**********************************************************

    public String getId() {
        return id;
    }

    public BaseTraceable setId(String id) {
        this.id = id;
        return this;
    }

    public String getModelId() {
        return modelId;
    }

    public BaseTraceable setModelId(String modelId) {
        this.modelId = modelId;
        return this;
    }

    public String getCollection() {
        return collection;
    }

    public BaseTraceable setCollection(String collection) {
        this.collection = collection;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public BaseTraceable setDetails(String details) {
        this.details = details;
        return this;
    }

    public List<String> getChanges() {
        return changes;
    }

    public BaseTraceable setChanges(List<String> changes) {
        this.changes = changes;
        return this;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public BaseTraceable setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    //**********************************************************
    // constructor
    //**********************************************************

    public BaseTraceable(String modelId, String collection, String details) {
        this.details = details;
        this.modelId = modelId;
        this.collection = collection;
        this.timestamp = new DateTime();
    }

    public BaseTraceable(String modelId, String collection, String details, List<String> changes) {
        this.modelId = modelId;
        this.collection = collection;
        this.details = details;
        this.changes = changes;
        this.timestamp = new DateTime();
    }
}
