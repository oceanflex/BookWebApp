package edu.wctc.zcs.bookwebapp.repository;

import edu.wctc.zcs.bookwebapp.entity.Book;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jlombardo
 */
public interface BookRepository extends JpaRepository<Book, Integer>, Serializable {
    
}
