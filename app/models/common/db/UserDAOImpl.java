package models.common.db;

import models.common.db.UserDAO;
import models.common.db.generic.SimpleDAOImpl;
import models.users.User;
import org.jongo.MongoCollection;

/**
 * May the build success be with you.
 * With great problems, comes great help from @guilhermesteves
 */
public class UserDAOImpl extends SimpleDAOImpl<User> implements UserDAO {

    @Override
    public User loadByVisualId(String visualId) {
        MongoCollection collection = getCollection(User.class);
        return collection.findOne("{visualId : #}", visualId).as(User.class);
    }

    @Override
    public User loadByIdentifier(String identifier) {
        MongoCollection collection = getCollection(User.class);
        return collection.findOne("{identifier : #}", identifier).as(User.class);
    }
}
