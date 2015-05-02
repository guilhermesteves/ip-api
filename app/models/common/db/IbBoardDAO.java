package models.common.db;

import models.IbBoard;
import models.IbThreadList;
import models.common.db.generic.SimpleDAO;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 */
public interface IbBoardDAO extends SimpleDAO<IbBoard> {

    IbBoard loadBySlug(String slug);

    IbBoard loadByLongSlug(String longSlug);

    IbThreadList loadCatalog(String id);
}
