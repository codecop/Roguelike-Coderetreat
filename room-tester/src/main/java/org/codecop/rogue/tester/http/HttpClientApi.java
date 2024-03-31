package org.codecop.rogue.tester.http;

import org.codecop.rogue.tester.model.Response;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientApi implements Api {
    // see https://dzone.com/articles/java-11-http-client-api-to-consume-restful-web-ser-1

    private final HttpClient client = HttpClient.newBuilder(). //
            version(HttpClient.Version.HTTP_1_1). //
            build();

    @Override
    public Response get(String url) {
        URI uri = URI.create(url);

        HttpRequest getRequest = HttpRequest.newBuilder(uri). //
                header("Accept", Response.CONTENT_TYPE). //
                GET(). //
                build(); //

        HttpResponse.BodyHandler<String> noBody = HttpResponse.BodyHandlers. //
                ofString();

        HttpResponse<String> response = send(getRequest, noBody);

        return new Response(response.statusCode(), //
                getContentType(response),
                response.body()
        );
    }

    private HttpResponse<String> send(HttpRequest request, HttpResponse.BodyHandler<String> body) {
        try {
            return client.send(request, body);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String getContentType(HttpResponse<String> response) {
        return response.headers().firstValue("Content-Type").orElse(null);
    }

}
