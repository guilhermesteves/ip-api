package models.common.db;

import models.common.db.SettingDAO;
import models.common.db.generic.SimpleDAOImpl;
import models.system.Setting;
import org.jongo.MongoCollection;

/**
 * May the build success be with you.
 * With great problems, comes great help from @guilhermesteves
 */
public class SettingDAOImpl extends SimpleDAOImpl<Setting> implements SettingDAO {

    @Override
    public Setting loadByKey(String key) {
        MongoCollection collection = getCollection(Setting.class);
        return collection.findOne("{key : #}", key).as(Setting.class);
    }
}
