package edu.wctc.zcs.bookwebapp.model;

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

    public Author(int authorID, String authorName, Date dateAdded) {
        this.authorID = authorID;
        this.authorName = authorName;
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return "Author{" + "authorID=" + authorID + ", authorName=" + authorName + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.authorID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if (this.authorID != other.authorID) {
            return false;
        }
        return true;
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
