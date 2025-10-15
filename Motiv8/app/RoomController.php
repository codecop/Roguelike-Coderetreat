<?php
namespace App;

use Illuminate\Http\Request;
use Laravel\Lumen\Routing\Controller;
require_once __DIR__ . '/RoomLayout.php';
require_once __DIR__ . '/Player.php';
require_once __DIR__ . '/Monster.php';

class RoomController extends Controller {
    private $room;
    private $stateFile;

    public function __construct()
    {
        $this->stateFile = __DIR__ . '/../storage/room_state.json';
        if (file_exists($this->stateFile)) {
            $this->room = new \RoomLayout(15, 15, [], [], $this->stateFile);
        } else {
            $this->room = new \RoomLayout(15, 15, [], [], $this->stateFile);
        }
    }

    public function createGame(Request $request)
    {
        $this->validate($request, [
            'boardsizeX' => 'required|integer|min:5|max:50',
            'boardsizeY' => 'required|integer|min:5|max:50',
            'playerCount' => 'required|integer|min:1|max:10',
            'difficulty' => 'string|in:easy,medium,hard,hell',
        ]);

        $rows = (int) $request->input('boardsizeY');
        $cols = (int) $request->input('boardsizeX');
        $playerCount = (int) $request->input('playerCount');
        $difficulty = $request->input('difficulty', 'easy');

        // Create players with starting positions spread out
        $players = [];
        for ($i = 0; $i < $playerCount; $i++) {
            $startRow = 1 + ($i * 2);
            $startCol = 1 + ($i * 2);
            
            // Ensure we don't go out of bounds
            if ($startRow >= $rows - 1) {
                $startRow = $rows - 2;
            }
            if ($startCol >= $cols - 1) {
                $startCol = $cols - 2;
            }
            
            $players[] = new Player($i + 1, $startRow, $startCol);
        }

        // Delete old state and create new game
        if (file_exists($this->stateFile)) {
            unlink($this->stateFile);
        }

        $this->room = new \RoomLayout($rows, $cols, $players, [], $this->stateFile, $difficulty);
        
        $playersData = [];
        foreach ($this->room->getPlayers() as $player) {
            $playersData[] = $player->toArray();
        }

        return response()->json([
            'success' => true,
            'message' => 'Game created successfully',
            'boardSize' => ['x' => $cols, 'y' => $rows],
            'players' => $playersData,
            'difficulty' => $difficulty,
            'layout' => $this->room->serialize()
        ], 201);
    }

    public function get(Request $request)
    {
        $layout = $this->room->serialize();
        
        $playersData = [];
        foreach ($this->room->getPlayers() as $player) {
            $playersData[] = $player->toArray();
        }
        
        $monstersData = [];
        foreach ($this->room->getMonsters() as $monster) {
            if ($monster->isAlive()) {
                $monstersData[] = $monster->toArray();
            }
        }
        
        $data = [
            "layout" => $layout,
            "players" => $playersData,
            "monsters" => $monstersData
        ];
        return response()->json($data);
    }

    public function walk(Request $request)
    {
        $row = (int) $request->input('row', 1);
        $col = (int) $request->input('column', 1);
        $playerId = (int) $request->input('playerId', 1);
        
        $moved = $this->room->setNewPosition($row, $col, $playerId);
        
        $layout = $this->room->serialize();
        
        $playersData = [];
        foreach ($this->room->getPlayers() as $player) {
            $playersData[] = $player->toArray();
        }
        
        $monstersData = [];
        foreach ($this->room->getMonsters() as $monster) {
            if ($monster->isAlive()) {
                $monstersData[] = $monster->toArray();
            }
        }
        
        $data = [
            "layout" => $layout,
            "players" => $playersData,
            "monsters" => $monstersData,
            "moved" => $moved
        ];
        return response()->json($data);
    }
}
