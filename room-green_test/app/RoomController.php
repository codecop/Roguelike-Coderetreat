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
            "layout" => $this->room->serialize(),
            "description" => "You entered a room full of infinite loops there is no exit in sight. Can you find the hidden backdoor?",
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
            $content = "";
            if ($this->room->hasDoor())
            {
                $data = array(
                    "message" => "Backdoor found run !!!!",
                );
                $content = json_encode($data);
            }
            return response($content, 201);
        }
        return response('', 400);
    }
}
