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

    public function enterPlayer(): void
    {
        [$x, $y] = $this->findTile(RoomTile::DOOR);
        $this->characterGrid[$x][$y] = RoomTile::PLAYER;
    }

    public function getTileAt(int $x, int $y): RoomTile
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


    public function interact(RoomTile $tile): string
    {
        [$boxX, $boxY] = $this->findTile($tile);
        [$playerX, $playerY] = $this->findTile(RoomTile::PLAYER);

        if ($boxX > $playerX) {
            $tileBehindBox = $this->getTileAt($boxX + 1 , $boxY);
            if ($tileBehindBox === RoomTile::FLOOR || $tileBehindBox === RoomTile::BOX_GOAL) {
                $this->setPlayerPosition($boxX, $boxY);
                $this->characterGrid[$boxX + 1][$boxY] = RoomTile::BOX;
            }
            return $tileBehindBox === RoomTile::BOX_GOAL ? 'you won' : 'you moved the box';

        }
        if ($boxX < $playerX) {
            $tileBehindBox = $this->getTileAt($boxX - 1, $boxY);
            if ($tileBehindBox === RoomTile::FLOOR || $tileBehindBox === RoomTile::BOX_GOAL) {
                $this->setPlayerPosition($boxX, $boxY);
                $this->characterGrid[$boxX - 1][$boxY] = RoomTile::BOX;
            }
            return $tileBehindBox === RoomTile::BOX_GOAL ? 'you won' : 'you moved the box';
        }
        if ($boxY > $playerY) {
            $tileBehindBox = $this->getTileAt($boxX, $boxY + 1);
            if ($tileBehindBox === RoomTile::FLOOR || $tileBehindBox === RoomTile::BOX_GOAL) {
                $this->setPlayerPosition($boxX, $boxY);
                $this->characterGrid[$boxX][$boxY + 1] = RoomTile::BOX;
            }
            return $tileBehindBox === RoomTile::BOX_GOAL ? 'you won' : 'you moved the box';

        }

        if ($boxY < $playerY) {
            $tileBehindBox = $this->getTileAt($boxX, $boxY - 1);
            if ($tileBehindBox === RoomTile::FLOOR || $tileBehindBox === RoomTile::BOX_GOAL) {
                $this->setPlayerPosition($boxX, $boxY);
                $this->characterGrid[$boxX][$boxY - 1] = RoomTile::BOX;
            }
            return $tileBehindBox === RoomTile::BOX_GOAL ? 'you won' : 'you moved the box';

        }

        return 'nothing happened';

    }

    private function removeCharacter(int $x, int $y): void
    {
        $this->characterGrid[$x][$y] = null;
    }

    public function getDescription()
    {
        return "You are in a room. There is a door to the north.";
    }

}