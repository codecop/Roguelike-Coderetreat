package org.codecop.rogue.stats;

import static io.micronaut.http.HttpHeaders.USER_AGENT;

import org.reactivestreams.Publisher;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

@Client(id = "stats")
@Header(name = USER_AGENT, value = "Micronaut HTTP Client")
public interface StatsClient {

    // see https://guides.micronaut.io/latest/micronaut-http-client-maven-java.html

    @Get("hp")
    Publisher<StatsHp> fetchHp();

    @Post("hp?action=hit")
    Publisher<HttpResponse<String>> hit();
}
