package models.common.db;

import models.common.db.generic.SimpleDAO;
import models.users.User;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 */
public interface UserDAO extends SimpleDAO<User> {

    User loadByVisualId(String visualId);

    User loadByIdentifier(String identifier);
}
