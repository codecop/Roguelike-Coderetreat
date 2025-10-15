<?php

namespace App;

use App\Database\RoomDatabase;
use App\Game\RoomTile;
use Illuminate\Http\Request;
use Laravel\Lumen\Routing\Controller;

class RoomController extends Controller
{

    public function get(Request $request, RoomDatabase $roomDatabase)
    {
        $name = $request->route('name');
        $room = $roomDatabase->getRoom($name);

        $content = json_encode([
            'layout' => $room->render(),
            'description' => $room->getDescription()
        ]);

        return response($content, 200)
            ->header('Content-Type', 'application/json');
    }

    public function post(Request $request, RoomDatabase $roomDatabase)
    {
        $name = $request->route('name');
        $room = $roomDatabase->getRoom($name);

        $playerX = (int)$request->input('row');
        $playerY = (int)$request->input('column');

        $room->setPlayerPosition($playerX, $playerY);

        $roomDatabase->putRoom($name, $room);

        $message = '';
        if (mt_rand(0, 10) > 8) {
            $message = 'The floor creaks';
        }

        return response([
            'message' => $message,
        ], 201)
            ->header('Content-Type', 'application/json');
    }

    public function interact(Request $request, RoomDatabase $roomDatabase)
    {
        $name = $request->route('name');
        $room = $roomDatabase->getRoom($name);

        $entity = $request->input('item');
        $roomTile = RoomTile::tryFrom($entity);

        $message = $room->interact($roomTile);

        $roomDatabase->putRoom($name, $room);

        return response([
            'message' => $message,
        ], 201)
            ->header('Content-Type', 'application/json');
    }
}
