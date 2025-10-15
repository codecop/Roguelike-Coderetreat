<?php

namespace App;

class Room
{
    private int $width;
    private int $height;
    private array $layout;

    public function __construct(int $width, int $height, ?Position $doorPosition = null)
    {
        $this->width = $width;
        $this->height = $height;

        $this->layout = $this->setupRoomLayout();

        if ($doorPosition) {
            $this->insertDoor($doorPosition);
        }
    }

    public function serialize(): string
    {
        $serializedRoom = "";

        foreach ($this->layout as $row) {
            $serializedRoom .= implode('', $row) . PHP_EOL;
        }
        return $serializedRoom;
    }

    private function setupRoomLayout(): array
    {
        $layout = [];
        for ($row = 0; $row < $this->height; $row++) {
            for ($column = 0; $column < $this->width; $column++) {
                if ($row === 0 || $row === $this->height - 1) {
                    $layout[$row][$column] = '#';
                }
                elseif ($column === 0 || $column === $this->width - 1) {
                    $layout[$row][$column] = '#';
                }
                else {
                    $layout[$row][$column] = ' ';
                }
            }
        }
        return $layout;
    }

    private function insertDoor(Position $doorPosition): void
    {
        $this->layout[$doorPosition->yPos][$doorPosition->xPos] = "|";
    }
}
