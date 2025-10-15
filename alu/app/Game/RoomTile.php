<?php

namespace App\Game;

enum RoomTile: string
{
    case WALL = '#';
    case DOOR = '|';
    case FLOOR = ' ';

    case PLAYER = '@';
}
