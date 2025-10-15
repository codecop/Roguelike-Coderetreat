<?php

namespace App\Game;

class RoomParser
{

    private array $characterGrid;
    private array $floorGrid;

    public function create(string $roomString): Room
    {
        $roomString = str_replace("\r\n", "\n", $roomString);
        $this->floorGrid = [];
        $this->characterGrid = [];
        $rows = explode("\n", $roomString);
        foreach ($rows as $x => $row) {
            for ($y = 0; $y < strlen($row); $y++) {
                $this->parseTile($row[$y], $x, $y);
            }
        }

        return new Room($this->floorGrid, $this->characterGrid);
    }

    private function parseTile(string $char, int $x, int $y): void
    {
        $enum = RoomTile::tryFrom($char);
        if ($enum === null) {
            throw new \InvalidArgumentException("was das für char hä $char");
        }
        if ($enum->isCharacter()) {
            $this->characterGrid[$x][$y] = $enum;
            $this->floorGrid[$x][$y] = RoomTile::FLOOR;
        } else {
            $this->floorGrid[$x][$y] = $enum;
            $this->characterGrid[$x][$y] = null;
        }
    }

}