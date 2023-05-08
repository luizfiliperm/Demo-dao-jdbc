package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        // TODO Auto-generated method stub
        return null;
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
