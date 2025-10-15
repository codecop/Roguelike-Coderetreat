<?php

namespace App\Database;

use App\Game\Room;
use App\Game\RoomParser;

class RoomDatabase
{
    const ALU_ROOM_DEFAULT = 'aluroom';

    public function getRoom(string $name): Room
    {
        if (!file_exists(__DIR__ . "/../../database/$name.txt")) {
            return $this->getRoomDefault($name);
        }
        $roomLayout = file_get_contents(__DIR__ . "/../../database/$name.txt");
        return (new RoomParser)->create($roomLayout);
    }

    public function getRoomDefault(string $name): Room
    {
        $roomLayout = file_get_contents(__DIR__ . "/../../rooms/$name.txt");
        return (new RoomParser)->create($roomLayout);
    }

    public function putRoom(string $name, Room $room): void
    {
        file_put_contents(__DIR__ . "/../../database/$name.txt", $room->render());
    }

    public function deleteRoom(string $name): void
    {
        if (file_exists(__DIR__ . "/../../database/$name.txt")) {
            unlink(__DIR__ . "/../../database/$name.txt");
        }
    }
}