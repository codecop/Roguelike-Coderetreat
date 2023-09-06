package org.codecop.rogue.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
class SimpleRoomRestTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void aInitialRoom() {
        String body = client.toBlocking().retrieve( //
                HttpRequest.GET("/1/").accept(MediaType.APPLICATION_JSON_TYPE));
        assertEquals(
                "{\"description\":\"You are in a little square room. There is nothing here.\","
                        + "\"layout\":\"#######\\n#  @  #\\n#     #\\n#     |\\n#     #\\n#     #\\n#######\\n\"}",
                body);
    }

    @Test
    public void zGoRight() {
        HttpResponse<Object> post = client.toBlocking().exchange( // 
                HttpRequest.POST("/1/walk?row=1&column=4", ""));
        assertEquals(202, post.code());

        String body = client.toBlocking().retrieve( //
                HttpRequest.GET("/1/").accept(MediaType.APPLICATION_JSON_TYPE));
        assertEquals(
                "{\"description\":\"You are in a little square room. There is nothing here.\","
                        + "\"layout\":\"#######\\n#   @ #\\n#     #\\n#     |\\n#     #\\n#     #\\n#######\\n\"}",
                body);
    }

}
