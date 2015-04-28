package models.common.db;

import models.common.db.ModDAO;
import models.common.db.generic.SimpleDAOImpl;
import models.users.Mod;
import org.jongo.MongoCollection;

/**
 * May the build success be with you.
 * With great problems, comes great help from @guilhermesteves
 */
public class ModDAOImpl extends SimpleDAOImpl<Mod> implements ModDAO {

    @Override
    public Mod loadByEmail(String email) {
        MongoCollection collection = getCollection(Mod.class);
        return collection.findOne("{email : #}", email).as(Mod.class);
    }
}
