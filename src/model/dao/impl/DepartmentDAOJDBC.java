package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDAOJDBC implements DepartmentDao{

    private Connection conn;

    public DepartmentDAOJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        // TODO Auto-generated method stub
    }

    @Override
    public void update(Department obj) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                "SELECT * from department " +
                "WHERE id = ?"
            );

            st.setInt(1, id);

            rs = st.executeQuery();

            if(rs.next()){
                Department dep = instantiateDepartment(rs);
                return dep;
            }
            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally{
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        return new Department(rs.getInt("Id"), rs.getString("Name"));
    }

    @Override
    public List<Department> findall() {
        // TODO Auto-generated method stub
        return null; 
    }
    
}
