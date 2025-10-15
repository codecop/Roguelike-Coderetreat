<?php

namespace App;

use Illuminate\Http\Request;
use Laravel\Lumen\Routing\Controller;

class AirwavesController extends Controller {

    public function get(Request $request) {
        $room = new Room(15, 15,1);
        $data = array(
            "layout" => $room->getGrid()
        );
        $content = json_encode($data);

        return response($content, 200)
            ->header('Content-Type', 'application/json');
    }

}