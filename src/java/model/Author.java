package model;

import java.util.Date;

/**
 *
 * @author Zachary
 */
public class Author {
    private int authorID;
    private String authorName;
    private Date dateAdded;

    public Author() {
        this.dateAdded = new Date();
    }

    public Author(int authorID) {
        this.authorID = authorID;
        this.dateAdded = new Date();
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
    
    
}
