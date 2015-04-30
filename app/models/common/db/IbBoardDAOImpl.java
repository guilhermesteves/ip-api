package models.common.db;

import models.IbBoard;
import models.IbThread;
import models.IbThreadList;
import models.common.db.generic.SimpleDAOImpl;
import org.jongo.MongoCollection;

import java.util.List;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 */
public class IbBoardDAOImpl extends SimpleDAOImpl<IbBoard> implements IbBoardDAO {

    @Override
    public IbBoard loadBySlug(String slug) {
        MongoCollection collection = getCollection(IbBoard.class);
        return collection.findOne("{ slug : # }", slug).as(IbBoard.class);
    }

    @Override
    public IbBoard loadByLongSlug(String longSlug) {
        MongoCollection collection = getCollection(IbBoard.class);
        return collection.findOne("{ longSlug : # }", longSlug).as(IbBoard.class);
    }

    @Override
    public IbThreadList loadCatalog() {
        // TODO: CATALOG DAO
        return null;
    }
}
