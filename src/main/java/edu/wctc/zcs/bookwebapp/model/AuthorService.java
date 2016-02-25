package edu.wctc.zcs.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Zachary
 */
@SessionScoped
public class AuthorService implements Serializable {
    @Inject
    private AuthorDaoStrategy dao;
    
    //db config init params from web.xml
    private String driver;
    private String driverUrl;
    private String username;
    private String password;

    public AuthorService() {
    }

    public AuthorDaoStrategy getDao() {
        return dao;
    }

    public void setDao(AuthorDaoStrategy dao) {
        this.dao = dao;
    }
    
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
