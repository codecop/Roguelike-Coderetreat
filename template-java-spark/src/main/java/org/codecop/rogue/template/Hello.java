package org.codecop.rogue.template;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public boolean nameFromJson(String json) {
        Matcher matcher = Pattern.compile("\"name\":\\s*\"([^\"]+)\"").matcher(json);
        if (!matcher.find()) {
            return false;
        }

        setName(matcher.group(1));
        return true;
    }

}
