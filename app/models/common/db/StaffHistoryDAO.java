package models.common.db;

import models.common.db.generic.SimpleDAO;
import models.history.StaffHistory;

/**
 * May the build success be with you.
 * With great problems, comes great help from @guilhermesteves
 */
public interface StaffHistoryDAO extends SimpleDAO<StaffHistory> {

    StaffHistory loadByAuthor(String author);
}
