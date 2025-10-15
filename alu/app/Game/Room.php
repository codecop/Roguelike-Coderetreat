<?php

namespace App\Game;

class Room
{

    private ?array $grid;

    private bool $playerIsOnDoor = false;


    public function __construct(?array $grid = null)
    {
        $this->grid = $grid ?? null;
    }

    public static function create(string $roomString): Room
    {
        $roomString = str_replace("\r\n", "\n", $roomString);
        $grid = [];
        $rows = explode("\n", $roomString);
        foreach ($rows as $x => $row) {
            for ($y = 0; $y < strlen($row); $y++) {
                $char = $row[$y];
                $enum = RoomTile::tryFrom($char);
                if ($enum === null) {
                    throw new \InvalidArgumentException("was das für char hä $char");
                }
                $grid[$x][$y] = $enum;
            }
        }
        return new Room($grid);
    }

    public function enter(): void
    {
        [$x, $y] = $this->findTile(RoomTile::DOOR);
        $this->grid[$x][$y] = RoomTile::PLAYER;
        $this->playerIsOnDoor = true;
    }

    public function getTileAt(int $x, int $y): RoomTile
    {
        return $this->grid[$x][$y];
    }

    public function render(): string
    {
        $output = "";
        foreach ($this->grid as $row) {
            foreach ($row as $tile) {
                $output .= $tile->value;
            }
            $output .= "\n";
        }
        return rtrim($output, "\n");
    }

    /**
     * @return RoomTile[][]
     */
    public function getGrid(): array
    {
        return [
            [RoomTile::WALL, RoomTile::WALL, RoomTile::WALL],
            [RoomTile::WALL, RoomTile::FLOOR, RoomTile::DOOR],
            [RoomTile::WALL, RoomTile::WALL, RoomTile::WALL],
        ];
    }

    public function findTile(RoomTile $tileToSearch): ?array
    {
        foreach ($this->grid as $x => $row) {
            foreach ($row as $y => $tile) {
                if ($tile === $tileToSearch) {
                    return [$x, $y];
                }
            }
        }
        return null;
    }

    public function setPlayerPosition(int $x, int $y)
    {
        $this->grid[$x][$y] = RoomTile::PLAYER;
    }

}