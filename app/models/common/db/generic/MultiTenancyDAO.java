package models.common.db.generic;

import models.common.BaseModel;
import org.jongo.MongoCollection;

import java.util.List;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 */
public interface MultiTenancyDAO<M extends BaseModel> extends DefaultDAO<M> {

    // CRUD

    void create(M model);

    M load(String id, String tenantId);
    M load(String id, Class<M> _class, String tenantId);

    void update(M model);
    void update(String id, M model);

    void delete(String id);
    void delete(String id, Class<M> _class);

    List<M> listAll(String tenantId);
    List<M> listAll(String tenantId, Class<M> _class);
    List<M> listAll(String tenantId, Integer offset, Integer limit);
    List<M> listAll(String tenantId, Integer offset, Integer limit, Class<M> _class);

    // State

    void activate(String id);
    void deactivate(String id);

}
