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
    protected String id;

    @Formats.DateTime(pattern = Constants.DATE_PATTERN)
    protected DateTime dateCreation;

    protected Boolean active = true;

    //**********************************************************
    // getters and setters
    //**********************************************************

    @Override
    public String getId() {
        return id;
    }

    public DateTime getDateCreation() {
        return dateCreation;
    }

    public Boolean getActive() {
        return active;
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
