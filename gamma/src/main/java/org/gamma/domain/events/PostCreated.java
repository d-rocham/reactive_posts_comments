package org.gamma.domain.events;

import co.com.sofka.domain.generic.DomainEvent;

public class PostCreated extends DomainEvent {

    private String title;
    private String author;

    // Why does it have two constructors, unlike regular non AR entities?

    public PostCreated() {
        super("domain.postCreated");
    }

    public PostCreated(String title, String author) {
        super("domain.postCreated");
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
