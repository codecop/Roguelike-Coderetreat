package org.codecop.rogue.template;

/**
 * Domain class with JSON support.
 */
public class Hello {

    private String name = "World!";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String nameAsJson() {
        return "{ \"name\": \"" + getName() + "\"}";
    }

}
