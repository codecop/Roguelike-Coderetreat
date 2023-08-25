package org.codecop.rogue.inventory;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.delete;
import static spark.Spark.port;
import static spark.Spark.post;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

public class Main {

    public static final int PORT = 8001;
    private static Inventory inventory;

    public static void main(String[] args) {
        System.out.println("Inventory started on " + PORT + ",\n" + //
                "Open http://localhost:" + PORT + "/inventory");

        createApp();
    }

    public static void createApp() {
        port(PORT);

        inventory = new Inventory();

        // return list of all items
        get("/inventory", (req, res) -> {
            return "[" + inventory.list().stream(). //
                map(Item::asJson). //
                collect(Collectors.joining(",")) + "]";
        });

        // return item
        get("/inventory/:item", (req, res) -> {
            String key = req.params("item");
            Item item = inventory.getItem(key.charAt(0));
            if (item == null) {
                res.status(HttpServletResponse.SC_NOT_FOUND);
                return "";
            }
            return item.asJson();
        });

        // remove item
        delete("/inventory/:item", (req, res) -> {
            String key = req.params("item");
            inventory.removeItem(key.charAt(0));
            res.status(HttpServletResponse.SC_ACCEPTED);
            return "";
        });
        
        // add item
        post("/inventory", (req, res) -> {
            String body = req.body();
            if (!req.contentType().startsWith("application/json")) {
                res.status(HttpServletResponse.SC_BAD_REQUEST);
                return "";
            }
            Item item = Item.fromJson(body);
            if (item == null) {
                res.status(HttpServletResponse.SC_BAD_REQUEST);
                return "";
            }

            inventory.putItem(item);
            res.status(HttpServletResponse.SC_CREATED);
            return "";
        });

        after((req, res) -> {
            res.type("application/json");
        });
    }
}
