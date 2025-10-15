<?php

namespace App;

use Illuminate\Http\Request;
use Laravel\Lumen\Routing\Controller;

class AirwavesController extends Controller {

    private $room;

    public function __construct(Room $room) {
        $this->room = $room;
    }

    public function get(Request $request) {
        $data = array(
            "layout" => $this->room->load()
        );
        $content = json_encode($data);

        return response($content, 200)
            ->header('Content-Type', 'application/json');
    }

    public function post($row, $column) {
        $nextPosition = array('row' => (int)$row, 'col' => (int)$column);
        $this->room->setPlayerPosition($nextPosition);
        return response('', 201);
    }
}