package app;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;

public class Main {
    public static void main(String[] args) throws Exception {
        SellerDao sellerDao = DaoFactory.createSellerDao();
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("##### TEST 1: seller findById #####");
        System.out.println(sellerDao.findById(7));
        System.out.println();

        System.out.println("##### TEST 2: department findById #####");
        System.out.println(departmentDao.findById(2));
    }
}
