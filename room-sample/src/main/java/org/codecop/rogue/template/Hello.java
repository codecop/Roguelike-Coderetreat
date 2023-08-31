package org.codecop.rogue.template;

import jakarta.inject.Singleton;

@Singleton
public class Hello {

    private String name = "World!";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
