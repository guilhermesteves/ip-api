package models.common.db;

import models.IbBoard;
import models.IbBoardSection;
import models.common.db.generic.SimpleDAO;

/**
 * May the build success be with you.
 * With great problems, comes great help from @guilhermesteves
 */
public interface IbBoardSectionDAO extends SimpleDAO<IbBoardSection> {

    IbBoardSection loadLastOrder();
}
