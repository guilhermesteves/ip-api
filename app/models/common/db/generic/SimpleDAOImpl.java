package models.common.db.generic;

import models.common.BaseModel;
import org.apache.commons.collections.IteratorUtils;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import java.util.List;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Implementation of the SimpleDAO Interface
 */
public abstract class SimpleDAOImpl<M extends BaseModel> extends DefaultDAOImpl<M> implements SimpleDAO<M> {

    //**********************************************************
    // constructor
    //**********************************************************

    public SimpleDAOImpl() {
        super();
    }

    //**********************************************************
    // CRUD methods
    //**********************************************************

    public void create(M model) {
        getCollection(model.getClass()).insert(model);
    }

    public M load(String id) {
        return (M) load(new ObjectId(id), getModelClass());
    }

    public M load(String id, Class<M> _class) {
        return load(new ObjectId(id), _class);
    }

    public M load(ObjectId id, Class<M> _class) {
        return (M) getCollection(_class).findOne(id).as(_class);
    }

    public void update(M model) {
        update(model.getId(), model);
    }

    public void update(String id, M model) {
        update(new ObjectId(id), model);
    }

    public void update(ObjectId id, M model) {
        getCollection(model.getClass()).update("{ _id : # }", id).with(model);
    }

    public void delete(String id) {
        delete(new ObjectId(id), getModelClass());
    }

    public void delete(String id, Class<M> _class) {
        delete(new ObjectId(id), _class);
    }

    public void delete(ObjectId id, Class<M> _class) {
        MongoCollection collection = getCollection(_class);
        collection.remove(id);
    }

    public List<M> listAll() {
        return listAll(0, 50, getModelClass());
    }

    public List<M> listAll(Class<M> _class) {
        return listAll(0, 50, _class);
    }

    public List<M> listAll(Integer offset, Integer limit) {
        return listAll(offset, limit, getModelClass());
    }

    public List<M> listAll(Integer offset, Integer limit, Class<M> _class) {
        Iterable<M> iterable = getCollection(_class).find().skip(offset).limit(limit).as(_class);

        if(iterable != null)
            return IteratorUtils.toList(iterable.iterator());

        return null;
    }

    //**********************************************************
    // State methods
    //**********************************************************

    public void activate(String id) {
        getCollection(getModelClass()).update("{ _id: # }", new ObjectId(id)).with("{ active : true }");
    }

    public void deactivate(String id) {
        getCollection(getModelClass()).update("{ _id: # }", new ObjectId(id)).with("{ active : false }");
    }

    //**********************************************************
    // Field Load methods
    //**********************************************************

    public M loadByEmail(String email) {
        MongoCollection collection = getCollection(getModelClass());
        return (M) collection.findOne("{email : #}", email).as(getModelClass());
    }

    public M loadByName(String name) {
        MongoCollection collection = getCollection(getModelClass());
        return (M) collection.findOne("{name : #}", name).as(getModelClass());
    }
}
