<?php

namespace App;

use Illuminate\Http\Request;
use Laravel\Lumen\Routing\Controller;

class RoomController extends Controller
{
    private Room $room;

    public function __construct(Room $room)
    {
        $this->room = $room;
    }

    public function get()
    {
        $data = array(
            "layout" => $this->room->serialize()
        );
        $content = json_encode($data);

        return response($content, 200)
            ->header('Content-Type', 'application/json');
    }

    public function walk(Request $request)
    {
        if ($request->exists('row') && $request->exists('column')) {
            $row = $request->input("row");
            $column = $request->input("column");
            $this->room->setNewPlayerPosition(new Position($column, $row));
            return response('', 201);
        }
        return response('', 400);
    }
}
