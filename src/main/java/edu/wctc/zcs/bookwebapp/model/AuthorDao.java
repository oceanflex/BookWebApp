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
public class AuthorDao {
    private DBStrategy db = new DBMySql();
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String DRIVER_URL = "jdbc:mysql://localhost:3306/book";
    private final String USERNAME = "root";
    private final String PASSWORD = "admin";
    
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException{
        db.openConnection(DRIVER, DRIVER_URL, USERNAME, PASSWORD);
        List<Author> back = new ArrayList();
        List<Map<String, Object>> rawData = db.findAllRecords(USERNAME, 0);
        for(Map rec : rawData){
            Author obj = new Author();
            obj.setAuthorID((int) rec.get("author_id"));
            obj.setAuthorName(rec.get("author_name").toString() == null ? "" 
                    : rec.get("author_name").toString());
            obj.setDateAdded(rec.get("date_added")==null ? null : (Date)(rec.get("date_added")));
            back.add(obj);
        }
        
        db.closeConnection();
        return back;
    }
    
}
