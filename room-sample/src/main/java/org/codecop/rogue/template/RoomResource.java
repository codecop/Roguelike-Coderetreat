package org.codecop.rogue.template;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class RoomResource {

    private String description;
    private String layout;
    // no legend

    public RoomResource() {
    }

    public RoomResource(String description, String layout) {
        this.description = description;
        this.layout = layout;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

}
