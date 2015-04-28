package models.users;

import models.common.BaseModel;
import models.common.constants.Constants;
import org.joda.time.DateTime;
import org.joda.time.Days;
import play.data.format.Formats;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;
import play.data.validation.Constraints;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Basic User to be used in the system access types models
 * (i.e. Admin, Mod, User)
 */
public abstract class BaseUser implements BaseModel {

    //**********************************************************
    // properties
    //**********************************************************

    @Id
    @ObjectId
    private String id;

    @Formats.DateTime(pattern = Constants.DATE_PATTERN)
    public DateTime dateCreation;
    
    public Boolean active = true;

    //**********************************************************
    // getters and setters
    //**********************************************************

    @Override
    public String getId() {
        return id;
    }

    public BaseUser setId(String id) {
        this.id = id;
        return this;
    }

    public DateTime getDateCreation() {
        return dateCreation;
    }

    public BaseUser setDateCreation(DateTime dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public BaseUser setActive(Boolean active) {
        this.active = active;
        return this;
    }

    //**********************************************************

    public Integer getLoyalty() {
        return Days.daysBetween(getDateCreation().withTimeAtStartOfDay(),
                new DateTime().withTimeAtStartOfDay()).getDays();
    }

    //**********************************************************
    // business
    //**********************************************************

    public abstract void activate();

    public abstract void deactivate();
}
