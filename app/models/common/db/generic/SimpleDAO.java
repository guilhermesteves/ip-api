package models.common.db.generic;

import models.common.BaseModel;
import org.jongo.MongoCollection;

import java.util.List;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Interface which defines all basic methods for
 * models in MongoDB
 */
public interface SimpleDAO<M extends BaseModel> extends DefaultDAO<M> {

    // CRUD

    void create(M model);

    M load(String id);
    M load(String id, Class<M> _class);

    void update(M model);
    void update(String id, M model);

    void delete(String id);
    void delete(String id, Class<M> _class);

    List<M> listAll();
    List<M> listAll(Class<M> _class);
    List<M> listAll(Integer offset, Integer limit);
    List<M> listAll(Integer offset, Integer limit, Class<M> _class);

    // State

    void activate(String id);
    void deactivate(String id);

    // Fields

    M loadByEmail(String email);

    M loadByName(String name);
}
