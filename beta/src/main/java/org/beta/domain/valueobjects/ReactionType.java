package org.beta.domain.valueobjects;

import co.com.sofka.domain.generic.ValueObject;

public class ReactionType implements ValueObject<String> {

    private final String reactionType;

    public ReactionType(String reactionType) {
        this.reactionType = reactionType;
    }

    @Override
    public String value() {
        return reactionType;
    }
}
