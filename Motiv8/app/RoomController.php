<?php
namespace App;

use Illuminate\Http\Request;
use Laravel\Lumen\Routing\Controller;
require_once __DIR__ . '/RoomLayout.php';

class RoomController extends Controller {
    private $room;
    private $stateFile;

    public function __construct()
    {
        $this->stateFile = __DIR__ . '/../storage/room_state.json';
        if (file_exists($this->stateFile)) {
            $this->room = new \RoomLayout(5, 5, 1, 1, $this->stateFile);
        } else {
            $this->room = new \RoomLayout(5, 5, 1, 1, $this->stateFile);
        }
    }

    public function get(Request $request)
    {
        $layout = $this->room->serialize();
        $data = ["layout" => $layout];
        return response()->json($data);
    }

    public function walk(Request $request)
    {
        $row = (int) $request->input('row', 1);
        $col = (int) $request->input('column', 1);
        $this->room->setNewPosition($row, $col); // this saves the state
        $layout = $this->room->serialize();
        $data = ["layout" => $layout];
        return response()->json($data);
    }
}
