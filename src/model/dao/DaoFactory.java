package model.dao;

import db.DB;
import model.dao.impl.DepartmentDAOJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC(DB.getConnection());
    }

    public static DepartmentDao createDepartmentDao(){
        return new DepartmentDAOJDBC(DB.getConnection());
    }
}
