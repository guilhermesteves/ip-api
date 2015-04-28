package models.common.db.generic;

import models.common.BaseModel;
import org.jongo.MongoCollection;

import java.util.List;

/**
 * May the build success be with you.
 * With great problems, comes great help from @guilhermesteves
 *
 * Interface which defines all basic methods for
 * models in MongoDB
 */
public interface SimpleDAO<M extends BaseModel> {

    void save(M model);

    void create(M model);

    M load(String id);
    M load(String id, Class<M> _class);

    M loadByQuery(String query);

    Iterable<M> find(Integer offset, Integer limit);
    Iterable<M> find(String query, Integer offset, Integer limit);

    List<M> listAll(Integer offset, Integer limit);

    List<M> listByQuery(String query);

    void update(M model);
    void update(String id, M model);

    void delete(String id);
    void delete(String id, Class<M> _class);

    void activate(String id);
    void deactivate(String id);

    Long count(Class<M> _class);

    MongoCollection getCollection(Class _class);
    MongoCollection getCollection(String col);
}
