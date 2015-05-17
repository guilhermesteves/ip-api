package models.common.db;

import models.IbBoardSection;
import models.common.db.generic.SimpleDAOImpl;

/**
 * May the build success be with you.
 * With great problems, comes great help from @guilhermesteves
 */
public class IbBoardSectionDAOImpl extends SimpleDAOImpl<IbBoardSection> implements IbBoardSectionDAO {

    @Override
    public IbBoardSection loadLastOrder() {
        return null;//TODO: LOAD NEXT ORDER
    }
}
