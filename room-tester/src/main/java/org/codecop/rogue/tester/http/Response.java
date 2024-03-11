package org.codecop.rogue.tester.http;

import org.json.JSONObject;

public class Response {
    public final int statusCode;
    public final String contentType; // or null if not set
    public final String body; // or null if not set
    public final JSONObject jsonBody; // or null if not set

    public Response(int statusCode, String contentType, String body, JSONObject jsonBody) {
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.body = body;
        this.jsonBody = jsonBody;
    }
}
