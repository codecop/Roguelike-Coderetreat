<?php

namespace App\Database;

class RoomDatabase
{
    const ALU_ROOM = 'aluroom';

    public function getRoom(string $name): string
    {
        if (file_exists(__DIR__ . "/../../database/$name.txt")) {
            $roomLayout = file_get_contents(__DIR__ . "/../../database/$name.txt");
        } else {
            $roomLayout = file_get_contents(__DIR__ . "/../aluroom.txt");
        }

        return str_replace("\r\n", "\n", $roomLayout);
    }

    public function putRoom(string $name, string $layout): void
    {
        file_put_contents(__DIR__ . "/../../database/$name.txt", $layout);
    }

    public function deleteRoom(string $name): void
    {
        if (file_exists(__DIR__ . "/../../database/$name.txt")) {
            unlink(__DIR__ . "/../../database/$name.txt");
        }
    }
}