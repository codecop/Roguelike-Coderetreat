<?php

namespace App\Game;

enum RoomTile: string
{
    case WALL = '#';
    case DOOR = '|';
    case FLOOR = ' ';
    case PLAYER = '@';
    case BOX = 'B';

    public function isCharacter(): bool
    {
        return match ($this) {
            RoomTile::WALL, RoomTile::DOOR, RoomTile::FLOOR => false,
            RoomTile::PLAYER, RoomTile::BOX => true,
        };
    }


}
