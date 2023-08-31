package org.codecop.rogue.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
class HelloRestTest {

    @Inject
    EmbeddedApplication<?> application;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void firstHello() {
        HttpResponse<String> response = client.toBlocking().exchange( //
                HttpRequest.GET("/hello").accept(MediaType.APPLICATION_JSON_TYPE));
        assertEquals(200, response.code());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getContentType().get());

        // response.body is null, why?
        String body = client.toBlocking().retrieve( //
                HttpRequest.GET("/hello").accept(MediaType.APPLICATION_JSON_TYPE));
        assertEquals("{\"name\":\"World!\"}", body);
    }

    @Test
    public void zupdate() {
        HttpResponse<Object> post = client.toBlocking().exchange( // 
                HttpRequest.POST("/hello", "{ \"name\":\"Peter\" }"). // 
                        contentType(MediaType.APPLICATION_JSON_TYPE));
        assertEquals(201, post.code());

        String body = client.toBlocking().retrieve( //
                HttpRequest.GET("/hello").accept(MediaType.APPLICATION_JSON_TYPE));
        assertEquals("{\"name\":\"Peter\"}", body);
    }

}
