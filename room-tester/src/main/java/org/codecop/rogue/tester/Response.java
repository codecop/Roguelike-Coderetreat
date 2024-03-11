package org.codecop.rogue.tester;

import org.json.JSONObject;

public class Response {
    final int statusCode;
    final String contentType; // or null if not set
    final String body; // or null if not set
    final JSONObject jsonBody; // or null if not set

    public Response(int statusCode, String contentType, String body, JSONObject jsonBody) {
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.body = body;
        this.jsonBody = jsonBody;
    }
}
