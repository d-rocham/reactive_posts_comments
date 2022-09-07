package org.beta.domain.identifiers;

import co.com.sofka.domain.generic.Identity;

public class CommentID extends Identity {

    public CommentID(String id) {
        super(id);
    }

    public CommentID() {
    }

    public static CommentID of(String id) {
        return new CommentID(id);
    }

}
