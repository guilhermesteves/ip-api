package models;

import models.common.BaseModel;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 */
public class IbPost implements BaseModel {

    //**********************************************************
    // properties
    //**********************************************************

    @Id
    @ObjectId
    private String id;

    //**********************************************************
    // getters and setters
    //**********************************************************

    public String getId() {
        return id;
    }

    public IbPost setId(String id) {
        this.id = id;
        return this;
    }
}
