package org.codecop.rogue.template;

import java.util.HashMap;
import java.util.Map;

import org.codecop.rogue.room1.AnyRoom;
import org.codecop.rogue.room1.Room;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;

@Controller
public class RoomController {

    private final Map<Integer, AnyRoom> rooms = new HashMap<>();

    public RoomController(Room room1) {
        rooms.put(1, room1);
    }

    @Get("/{number}/")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<RoomResource> getRoom(@PathVariable Integer number) {
        AnyRoom room = rooms.get(number);
        return HttpResponse.ok().body( //
                new RoomResource(room.decription(), room.display()));
    }

    @Post("/{number}/")
    public HttpStatus update(@PathVariable Integer number, @QueryValue("action") char direction) {
        AnyRoom room = rooms.get(number);
        room.playerMoves(direction);
        return HttpStatus.ACCEPTED;
    }

}
