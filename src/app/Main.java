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
        System.out.println();

        System.out.println("##### TEST 5: Seller insert");
        // sellerDao.insert(new Seller(null, "Lucas Quadrado", "lucasquadrado@gmail.com", LocalDate.parse("21/02/2000", Seller.fmtBirthDaye), 1210.0, new Department(4, "Books")));
        System.out.println();

        System.out.println("##### TEST 6: Seller Update");
        // Seller seller = sellerDao.findById(7);
        // seller.setName("João Pedro");
        // seller.setEmail("jpsoares@gmail.com");
        // seller.setBirthDate(LocalDate.parse("02/02/2002", Seller.fmtBirthDaye));
        // sellerDao.update(seller);
    }
}
