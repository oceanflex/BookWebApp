package edu.wctc.zcs.bookwebapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zachary
 */
public class DBMySql implements DBStrategy {
    private Connection conn;
    
    public DBMySql() {
    }
    
    /**
     * This method is a prerequisite for running any queries
     * @param driver location of the driver class for the mySql database
     * @param url the network location of the database to open a connection with
     * @param userName for authentication with the database
     * @param password for authentication with the database
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public void openConnection(String driver, String url, String userName, String password) 
            throws ClassNotFoundException, SQLException{
        
        //conn.close();
        Class.forName(driver);
        conn = DriverManager.getConnection(url,userName,password);
    }
    
    /**
     * Run this method at your earliest convenience after querying the database.
     * @throws SQLException 
     */
    @Override
    public void closeConnection() throws SQLException{
        conn.close();
    }
    /**
     * Open a connection before calling this method, and close the connection 
     * afterwards. Future optimizations may return an array.
     * @param tableName - Name of the table to find results within.
     * @param maxRecords - Limits number of records to be returned to the input 
     * number, or this parameter is less than one (1) there is no limit.
     * @return 
     */
    @Override
    public List<Map<String,Object>> findAllRecords(String tableName, int maxRecords)
            throws SQLException{
        String sql = "SELECT * FROM "+ tableName +" LIMIT "+ maxRecords+";";
        if(maxRecords < 1) sql = "SELECT * FROM "+ tableName+";";
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        
        List<Map<String,Object>> records = new ArrayList<>();
        
        while(rs.next()){
            Map<String,Object> record = new HashMap<>();
            for(int colNum = 1; colNum <= colCount; colNum++){
                Object colData = rs.getObject(colNum);
                String colName = rsmd.getColumnName(colNum);
                record.put(colName, colData);
            }
            records.add(record);
        }
        return records;
    }
    /**
     * test method, please ignore
     * @param args
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public static void main(String[] args) 
            throws ClassNotFoundException, SQLException {
        DBStrategy db = new DBMySql();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        List<Map<String,Object>> rawData = db.findAllRecords("author",0);
        //int rawData = db.deleteById("author",5,"author_id");
        db.closeConnection();
        System.out.println(rawData);//.get(0).get("author_id").getClass());
    }

    /**
     * Open a connection before calling this method, and close the connection 
     * afterwards.
     * @param tableName tableName needs to be checked against a white list of tables in your database before being input
     * @param id The value of primary key of the entry to be removed
     * @param primaryKeyColumn the name of the primary key column
     * @return 
     * @throws java.sql.SQLException 
     */
    @Override
    public int deleteById(String tableName, Object id, String primaryKeyColumn) throws SQLException {
        
            String sql = "DELETE FROM "+tableName+" WHERE "+primaryKeyColumn+"  LIKE ? ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setObject(1, id);
            
            return stmt.executeUpdate();
    }
}
