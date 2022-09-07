package org.beta.domain.identifiers;

import co.com.sofka.domain.generic.Identity;

public class ReactionID extends Identity {
    public ReactionID(String id) {
        super(id);
    }

    public ReactionID() {
    }

    public static ReactionID of(String id) {
        return new ReactionID(id);
    }
}
