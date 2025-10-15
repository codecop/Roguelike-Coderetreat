<?php

namespace App\Game;

class Room
{

    private array $tileGrid;
    private array $characterGrid;

    public function __construct(array $grid, array $characterGrid = [])
    {
        $this->tileGrid = $grid;
        $this->characterGrid = $characterGrid;
    }

    /**
     * create a room from a string
     */
    public static function create(string $roomString): Room
    {
        $roomString = str_replace("\r\n", "\n", $roomString);
        $floorGrid = [];
        $characterGrid = [];
        $rows = explode("\n", $roomString);
        foreach ($rows as $x => $row) {
            for ($y = 0; $y < strlen($row); $y++) {
                $char = $row[$y];
                $enum = RoomTile::tryFrom($char);
                if ($enum === null) {
                    throw new \InvalidArgumentException("was das für char hä $char");
                }
                if ($enum->isCharacter()) {
                    $characterGrid[$x][$y] = $enum;
                    $floorGrid[$x][$y] = RoomTile::FLOOR;
                } else {
                    $floorGrid[$x][$y] = $enum;
                    $characterGrid[$x][$y] = null;
                }
            }
        }

        return new Room($floorGrid, $characterGrid);
    }

    public function enterPlayer(): void
    {
        [$x, $y] = $this->findTile(RoomTile::DOOR);
        $this->characterGrid[$x][$y] = RoomTile::PLAYER;
    }

    public function getTileAt(int $y, int $x): RoomTile
    {
        $character = $this->characterGrid[$x][$y] ?? null;
        if ($character) {
            return $character;
        }
        return $this->tileGrid[$x][$y];
    }

    public function render(): string
    {
        $output = "";
        foreach ($this->tileGrid as $x => $row) {
            foreach ($row as $y => $tile) {
                $character = $this->characterGrid[$x][$y] ?? null;
                $output .= $character ? $character->value : $tile->value;
            }
            $output .= "\n";
        }
        return rtrim($output, "\n");
    }

    /**
     * @return RoomTile[][]
     */
    public function getTileGrid(): array
    {
        return $this->tileGrid;
    }

    public function findTile(RoomTile $tileToSearch): ?array
    {
        $grid = $tileToSearch->isCharacter() ? $this->characterGrid : $this->tileGrid;
        foreach ($grid as $x => $row) {
            foreach ($row as $y => $tile) {
                if ($tile === $tileToSearch) {
                    return [$x, $y];
                }
            }
        }
        return null;
    }

    public function setPlayerPosition(int $x, int $y): void
    {
        if ($xy = $this->findTile(RoomTile::PLAYER)) {
            $this->removeCharacter($xy[0], $xy[1]);
        }
        $this->characterGrid[$x][$y] = RoomTile::PLAYER;
    }

    private function removeCharacter(int $x, int $y): void
    {
        $this->characterGrid[$x][$y] = null;
    }

}