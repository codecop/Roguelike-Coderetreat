package org.codecop.rogue.template;

import org.codecop.rogue.room1.Room;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;

@Controller
public class RoomController {

    private final Room room;

    public RoomController(Room room) {
        this.room = room;
    }

    @Get("/1/")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<RoomResource> getRoom() {
        return HttpResponse.ok().body( //
                new RoomResource("A You are in a little square room. There is nothing here.", room.display()));
    }

    @Post("/1/")
    public HttpStatus update(@QueryValue("action") char direction) {
        room.playerMoves(direction);
        return HttpStatus.ACCEPTED;
    }

    // @Get("/return/{number}")
    // public String issue(@PathVariable Integer number) {

}
