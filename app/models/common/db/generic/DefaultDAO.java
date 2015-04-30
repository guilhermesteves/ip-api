package models.common.db.generic;

import org.jongo.MongoCollection;

import java.util.List;

/**
 * May the build success be with you.
 * With great problems, comes great help from @guilhermesteves
 *
 * This is the maximum abstraction of this Database ORM.
 * The objective is to have a default DAO used by different
 * types, for instance:
 *
 * - Simple Models (implementing a DAO from SimpleDAO)
 * - Models with Parents (implementing a DAO from MultiTenancyDAO)
 */
public interface DefaultDAO<M> {

    // Common

    MongoCollection getCollection(Class _class);
    MongoCollection getCollection(String col);

    void save(M model);

    Iterable<M> find(Integer offset, Integer limit);
    Iterable<M> find(String query, Integer offset, Integer limit);

    M loadByQuery(String query);
    M loadByQuery(String query, Class<M> _class);

    List<M> listByQuery(String query);
    List<M> listByQuery(String query, Class<M> _class);
    List<M> listByQuery(String query, Integer offset, Integer limit);
    List<M> listByQuery(String query, Integer offset, Integer limit, Class<M> _class);

    Long count(Class<M> _class);
    Long count(String query, Class<M> _class);
}
