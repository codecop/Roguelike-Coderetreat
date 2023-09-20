<?php

namespace App;

use Illuminate\Http\Request;
use Laravel\Lumen\Routing\Controller;

class ThreeController extends Controller {

    private $room;

    public function __construct()
    {
        $this->room = new Room(10,10);
    }

    public function get(Request $request) {

        $data = array(
            "layout" => $this->room->getStringifiedMap()
        );
        $content = json_encode($data);

        return response($content, 200)
               ->header('Content-Type', 'application/json');
    }

    public function post(Request $request) {
        if ($request->exists('row') && $request->exists('column')) {
            $newRow = $request->input('row');
            $newColumn = $request->input('column');
            $this->room->setPlayerPosition($newRow, $newColumn);
            return response('', 201);
        }
        return response('', 400);
    }
}
