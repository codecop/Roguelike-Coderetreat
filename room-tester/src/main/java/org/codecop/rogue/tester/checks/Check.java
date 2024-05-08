package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.model.Response;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Check {
    public static void statusCodeIs(Findings findings, Response response, Integer... codes) {
        List<Integer> list = Arrays.asList(codes);
        if (!list.contains(response.statusCode)) {
            findings.error("Expect Status Code " + list + ", was " + response.statusCode);
        }
    }

    public static void contentTypeIsJson(Findings findings, Response response) {
        if (!response.hasJsonContentType()) {
            findings.warn("Expect ContentType '" + Response.CONTENT_TYPE + "', was " + response.contentType);
        }
    }

    public static void jsonBodyOnlyHas(Findings findings, Response response, String... allowedJsonKeys) {
        jsonBodyOnlyHas(findings, response, Arrays.asList(allowedJsonKeys));
    }

    public static void jsonBodyOnlyHas(Findings findings, Response response, Collection<String> allowedJsonKeys) {
        if (response.jsonBody != null) {
            JSONObject json = response.jsonBody;
            for (String key : json.keySet()) {
                if (!allowedJsonKeys.contains(key)) {
                    findings.warn("Unexpected key in JSON " + key);
                }
            }
        } else if (response.body != null && !response.body.isEmpty()) {
            findings.warn("Unexpected body " + response.body);
        }
    }
}
