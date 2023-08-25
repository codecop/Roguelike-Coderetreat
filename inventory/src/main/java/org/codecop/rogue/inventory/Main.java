package org.codecop.rogue.inventory;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import javax.servlet.http.HttpServletResponse;

public class Main {

    public static final int PORT = 4567;

    public static void main(String[] args) {
        System.out.println("Inventory started on " + PORT + ",\n" + //
                "Open http://localhost:" + PORT + "/inventory");

        createApp();
    }

    public static void createApp() {
        port(PORT);

        Hello hello = new Hello(); // mutable data, not concurrent, not thread safe

        get("/hello", (req, res) -> {
            // TODO how to do path parameters
            // req.queryParams("type");
            return hello.nameAsJson();
        });

        post("/hello", (req, res) -> {
            String body = req.body();
            if (req.contentType().startsWith("application/json") && hello.nameFromJson(body)) {
                res.status(HttpServletResponse.SC_CREATED);
            } else {
                res.status(HttpServletResponse.SC_BAD_REQUEST);
            }
            return "";
        });

        after((req, res) -> {
            res.type("application/json");
        });
    }
}
