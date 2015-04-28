package models.common.db;

import models.common.db.generic.SimpleDAO;
import models.system.Setting;

/**
 * May the build success be with you.
 * With great problems, comes great help from @guilhermesteves
 */
public interface SettingDAO extends SimpleDAO<Setting> {

    Setting loadByKey(String key);
}
