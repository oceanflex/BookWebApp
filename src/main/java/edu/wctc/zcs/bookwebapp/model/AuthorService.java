package edu.wctc.zcs.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Zachary
 */
public class AuthorService {
    private AuthorDaoStrategy dao = new AuthorDao();
    
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException{
        return dao.getAuthorList();
    }
    public Author getAuthorById(Object id) throws ClassNotFoundException, SQLException{
        return dao.getAuthorById(id);
    }
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException{
        return dao.deleteAuthorById(id);
    }
    public int updateAuthorById(Object id, List<String> toUpdate, List<Object> values) throws ClassNotFoundException, SQLException{
        return dao.updateAuthorById(id, toUpdate, values);
    }
    public boolean insertRecord(String tableName, List colDescriptors,
            List colValues) throws ClassNotFoundException, SQLException{
        return dao.insertRecord(tableName, colDescriptors, colValues);
    }
}
