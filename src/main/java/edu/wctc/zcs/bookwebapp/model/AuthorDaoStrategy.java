package edu.wctc.zcs.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Zachary
 */
public interface AuthorDaoStrategy {

    List<Author> getAuthorList() throws ClassNotFoundException, SQLException;
    
}
