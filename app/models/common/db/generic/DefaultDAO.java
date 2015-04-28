package models.common.db.generic;

import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;

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
public abstract class DefaultDAO {

    protected Class modelClass;

    protected Class getModelClass() {
        return modelClass;
    }

    //**********************************************************
    // helpers
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
}
