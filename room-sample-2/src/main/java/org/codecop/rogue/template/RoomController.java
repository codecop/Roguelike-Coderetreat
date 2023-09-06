package org.codecop.rogue.template;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.codecop.rogue.room2.AnyRoom;
import org.codecop.rogue.room2.ExtendedRoom;
import org.codecop.rogue.room2.Item;
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

    private final Map<Integer, AnyRoom> rooms = new HashMap<>();

    public RoomController(SimpleRoom room1, ExtendedRoom room2) {
        rooms.put(1, room1);
        rooms.put(2, room2);
    }

    @Get("/{number}/")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<RoomResource> getLayout(@PathVariable Integer number) {
        AnyRoom room = rooms.get(number);

        RoomResource response = new RoomResource(room.description(), room.layout());
        response.setLegend(room.getLegend());

        return HttpResponse.ok().body(response);
    }

    @Get("/{number}/open")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Boolean> isDoorOpen(@PathVariable Integer number) {
        AnyRoom room = rooms.get(number);

        boolean canExitDoor = room.canExitDoor();

        return HttpResponse.ok().body(canExitDoor);
    }

    @Post("/{number}/walk")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<MessageResource> walk(@PathVariable Integer number, @QueryValue("row") int row,
            @QueryValue("column") int column) {
        AnyRoom room = rooms.get(number);

        Optional<String> message = room.movesTo(new Position(row, column));

        if (message.isPresent()) {
            return HttpResponse.accepted().body(new MessageResource(message.get()));
        }

        return HttpResponse.accepted();
    }

    @Post("/{number}/interact")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<MessageResource> interact(@PathVariable Integer number, @QueryValue("item") char item) {
        AnyRoom room = rooms.get(number);

        Optional<String> message = room.interactWith(new Item(item, null));

        if (message.isPresent()) {
            return HttpResponse.accepted().body(new MessageResource(message.get()));
        }

        return HttpResponse.accepted();
    }

}
