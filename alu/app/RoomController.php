<?php

namespace App;

use App\Database\RoomDatabase;
use App\Game\Room;
use App\Game\RoomParser;
use Illuminate\Http\Request;
use Laravel\Lumen\Routing\Controller;

class RoomController extends Controller
{

    public function get(Request $request, RoomDatabase $roomDatabase)
    {
        $roomLayout = $roomDatabase->getRoom(RoomDatabase::ALU_ROOM_DEFAULT);
        $room = (new Roomparser)->create($roomLayout);

        $content = json_encode([
            'layout' => $room->render()
        ]);

        return response($content, 200)
            ->header('Content-Type', 'application/json');
    }

    public function post(Request $request, RoomDatabase $roomDatabase)
    {
        $roomLayout = $roomDatabase->getRoom(RoomDatabase::ALU_ROOM_DEFAULT);
        $room = (new Roomparser)->create($roomLayout);

        $playerX = (int)$request->input('row');
        $playerY = (int)$request->input('column');

        $room->setPlayerPosition($playerX, $playerY);

        $roomDatabase->putRoom(RoomDatabase::ALU_ROOM_DEFAULT, $room->render());

        return response('', 201)
            ->header('Content-Type', 'application/json');
    }
}
