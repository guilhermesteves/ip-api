package models.common.db.factory;

import models.common.db.IbPostDAO;
import models.common.db.IbThreadDAO;

/**
 * May the build success be with you.
 * With great problems, comes great help from @guilhermesteves
 */
public abstract class MultiTenancyDAOFactory {

    //**********************************************************
    // properties
    //**********************************************************

    private static final MultiTenancyDAOFactory factory = new MultiTenancyDAOFactoryImpl();

    //**********************************************************
    // instance
    //**********************************************************

    public static MultiTenancyDAOFactory getInstance() {
        return factory;
    }

    //**********************************************************
    // interfaces
    //**********************************************************

    public abstract IbThreadDAO getThreadDAO();
    public abstract IbPostDAO getPostDAO();
}
