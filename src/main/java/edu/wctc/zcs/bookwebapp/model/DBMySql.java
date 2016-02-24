package edu.wctc.zcs.bookwebapp.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Zachary
 */
@SessionScoped
public class DBMySql implements DBStrategy, Serializable {
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
     *  Open a connection before calling this method, and close the connection 
     * afterwards. Future optimizations may return an array.
     * @param tableName - Name of the table to find results within.
     * @param pKey - name of the primary key column
     * @param pValue - value of the primary key (id) of the record
     * @return 
     * @throws SQLException 
     */
    @Override
    public Map<String,Object> findRecordById(String tableName, String pKey, Object pValue) throws SQLException{
        String sql = "SELECT * FROM "+ tableName +" WHERE "+ pKey+"="+pValue+";";
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        
        
            Map<String,Object> record = new HashMap<>();
            for(int colNum = 1; colNum <= colCount; colNum++){
                Object colData = rs.getObject(colNum);
                String colName = rsmd.getColumnName(colNum);
                record.put(colName, colData);
            }
        
        return record;
    }
    
    /**
     * Run the openConnection method before this, and run closeConnection afterwards
     * at your earliest convenience.
     * @param tableName Name of the table where the record(s) to be updated exist
     * @param colNamesToUpdate List of column names that you want to update, in order
     * @param colValuesToUpdate List of values for those columns, in the SAME order
     * @param keyColumn Column to be checked, in order to confirm the target to be updated
     * @param keyValue Value that needs to match in the column to confirm update
     * @return
     * @throws SQLException 
     */
    public int updateRecordByKey(String tableName, List<String> colNamesToUpdate,
            List<Object> colValuesToUpdate, String keyColumn, Object keyValue) 
            throws SQLException{
        PreparedStatement pstmt = null;
        int recsUpdated = 0;

        pstmt = buildUpdateStatement(conn,tableName,colNamesToUpdate,keyColumn);

        final Iterator i=colValuesToUpdate.iterator();
        int index = 1;
        Object obj = null;

        // set params for column values
        while( i.hasNext()) {
            obj = i.next();
            pstmt.setObject(index++, obj);
        }
        // and finally set param for wehere value
        pstmt.setObject(index,keyValue);

        recsUpdated = pstmt.executeUpdate();

        try {
                pstmt.close();
                conn.close();
        } catch(SQLException e) {
                throw e;
        } // end try

        return recsUpdated;
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
    
    /**
    * Builds a java.sql.PreparedStatement for an sql update using only one where clause test
    * @param conn - a JDBC <code>Connection</code> object
    * @param tableName - a <code>String</code> representing the table name
    * @param colDescriptors - a <code>List</code> containing the column descriptors for
    * the fields that can be updated.
    * @param whereField - a <code>String</code> representing the field name for the
    * search criteria.
    * @return java.sql.PreparedStatement
    * @throws SQLException
    */
    private PreparedStatement buildUpdateStatement(Connection conn_loc, 
                String tableName, List colDescriptors, String whereField) throws SQLException {
            
        StringBuffer sql = new StringBuffer("UPDATE ");
        (sql.append(tableName)).append(" SET ");
        final Iterator i=colDescriptors.iterator();
        
        while( i.hasNext() ) {
            (sql.append( (String)i.next() )).append(" = ?, ");
        }
        
        sql = new StringBuffer( (sql.toString()).substring( 0,(sql.toString()).lastIndexOf(", ") ) );
        ((sql.append(" WHERE ")).append(whereField)).append(" = ?");
        final String finalSQL=sql.toString();
        
        return conn_loc.prepareStatement(finalSQL);
    }
    
    /**
     * Inserts a record into a table based on a <code>List</code> of column
     * descriptors and a one-to-one mapping of an associated <code>List</code>
     * of column values.
     * 
     * authored by jlombardo
     * @param tableName - a <code>String</code> representing the table name
     * @param colDescriptors - <code>List</code> containing the column
     * descriptors
     * @param colValues - <code>List</code> containing the column values. The
     * order of these values must match the order of the column descriptors.
     * @return <code>true</code> if successfull; <code>false</code> otherwise
     * @throws DataAccessException if database access error or illegal sql
     */
    @Override
    public final boolean insertRecord(String tableName, List colDescriptors,
            List colValues) throws SQLException {

        PreparedStatement pstmt = null;
        int recsUpdated = 0;

		// do this in an excpetion handler so that we can depend on the
        // finally clause to close the connection
        //try {
            pstmt = buildInsertStatement(conn, tableName, colDescriptors);

            final Iterator i = colValues.iterator();
            int index = 1;
            while (i.hasNext()) {
                final Object obj = i.next();
                pstmt.setObject(index++, obj);
            }
            recsUpdated = pstmt.executeUpdate();

        /*} catch (SQLException sqle) {
            throw new DataAccessException(sqle.getMessage(),sqle.getCause());
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(),e.getCause());
        } finally {*/
            //try {
                pstmt.close();
                conn.close();
            /*} catch (SQLException e) {
                throw new DataAccessException(e.getMessage(),e.getCause());
            } // end try
        } // end finally*/

        if (recsUpdated == 1) {
            return true;
        } else {
            return false;
        }
    }
    
    /*
     * Builds a java.sql.PreparedStatement for an sql insert
     * authored by jlombardo
     * @param conn - a valid connection
     * @param tableName - a <code>String</code> representing the table name
     * @param colDescriptors - a <code>List</code> containing the column descriptors for
     * the fields that can be inserted.
     * @return java.sql.PreparedStatement
     * @throws DataAccessException
     */
    private PreparedStatement buildInsertStatement(Connection conn, String tableName, List colDescriptors) throws SQLException
             {
        StringBuffer sql = new StringBuffer("INSERT INTO ");
        (sql.append(tableName)).append(" (");
        final Iterator i = colDescriptors.iterator();
        while (i.hasNext()) {
            (sql.append((String) i.next())).append(", ");
        }
        sql = new StringBuffer((sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")) + ") VALUES (");
        for (int j = 0; j < colDescriptors.size(); j++) {
            sql.append("?, ");
        }
        final String finalSQL = (sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")) + ")";
        //System.out.println(finalSQL);
        PreparedStatement psmt = null;
        //try {
            psmt = conn.prepareStatement(finalSQL);
        /*} catch(SQLException e) {
            throw new DataAccessException(e.getMessage(),e.getCause());
        }*/
        return psmt;
    }
}
