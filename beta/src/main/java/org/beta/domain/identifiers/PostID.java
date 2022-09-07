package org.beta.domain.identifiers;

import co.com.sofka.domain.generic.Identity;

public class PostID extends Identity {
    public PostID(String id) {
        super(id);
    }

    public PostID() {
    }

    public static PostID of(String id) {
        return new PostID(id);
    }
}
