package org.gamma.domain.entities;

import co.com.sofka.domain.generic.Entity;
import org.gamma.domain.identifiers.CommentID;
import org.gamma.domain.valueobjects.Author;
import org.gamma.domain.valueobjects.Content;

import java.util.Objects;

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

    // Behaviour

    public void modifyContent(Content updatedContent) {
        this.content = Objects.requireNonNull(updatedContent);
    }
}
