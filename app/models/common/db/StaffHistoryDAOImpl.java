package models.common.db;

import models.common.db.generic.SimpleDAOImpl;
import models.history.StaffHistory;
import org.jongo.MongoCollection;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 */
public class StaffHistoryDAOImpl extends SimpleDAOImpl<StaffHistory> implements StaffHistoryDAO {

    @Override
    public StaffHistory loadByAuthor(String author) {
        MongoCollection collection = getCollection(StaffHistory.class);
        return collection.findOne("{author : #}", author).as(StaffHistory.class);
    }
}
