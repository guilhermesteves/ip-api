package models.common.db;

import models.common.db.generic.SimpleDAO;
import models.users.Mod;

/**
 * May the build success be with you.
 * With great problems, comes great help from @guilhermesteves
 */
public interface ModDAO extends SimpleDAO<Mod> {

    Mod loadByEmail(String email);
}
