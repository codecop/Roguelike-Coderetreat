package org.codecop.rogue.template;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;

@Controller
public class HelloController {
    // see https://itnext.io/building-restful-apis-with-micronaut-98f4eb39211c

    private final Hello hello;

    public HelloController(Hello hello) {
        this.hello = hello;
    }

    @Get("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<NameResource> hello() {
        return HttpResponse.ok().body(new NameResource(hello.getName()));
    }

    @Post("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpStatus update(@QueryValue("name") String name) {
        hello.setName(name);
        return HttpStatus.CREATED;
    }

    // @Get("/return/{number}")
    // public String issue(@PathVariable Integer number) {

}
