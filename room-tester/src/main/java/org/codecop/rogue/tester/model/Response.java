package org.codecop.rogue.tester.model;

import org.json.JSONObject;

import java.util.Optional;

public class Response {
    public static final String CONTENT_TYPE = "application/json";

    public final int statusCode;
    public final String contentType; // or null if not set

    public boolean hasJsonContentType() {
        return contentType != null && contentType.startsWith(CONTENT_TYPE);
    }

    public final String body; // or null if not set

    public final JSONObject jsonBody; // or null if not set

    public Response(int statusCode, String contentType, String body) {
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.body = body;
        this.jsonBody = asJson(body);
    }

    private static JSONObject asJson(String body) {
        boolean hasBody = body != null && body.length() > 0 && body.trim().startsWith("{");
        return hasBody ? new JSONObject(body) : null;
    }

    /**
     * Shortcut method.
     */
    public String getLayoutString() {
        String key = "layout";
        return jsonBody.getString(key);
    }

    public Layout getLayout() {
        return new Layout(getLayoutString());
    }

    public Optional<String> getDescription() {
        String key = "description";
        if (jsonBody.has(key)) {
            return Optional.of(jsonBody.getString(key));
        }
        return Optional.empty();
    }

}
