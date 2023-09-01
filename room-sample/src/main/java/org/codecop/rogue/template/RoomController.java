package org.codecop.rogue.template;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.codecop.rogue.room1.AnyRoom;
import org.codecop.rogue.room1.ExtendedRoom;
import org.codecop.rogue.room1.Room;

import io.micronaut.http.HttpResponse;
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

    public RoomController(Room room1, ExtendedRoom room2) {
        rooms.put(1, room1);
        rooms.put(2, room2);
    }

    @Get("/{number}/")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<RoomResource> getRoom(@PathVariable Integer number) {
        AnyRoom room = rooms.get(number);

        RoomResource response = new RoomResource(room.description(), room.display());
        response.setLegend(room.getLegend());

        return HttpResponse.ok().body(response);
    }

    @Post("/{number}/")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<MessageResource> update(@PathVariable Integer number, @QueryValue("action") char direction) {
        AnyRoom room = rooms.get(number);

        Optional<String> message = room.playerMoves(direction);

        if (message.isPresent()) {
            return HttpResponse.accepted().body(new MessageResource(message.get()));
        }
        
        return HttpResponse.accepted();
    }

}
