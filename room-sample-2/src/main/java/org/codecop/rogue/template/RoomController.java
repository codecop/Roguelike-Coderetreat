package org.codecop.rogue.template;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.codecop.rogue.room2.AnyRoom;
import org.codecop.rogue.room2.ExtendedRoom;
import org.codecop.rogue.room2.Item;
import org.codecop.rogue.room2.LargeRoom;
import org.codecop.rogue.room2.MonsterRoom;
import org.codecop.rogue.room2.Position;
import org.codecop.rogue.room2.SimpleRoom;

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

    private final Map<String, AnyRoom> rooms = new HashMap<>();

    public RoomController(SimpleRoom room1, ExtendedRoom room2, LargeRoom room3, MonsterRoom room4) {
        // legacy lookup
        rooms.put("1", room1);
        rooms.put("2", room2);

        rooms.put("empty", room1);
        rooms.put("key", room2);
        rooms.put("large", room3);
        rooms.put("monster", room4);
    }

    @Get("/{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<RoomResource> getLayout(@PathVariable String id) {
        AnyRoom room = rooms.get(id);
        if (room == null) {
            return HttpResponse.notFound();
        }

        RoomResource response = new RoomResource(room.description(), room.layout());
        response.setLegend(room.getLegend());

        return HttpResponse.ok().body(response);
    }

    @Get("/{id}/open")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Boolean> isDoorOpen(@PathVariable String id) {
        AnyRoom room = rooms.get(id);
        if (room == null) {
            System.out.println("404");
            return HttpResponse.notFound();
        }

        boolean canExitDoor = room.canExitDoor();

        return HttpResponse.ok().body(canExitDoor);
    }

    @Post("/{id}/walk")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<MessageResource> walk(@PathVariable String id, @QueryValue("row") int row,
            @QueryValue("column") int column) {
        AnyRoom room = rooms.get(id);
        if (room == null) {
            return HttpResponse.notFound();
        }

        Optional<String> message = room.movesTo(new Position(row, column));

        if (message.isPresent()) {
            return HttpResponse.accepted().body(new MessageResource(message.get()));
        }

        return HttpResponse.accepted();
    }

    @Post("/{id}/interact")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<MessageResource> interact(@PathVariable String id, @QueryValue("item") char item) {
        AnyRoom room = rooms.get(id);
        if (room == null) {
            return HttpResponse.notFound();
        }

        Optional<String> message = room.interactWith(new Item(item, null));

        if (message.isPresent()) {
            return HttpResponse.accepted().body(new MessageResource(message.get()));
        }

        return HttpResponse.accepted();
    }

}
