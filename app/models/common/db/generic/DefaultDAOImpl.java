package models.common.db.generic;

import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * This class has the default methods used by all factory
 * implementations in this same package.
 *
 * @see models.common.db.generic.SimpleDAOImpl
 * @see models.common.db.generic.MultiTenancyDAOImpl
 */
public abstract class DefaultDAOImpl<M> {

    //**********************************************************
    // properties
    //**********************************************************

    protected Class modelClass;

    //**********************************************************
    // getters and setters
    //**********************************************************

    protected Class getModelClass() {
        return modelClass;
    }

    //**********************************************************
    // constructor
    //**********************************************************

    public DefaultDAOImpl() {
        modelClass = (Class<M>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    //**********************************************************
    // common methods
    //**********************************************************

    public MongoCollection getCollection(Class _class) {
        return PlayJongo.getCollection(_class.getSimpleName().toLowerCase());
    }

    public MongoCollection getCollection(String col) {
        return PlayJongo.getCollection(col);
    }

    public static MongoCollection getCollectionFrom(Class _class) {
        return PlayJongo.getCollection(_class.getSimpleName().toLowerCase());
    }

    public static MongoCollection getCollectionFrom(String col) {
        return PlayJongo.getCollection(col);
    }

    public void save(M model) {
        getCollection(model.getClass()).save(model);
    }

    public Iterable<M> find(Integer offset, Integer limit) {
        MongoCollection mongoCollection = getCollection(getModelClass());

        return mongoCollection.find().skip(offset).limit(limit).as(getModelClass());
    }

    public Iterable<M> find(String query, Integer offset, Integer limit) {
        MongoCollection mongoCollection = getCollection(getModelClass());

        return mongoCollection.find(query).skip(offset).limit(limit).as(getModelClass());
    }

    public M loadBy(String field, String value) {
        MongoCollection collection = getCollection(getModelClass());
        return (M) collection.findOne("{# : #}", field, value).as(getModelClass());
    }

    public M loadByQuery(String query) {
        return (M) loadByQuery(query, getModelClass());
    }

    public M loadByQuery(String query, Class<M> _class) {
        MongoCollection collection = getCollection(_class);
        return (M) collection.findOne(query).as(_class);
    }

    public List<M> listByQuery(String query) {
        return listByQuery(query, 0, 50, getModelClass());
    }

    public List<M> listByQuery(String query, Class<M> _class) {
       return listByQuery(query, 0, 50, _class);
    }

    public List<M> listByQuery(String query, Integer offset, Integer limit) {
        MongoCollection collection = getCollection(getModelClass());
        return (List<M>) collection.find(query).skip(offset).limit(limit).as(getModelClass());
    }

    public List<M> listByQuery(String query, Integer offset, Integer limit, Class<M> _class) {
        MongoCollection collection = getCollection(getModelClass());
        return (List<M>) collection.find(query).skip(offset).limit(limit).as(_class);
    }

    public Long count(Class<M> _class) {
        return getCollection(_class).count();
    }

    public Long count(String query, Class<M> _class) {
        return getCollection(_class).count(query);
    }
}
