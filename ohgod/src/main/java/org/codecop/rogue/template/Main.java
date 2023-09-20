package org.codecop.rogue.template;

import com.google.gson.Gson;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static final int PORT = 9001;

    public static void main(String[] args) {
        System.out.println("RPG game with OhGod map started on " + PORT + ",\\n" + //
                "Open http://localhost:" + PORT + "/ohgod");

        createApp();
    }

    public static void createApp() {
        Room room = new Room();
        port(PORT);

        get("/ohgod", (req, res) -> {
            Gson gson = new Gson();
            Map<String,String> responseBody = new HashMap<>();

            String roomLayout =  room.toString();

            responseBody.put("layout",roomLayout);
            return gson.toJson(responseBody);
        });

        post("/ohgod/walk", (req, res) -> {
            if ( !req.queryParams("row").isEmpty()
                    && !req.queryParams("column").isEmpty()
            ) {
                String row = req.queryParams("row");
                String column = req.queryParams("column");

                room.setNewPosition(Integer.parseInt(row),Integer.parseInt(column));

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
