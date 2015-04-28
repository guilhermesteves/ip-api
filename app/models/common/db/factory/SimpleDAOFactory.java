package models.common.db.factory;

import models.common.db.*;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 */
public abstract class SimpleDAOFactory {

    //**********************************************************
    // properties
    //**********************************************************

    private static final SimpleDAOFactory factory = new SimpleDAOFactoryImpl();

    //**********************************************************
    // instance
    //**********************************************************

    public static SimpleDAOFactory getInstance() {
        return factory;
    }

    //**********************************************************
    // interfaces
    //**********************************************************

    public abstract AdminDAO getAdminDAO();
    public abstract ModDAO getModDAO();
    public abstract UserDAO getUserDAO();

    public abstract IbBoardDAO getBoardDAO();
    public abstract IbThreadDAO getThreadDAO();
    public abstract IbPostDAO getPostDAO();

    public abstract StaffHistoryDAO getStaffHistoryDAO();
    public abstract SettingDAO getSettingDAO();
}
