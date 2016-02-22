package edu.wctc.zcs.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Zachary
 */
public class MockAuthorDao implements AuthorDaoStrategy{
    private List<Author> authors;

    public MockAuthorDao() {
        Author bill = new Author(007);
        Author alex = new Author(101);
        Author cody = new Author(042);
       bill.setAuthorName("Bill Nye");
       alex.setAuthorName("Albert Einstien");
       cody.setAuthorName("Cody Stark");
       authors = new ArrayList<Author>(3);
       authors.add(bill);
       authors.add(alex);
       authors.add(cody);
    }
    
    @Override
    public List<Author> getAuthorList(){
        return authors;
    }

    @Override
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException {
        
        return 1;
    }

    @Override
    public boolean insertRecord(String tableName, List colDescriptors, List colValues) throws ClassNotFoundException, SQLException {
        Author e = new Author((int)(Math.random()*100));
        e.setAuthorName((String) colDescriptors.get(0));
        e.setDateAdded((Date) colDescriptors.get(1));
        authors.add(e);
        return true;
    }

    @Override
    public int updateAuthorById(Object id, List<String> thingsToUpdate, List<Object> newValues) throws ClassNotFoundException, SQLException {
        return 1;
    }
}
