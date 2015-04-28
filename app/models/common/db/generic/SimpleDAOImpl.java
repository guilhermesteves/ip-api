package models.common.db.generic;

import models.common.BaseModel;
import org.apache.commons.collections.IteratorUtils;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Implementation of the SimpleDAO Interface
 */
public abstract class SimpleDAOImpl<M extends BaseModel> extends DefaultDAO implements SimpleDAO<M> {

    // I have to say that I'm really proud of this solution
    public SimpleDAOImpl() {
        modelClass = (Class<M>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void save(M model) {
        getCollection(model.getClass()).save(model);
    }

    public void create(M model) {
        getCollection(model.getClass()).insert(model);
    }

    public M load(String id) {
        return load(new ObjectId(id), getModelClass());
    }

    public M load(String id, Class<M> _class) {
        return load(new ObjectId(id), _class);
    }

    public M load(ObjectId id, Class<M> _class) {
        MongoCollection collection = getCollection(_class);
        return collection.findOne(id).as(_class);
    }

    public M loadByQuery(String query) {
        MongoCollection collection = getCollection(getModelClass());
        return (M) collection.findOne(query).as(getModelClass());
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

    public Iterable<M> find(Integer offset, Integer limit) {
        MongoCollection mongoCollection = getCollection(getModelClass());

        return mongoCollection.find().skip(offset).limit(limit).as(getModelClass());
    }

    public Iterable<M> find(String query, Integer offset, Integer limit) {
        MongoCollection mongoCollection = getCollection(getModelClass());

        return mongoCollection.find(query).skip(offset).limit(limit).as(getModelClass());
    }

    public List<M> listAll(Integer offset, Integer limit) {
        Iterable<M> iterable = getCollection(getModelClass()).find().as(getModelClass());

        if(iterable != null)
            return IteratorUtils.toList(iterable.iterator());

        return null;
    }

    public List<M> listAll(Class<M> _class) {
        Iterable<M> iterable = getCollection(_class).find().as(_class);

        if(iterable != null)
            return IteratorUtils.toList(iterable.iterator());

        return null;
    }

    public List<M> listByQuery(String query) {
        MongoCollection collection = getCollection(getModelClass());
        return (List<M>) collection.find(query).as(getModelClass());
    }

    public Long count(Class<M> _class) {
        return getCollection(_class).count();
    }


    public void activate(String id) {
        getCollection(getModelClass()).update("{ _id: # }", new ObjectId(id)).with("{ active : true }");
    }

    public void deactivate(String id) {
        getCollection(getModelClass()).update("{ _id: # }", new ObjectId(id)).with("{ active : false }");
    }

}
