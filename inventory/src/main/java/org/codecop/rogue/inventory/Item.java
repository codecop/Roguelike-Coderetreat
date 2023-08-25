package org.codecop.rogue.inventory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Item {

    public final char item;
    public final String description;

    public Item(char item, String description) {
        this.item = item;
        this.description = description;
    }

    public String asJson() {
        return "{ \"item\": \"" + item + "\", \"description\": \"" + description + "\" }";
    }

    public static Item fromJson(String json) {
        Matcher charMatcher = Pattern.compile("\"item\":\\s*\"(.)\"").matcher(json);
        Matcher descMatcher = Pattern.compile("\"description\":\\s*\"([^\"]+)\"").matcher(json);
        if (!charMatcher.find() || !descMatcher.find()) {
            return null;
        }
        return new Item(charMatcher.group(1).charAt(0), descMatcher.group(1));
    }

}
