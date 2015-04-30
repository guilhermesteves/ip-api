package models.common.db.generic;

import models.common.BaseModel;
import org.apache.commons.collections.IteratorUtils;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 */
public abstract class MultiTenancyDAOImpl<M extends BaseModel> extends DefaultDAOImpl<M> implements SimpleDAO<M> {

    public void create(M model) {
        getCollection(model.getClass()).insert(model);
    }

    public M load(ObjectId id) {
        return (M) getCollection(getModelClass()).findOne("{_id : #}", id).as(getModelClass());
    }

    public M load(String id, String tenantId) {
        return load(new ObjectId(id), new ObjectId(tenantId), getModelClass());
    }

    public M load(String id, String tenantId, Class<M> _class) {
        return load(new ObjectId(id), new ObjectId(tenantId), _class);
    }

    public M load(ObjectId id, ObjectId tenantId, Class<M> _class) {
        return getCollection(_class).findOne("{_id : #, tenantId: #}", id, tenantId).as(_class);
    }

    public void update( M model) {
        update(model.getId(), model);
    }

    public void update(String id, M model) {
        update(new ObjectId(id), model);
    }

    public void update(ObjectId id, M model) {
        getCollection(model.getClass()).update("{ _id : # }", id).with(model);
    }

    public void delete(String id, String tenantId) {
        delete(new ObjectId(id), new ObjectId(tenantId), getModelClass());
    }

    public void delete(String id, String tenantId, Class<M> _class) {
        delete(new ObjectId(id), new ObjectId(tenantId), _class);
    }

    public void delete(ObjectId id, ObjectId tenantId, Class<M> _class) {
        getCollection(_class).remove("{_id : #, accountId : #}", id, tenantId);
    }

    public List<M> listAll(String tenantId) {
        return listAll(new ObjectId(tenantId), 0, 50, getModelClass());
    }

    public List<M> listAll(String tenantId, Class<M> _class) {
        return listAll(new ObjectId(tenantId), 0, 50, _class);
    }

    public List<M> listAll(String tenantId, Integer offset, Integer limit) {
        return listAll(new ObjectId(tenantId), offset, limit, getModelClass());
    }

    public List<M> listAll(String tenantId, Integer offset, Integer limit, Class<M> _class) {
        return listAll(new ObjectId(tenantId), offset, limit, _class);
    }

    public List<M> listAll(ObjectId tenantId, Integer offset, Integer limit, Class<M> _class) {
        Iterable<M> iterable = getCollection(_class).find("{accountId: #}", tenantId).skip(offset).limit(limit).as(_class);

        if(iterable != null)
            return IteratorUtils.toList(iterable.iterator());

        return null;
    }

    //**********************************************************
    // Field Load methods
    //**********************************************************
}
