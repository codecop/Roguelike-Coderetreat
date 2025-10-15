<?php

namespace App;

use App\Database\RoomDatabase;
use App\Game\RoomParser;
use Illuminate\Http\Request;
use Laravel\Lumen\Routing\Controller;

class RoomController extends Controller
{

    public function get(Request $request, RoomDatabase $roomDatabase)
    {
        $room = $roomDatabase->getRoom(RoomDatabase::ALU_ROOM_DEFAULT);

        $content = json_encode([
            'layout' => $room->render(),
            'description' => $room->getDescription()
        ]);

        return response($content, 200)
            ->header('Content-Type', 'application/json');
    }

    public function post(Request $request, RoomDatabase $roomDatabase)
    {
        $room = $roomDatabase->getRoom(RoomDatabase::ALU_ROOM_DEFAULT);

        $playerX = (int)$request->input('row');
        $playerY = (int)$request->input('column');

        $room->setPlayerPosition($playerX, $playerY);

        $roomDatabase->putRoom(RoomDatabase::ALU_ROOM_DEFAULT, $room);

        $message = '';
        if (mt_rand(0, 10) > 8) {
            $message = 'The floor creaks';
        }

        return response([
            'message' => $message,
        ], 201)
            ->header('Content-Type', 'application/json');
    }
}
