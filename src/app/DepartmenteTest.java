package app;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmenteTest {
    public static void main(String[] args) {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        
        System.out.println("##### TEST 1 department findById");
        System.out.println(departmentDao.findById(3));
        System.out.println();

        System.out.println("##### TEST 2 department insert");
        // departmentDao.insert(new Department(null, "Games"));
        System.out.println();

        System.out.println("##### TEST 3 department update #####");
        Department dep = departmentDao.findById(4);
        dep.setName("Music");
        departmentDao.update(dep);
        System.out.println("Updated Department: " + departmentDao.findById(4));
        System.out.println();
        
    }
}
