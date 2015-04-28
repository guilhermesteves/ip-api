package models.common.db.generic;

import models.common.BaseModel;
import org.apache.commons.collections.IteratorUtils;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.List;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 */
public class MultiTenancyDAOImpl {

    public static <M> MongoCollection getCollection(Class<M> _class) {
        return PlayJongo.getCollection(_class.getSimpleName().toLowerCase());
    }

    public static <M> M load(ObjectId id, Class<M> _class, ObjectId tenantId) {
        return getCollection(_class).findOne("{_id : #, tenantId: #}", id, tenantId).as(_class);
    }

    public static <M> M load(String id, Class<M> _class, String tenantId){
        return load(new ObjectId(id), _class, new ObjectId(tenantId));
    }

    public static <M> void create(M model) {
        getCollection(model.getClass()).save(model);
    }

    public static <M> void save(M model){
        getCollection(model.getClass()).save(model);
    }

    public static <M extends BaseModel> void update( M model) {
        update(model.getId(), model);
    }

    public static <M> void update(String id, M model) {
        update(new ObjectId(id), model);
    }

    public static <M> void update(ObjectId id, M model){
        getCollection(model.getClass()).update("{ _id : # }", id).with(model);
    }

    public static <M> void delete(String id, String tenantId, Class<M> _class){
        delete(new ObjectId(id), new ObjectId(tenantId), _class);
    }

    public static <M> void delete(ObjectId id, ObjectId tenantId, Class<M> _class){
        getCollection(_class).remove("{_id : #, accountId : #}",id, tenantId);
    }

    public static <M> List<M> list(Class<M> _class, ObjectId tenantId){
        return list(_class, tenantId);
    }

    public static <M> List<M> list(Class<M> _class, String tenantId){
        Iterable<M> iterable = getCollection(_class).find("{accountId: #}", new ObjectId(tenantId)).as(_class);

        if(iterable != null)
            return IteratorUtils.toList(iterable.iterator());

        return null;
    }
}
