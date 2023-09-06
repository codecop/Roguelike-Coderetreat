package org.codecop.rogue.template;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class MessageResource {

    private String message;

    public MessageResource() {
    }

    public MessageResource(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
