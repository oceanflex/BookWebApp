package edu.wctc.zcs.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Zachary
 */
public interface AuthorDaoStrategy {
    public abstract DBStrategy getDb();
    public abstract void setDb(DBStrategy db);

    public abstract Author getAuthorById(Object id) throws ClassNotFoundException, SQLException;
    public abstract List<Author> getAuthorList() throws ClassNotFoundException, SQLException;
    public abstract int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException;
    public abstract boolean insertRecord(String tableName, List colDescriptors,
            List colValues) throws ClassNotFoundException, SQLException;
    public int updateAuthorById(Object id, List<String> thingsToUpdate, List<Object> newValues) throws ClassNotFoundException, SQLException;
    
}
