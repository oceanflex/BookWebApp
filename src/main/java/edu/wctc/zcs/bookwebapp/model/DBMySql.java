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
    
    @Override
    public void openConnection(String driver, String url, String userName, String password) 
            throws ClassNotFoundException, SQLException{
        
        //conn.close();
        Class.forName(driver);
        conn = DriverManager.getConnection(url,userName,password);
    }
    
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
        //int rawData = db.deleteById("author",2,"author_id");
        db.closeConnection();
        System.out.println(rawData);//.get(0).get("author_id").getClass());
    }

    /**
     * 
     * @param tableName tableName needs to be checked against a white list of tables in your database before being input
     * @param id The value of primary key of the entry to be removed
     * @param primaryKeyColumn the name of the primary key column
     * @return 
     */
    @Override
    public int deleteById(String tableName, Object id, String primaryKeyColumn) {
        
        try {
            String sql = "DELETE FROM "+tableName+" WHERE ? LIKE ? ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            //stmt.setString(1, tableName);
            stmt.setString(1, primaryKeyColumn);
            stmt.setObject(2, id);
            
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBMySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
