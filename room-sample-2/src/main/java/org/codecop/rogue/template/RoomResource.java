package org.codecop.rogue.template;

import java.util.ArrayList;
import java.util.List;

import org.codecop.rogue.room2.Item;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class RoomResource {

    private String description;
    private String layout;
    private List<Item> legend;

    public RoomResource() {
    }

    public RoomResource(String description, String layout) {
        this.description = description;
        this.layout = layout;
        this.legend = new ArrayList<>();
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

    public List<Item> getLegend() {
        return legend;
    }

    public void setLegend(List<Item> legend) {
        this.legend = legend;
    }

}
