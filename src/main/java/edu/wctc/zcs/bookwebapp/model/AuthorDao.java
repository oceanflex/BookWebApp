package edu.wctc.zcs.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 *
 * @author Zachary
 */
@Dependent
public class AuthorDao implements AuthorDaoStrategy, Serializable {
    @Inject
    private DBStrategy db;

    public AuthorDao() {
    }

    public DBStrategy getDb() {
        return db;
    }

    public void setDb(DBStrategy db) {
        this.db = db;
    }
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String DRIVER_URL = "jdbc:mysql://localhost:3306/book";
    private final String USERNAME = "root";
    private final String PASSWORD = "admin";
    private final String NO_NAME = "";
    private final Date NO_DATE = null;
    private final String TABLE = "author";
    private final String ID_COLUMN = TABLE + "_id";
    private final String NAME_COLUMN = TABLE + "_name";
    private final String DATE_COLUMN = "date_added";
    
    /**
     * 
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException{
        db.openConnection(DRIVER, DRIVER_URL, USERNAME, PASSWORD);
        int result = db.deleteById(TABLE, id, ID_COLUMN);
        db.closeConnection();
        return result;
    }
    
    /**
     * 
     * @param id 
     * @param thingsToUpdate An ordered list of what things about the author need to be updated
     * @param newValues A list, in the same order as the other one, of the new values for the updated author
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public int updateAuthorById(Object id, List<String> thingsToUpdate, List<Object> newValues) throws ClassNotFoundException, SQLException{
        db.openConnection(DRIVER, DRIVER_URL, USERNAME, PASSWORD);
        int result = db.updateRecordByKey(TABLE, thingsToUpdate, newValues, ID_COLUMN, id);
        db.closeConnection();
        return result;
    }
    
    /**
     * 
     * @return a list of all authors
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException{
        db.openConnection(DRIVER, DRIVER_URL, USERNAME, PASSWORD);
        List<Author> back = new ArrayList();
        List<Map<String, Object>> rawData = db.findAllRecords(TABLE, 0);
        for(Map rec : rawData){
            Author obj = new Author();
            obj.setAuthorID((int) rec.get(ID_COLUMN));
            obj.setAuthorName(rec.get(NAME_COLUMN).toString() == null ? NO_NAME 
                    : rec.get(NAME_COLUMN).toString());
            obj.setDateAdded(rec.get(DATE_COLUMN)== null ? NO_DATE : (Date)(rec.get(DATE_COLUMN)));
            back.add(obj);
        }
        
        db.closeConnection();
        return back;
    }

    /**
     * 
     * @param tableName
     * @param colDescriptors
     * @param colValues
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public boolean insertRecord(String tableName, List colDescriptors, List colValues) throws ClassNotFoundException, SQLException {
        db.openConnection(DRIVER, DRIVER_URL, USERNAME, PASSWORD);
        boolean result = db.insertRecord(tableName, colDescriptors, colValues);
        
        db.closeConnection();
        return result;
    }

    /**
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public Author getAuthorById(Object id) throws ClassNotFoundException, SQLException {
        db.openConnection(DRIVER, DRIVER_URL, USERNAME, PASSWORD);
        Map author = db.findRecordById(TABLE, ID_COLUMN, id);
        db.closeConnection();
        Author back = new Author((int) author.get(ID_COLUMN));
        back.setAuthorName((String) author.get(NAME_COLUMN));
        back.setDateAdded((Date) author.get(DATE_COLUMN));
        return back;
    }
}
