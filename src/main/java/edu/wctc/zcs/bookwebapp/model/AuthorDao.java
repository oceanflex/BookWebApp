package edu.wctc.zcs.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Zachary
 */
public class AuthorDao implements AuthorDaoStrategy {
    private DBStrategy db = new DBMySql();
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
     * test method, please ignore
     * @param args
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthorDaoStrategy dao = new AuthorDao();
        List<Author> authors = dao.getAuthorList();
        System.out.println(authors);
    }
    
}
