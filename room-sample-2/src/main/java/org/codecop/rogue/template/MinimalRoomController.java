package org.codecop.rogue.template;

import org.codecop.rogue.room2.MinimalRoom;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;

@Controller("/minimal")
public class MinimalRoomController {

    private final MinimalRoom room;

    public MinimalRoomController(MinimalRoom room1) {
        this.room = room1;
    }

    @Get("/")
    public HttpResponse<String> getLayout() {
        return HttpResponse.ok().body("{\"layout\":\"" + room.layout().replaceAll("\n", "\\\\n") + "\"}");
    }

    @Post("/walk")
    public void walk(@QueryValue("row") int row, @QueryValue("column") int column) {
        room.movesTo(column, row);
    }

}
