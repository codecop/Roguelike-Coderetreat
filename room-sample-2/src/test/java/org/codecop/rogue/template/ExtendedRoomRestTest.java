package org.codecop.rogue.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
class ExtendedRoomRestTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    public void findKey() {
        String body = client.toBlocking().retrieve( // 
                HttpRequest.POST("/key/interact?item=c", ""));
        assertEquals("{\"message\":\"There is a key in the chest.\"}", body);
    }

}
