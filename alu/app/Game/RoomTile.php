<?php

namespace App\Game;

enum RoomTile: string
{
    case WALL = '#';
    case DOOR = '|';
    case FLOOR = ' ';
    case PLAYER = '@';
    case BOX = 'B';
    case BOX_GOAL = 'O';

    public function isCharacter(): bool
    {
        return match ($this) {
            RoomTile::WALL, RoomTile::DOOR, RoomTile::FLOOR, RoomTile::BOX_GOAL => false,
            RoomTile::PLAYER, RoomTile::BOX => true,
        };
    }


}
