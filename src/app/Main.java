package app;

import model.dao.DaoFactory;
import model.dao.SellerDao;

public class Main {
    public static void main(String[] args) throws Exception {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findById ===");
        System.out.println(sellerDao.findById(7));
    }
}
