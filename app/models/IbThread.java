package models;

import models.common.BaseModel;
import org.joda.time.DateTime;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

/**
 * Created with IntelliJ IDEA.
 * User: Guilherme Esteves (@guilhermesteves)
 * Date: 3/14/15
 * Time: 12:13 PM
 * May the build success be with you
 */
public class IbThread implements BaseModel {

    //**********************************************************
    // properties
    //**********************************************************

    @Id
    @ObjectId
    private String id;

    private DateTime lastBump;

    private Integer maxPosts;

    private Boolean sticky = false;

    private Boolean autosage = false;

    //**********************************************************
    // getters and setters
    //**********************************************************

    public String getId() {
        return id;
    }

    public IbThread setId(String id) {
        this.id = id;
        return this;
    }
}
