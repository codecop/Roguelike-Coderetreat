<?php
namespace App;

use Illuminate\Http\Request;
use Laravel\Lumen\Routing\Controller;
require_once __DIR__ . '/RoomLayout.php';

class RoomController extends Controller {
    private $room;

    public function __construct()
    {
        // Room name and size can be parameterized if needed
        $this->room = new \RoomLayout(5, 5, 1, 1);
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
        $this->room->setNewPosition($row, $col);
        $layout = $this->room->serialize();
        $data = ["layout" => $layout];
        return response()->json($data);
    }
}
