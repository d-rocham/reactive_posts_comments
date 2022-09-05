package domain.entities;

import co.com.sofka.domain.generic.Entity;
import domain.identifiers.CommentID;
import domain.valueobjects.Author;
import domain.valueobjects.Content;

public class Comment extends Entity<CommentID> {

    private Author author;
    private Content content;

    public Comment(CommentID entityId, Author author, Content content) {
        super(entityId);
        this.author = author;
        this.content = content;
    }

    public Author author() {
        return author;
    }

    public Content content() {
        return content;
    }
}
