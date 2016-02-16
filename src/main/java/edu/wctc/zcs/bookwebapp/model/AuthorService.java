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
}
