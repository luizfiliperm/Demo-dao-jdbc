package app;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Main {
    public static void main(String[] args) throws Exception {
        SellerDao sellerDao = DaoFactory.createSellerDao();
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("##### TEST 1: seller findById #####");
        System.out.println(sellerDao.findById(7));
        System.out.println();

        System.out.println("##### TEST 2: department findById #####");
        System.out.println(departmentDao.findById(2));
        System.out.println();

        System.out.println("##### TEST 3: seller findByDepartment");
        List<Seller> sellers = sellerDao.findByDepartment(new Department(2, "Electronics"));
        sellers.forEach(System.out::println);
        System.out.println();

        System.out.println("##### TEST 4: seller findAll");
        List<Seller> allSellers = sellerDao.findall();
        allSellers.forEach(System.out::println);
    }
}
