package org.fasttrackit.countries.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final long entityId;

    public ResourceNotFoundException(String message, long entityId) {
        super(message);
        this.entityId = entityId;
    }
}
