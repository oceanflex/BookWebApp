package edu.wctc.zcs.bookwebapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zachary
 */
public class AuthorService {
    private List<Author> authors;

    public AuthorService() {
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
    
    public List getAuthors(){
        return authors;
    }
}
