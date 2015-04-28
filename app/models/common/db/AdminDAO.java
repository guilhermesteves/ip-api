package models.common.db;

import models.common.db.generic.SimpleDAO;
import models.users.Admin;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 */
public interface AdminDAO extends SimpleDAO<Admin> {

    Admin loadByEmail(String email);

    Admin loadByName(String name);
}
