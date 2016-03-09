package edu.wctc.zcs.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author Zachary
 */
public interface DBStrategy {
    
    public abstract void openConnection(String driver, String url, String userName, String password)throws ClassNotFoundException, SQLException;
    public void openConnection(DataSource ds) throws SQLException;
    public abstract void closeConnection() throws SQLException;
     
    public abstract boolean insertRecord(String tableName, List colDescriptors,
            List colValues) throws SQLException;
    
    public abstract int deleteById(String tableName, Object id, String primaryKey)
            throws SQLException;
    
    public abstract int updateRecordByKey(String tableName, List<String> colNamesToUpdate,
            List<Object> colValuesToUpdate, String keyColumn, Object keyValue) 
            throws SQLException;
    
    public abstract Map<String,Object> findRecordById(String tableName, String pKey, Object pValue) throws ClassNotFoundException, SQLException;
    public abstract List<Map<String,Object>> findAllRecords(String tableName, int maxRecords)
            throws SQLException;
    
}
