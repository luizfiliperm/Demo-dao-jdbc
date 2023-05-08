package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{

    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        // TODO Auto-generated method stub
    }

    @Override
    public void update(Seller obj) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
    }

    @Override
    public Seller findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            st = conn.prepareStatement(
                "SELECT *,department.name as DepName "
                + "FROM seller INNER JOIN department "
                + "ON seller.DepartmentID = department.Id "
                + "Where seller.Id = ?"
            );

            st.setInt(1, id);

            rs = st.executeQuery();
            
            if(rs.next()){

                Department dep = instantiateDepartment(rs);
                Seller seller = instantiateSeller(rs, dep);

                return seller;
            }
            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

        
    }

    private Seller instantiateSeller(ResultSet rs, Department dp) throws SQLException {
        Seller seller = new Seller();
        seller.setDepartment(dp);
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setEmail(rs.getString("Email"));
        seller.setBirthDate(rs.getDate("BirthDate").toLocalDate());
        seller.setBaseSalary(rs.getDouble("BaseSalary"));

        return seller;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        return new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
    }

    @Override
    public List<Seller> findall() {
        Statement st = null;
        ResultSet rs = null;

        List<Seller> sellersList = new ArrayList<>();
        Map<Integer, Department> departmentMap = new HashMap<>();

        try {
            st = conn.createStatement();
            rs = st.executeQuery(
                "SELECT seller.*,department.Name as DepName "
                + "FROM seller " + 
                "INNER JOIN department "
                + "On seller.DepartmentId = department.Id "
                + "ORDER BY seller.Name"
                );
                
            
            
            while(rs.next()){
                Department dep = departmentMap.get(rs.getInt("DepartmentId"));

                if(dep == null){
                    dep = instantiateDepartment(rs);
                    departmentMap.put(rs.getInt("DepartmentId"), dep);
                }
                Seller seller = instantiateSeller(rs, dep);
                sellersList.add(seller);
            }
            return sellersList;
            

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        List<Seller> sellersList = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            st = conn.prepareStatement(
                "SELECT * "
                + "FROM seller "
                + "WHERE DepartmentId = ? "
                + "ORDER BY Name"
            );

            st.setInt(1, department.getId());

            rs = st.executeQuery();
            
            while(rs.next()){
                Seller seller = instantiateSeller(rs, department);
                sellersList.add(seller);
            }
            return sellersList;
            

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }
    
}
