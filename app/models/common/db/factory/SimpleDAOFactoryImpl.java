package models.common.db.factory;

import models.common.db.*;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Implement here all DAO methods to it's
 * corresponding DAOImpl
 */
public class SimpleDAOFactoryImpl extends SimpleDAOFactory {

    public AdminDAO getAdminDAO() {
        return new AdminDAOImpl();
    }
    public ModDAO getModDAO() {
        return new ModDAOImpl();
    }
    public UserDAO getUserDAO() {
        return new UserDAOImpl();
    }

    public IbBoardSectionDAO getIbBoardSectionDAO() {
        return new IbBoardSectionDAOImpl();
    }
    public IbBoardDAO getIbBoardDAO() {
        return new IbBoardDAOImpl();
    }

    public StaffHistoryDAO getStaffHistoryDAO() {
        return new StaffHistoryDAOImpl();
    }

    public SettingDAO getSettingDAO() {
        return new SettingDAOImpl();
    }
}
