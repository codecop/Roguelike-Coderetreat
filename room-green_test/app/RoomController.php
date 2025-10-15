<?php

namespace App;

use Illuminate\Http\Request;
use Laravel\Lumen\Routing\Controller;

class RoomController extends Controller
{
    public function get()
    {
        $doorPosition = new Position(1, 1);
        $room = new Room(3, 3, $doorPosition);

        $data = array(
            "layout" => $room->serialize()
        );
        $content = json_encode($data);

        return response($content, 200)
            ->header('Content-Type', 'application/json');
    }

    public function walk()
    {

    }
}
