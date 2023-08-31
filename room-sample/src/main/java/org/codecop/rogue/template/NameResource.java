package org.codecop.rogue.template;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class NameResource {

    private String name;

    public NameResource() {
    }

    public NameResource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
