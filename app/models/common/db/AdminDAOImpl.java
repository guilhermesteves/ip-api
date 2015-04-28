package models.common.db;

import models.common.db.generic.SimpleDAOImpl;
import models.users.Admin;
import org.jongo.MongoCollection;

/**
 * May the build success be with you.
 * With great problems, comes great help from @guilhermesteves
 */
public class AdminDAOImpl extends SimpleDAOImpl<Admin> implements AdminDAO {

    @Override
    public Admin loadByEmail(String email) {
        MongoCollection collection = getCollection(Admin.class);
        return collection.findOne("{email : #}", email).as(Admin.class);
    }

    @Override
    public Admin loadByName(String name) {
        MongoCollection collection = getCollection(Admin.class);
        return collection.findOne("{name : #}", name).as(Admin.class);
    }
}
