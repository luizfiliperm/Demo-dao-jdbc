package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
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
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                "INSERT INTO seller "
                + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                + "VALUES "
                + "(?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, Date.valueOf(obj.getBirthDate()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());

            int rowsAffected = st.executeUpdate();

            if(rowsAffected > 0){
                rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                    System.out.println("Inserted! New id: " + id);
                }
            }else{
                throw new DbException("Unexpecd Error! No rows affected");
            }
            
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "UPDATE seller "
                + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                + "WHERE Id = ? "
                );

                
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, Date.valueOf(obj.getBirthDate()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            
            st.setInt(6, obj.getId());

            st.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;

        try {
            conn = DB.getConnection();

            st = conn.prepareStatement(
                "DELETE FROM seller "
                + "WHERE Id = ?"
            );

            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            if(rowsAffected == 0){
                throw new DbException("inexistent ID!");
            }
            
            System.out.println("Done! Rows Affected: " + rowsAffected);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally{
            DB.closeStatement(st);
        }
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
}
