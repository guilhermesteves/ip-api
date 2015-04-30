package models.common.db.factory;

import models.common.db.IbPostDAO;
import models.common.db.IbPostDAOImpl;
import models.common.db.IbThreadDAO;
import models.common.db.IbThreadDAOImpl;

/**
 * May the build success be with you.
 * With great problems, comes great help from @guilhermesteves
 */
public class MultiTenancyDAOFactoryImpl extends MultiTenancyDAOFactory {


    public IbThreadDAO getThreadDAO() {
        return new IbThreadDAOImpl();
    }
    public IbPostDAO getPostDAO() {
        return new IbPostDAOImpl();
    }
}
