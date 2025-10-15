<?php

namespace App;

class Room
{
    private int $width;
    private int $height;
    private array $layout;

    public function __construct(int $width = 3, int $height = 3, ?Position $doorPosition = null)
    {
        $this->width = $width;
        $this->height = $height;

        $this->layout = $this->setupRoomLayout();
        $this->loadFromDisk();

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
                } elseif ($column === 0 || $column === $this->width - 1) {
                    $layout[$row][$column] = '#';
                } else {
                    $layout[$row][$column] = ' ';
                }
            }
        }
        return $layout;
    }

    private function insertDoor(Position $doorPosition): void
    {
        $this->layout[$doorPosition->yPos][$doorPosition->xPos] = "|";
        $this->saveLayoutToDisk();
    }

    public function setNewPlayerPosition(Position $playerPosition): void
    {
        $this->removePlayerPosition();
        $this->layout[$playerPosition->yPos][$playerPosition->xPos] = "@";
        $this->saveLayoutToDisk();
    }




    public function loadFromDisk(): void
    {
        if (file_exists(base_path('room.txt'))) {
            $serializedRoom = json_decode(file_get_contents(base_path('room.txt')), true);

            if ($serializedRoom) {
                $this->setLayoutFromSerializedRoom($serializedRoom);
            } else {
                $this->saveLayoutToDisk();
            }
        }
    }

    private function setLayoutFromSerializedRoom(string $serializedRoom): void
    {
        $roomRows = explode("\n", $serializedRoom);
        while(end($roomRows) === ''){
            array_pop($roomRows);
        }

        foreach ($roomRows as $rowIndex => $row)
        {
            $columns = str_split($row);
            $this->layout[$rowIndex] = $columns;
        }
    }

    private function saveLayoutToDisk(): void
    {
        $serializedRoom = $this->serialize();
        $json = json_encode($serializedRoom, JSON_UNESCAPED_UNICODE);
        file_put_contents(base_path('room.txt'), $json);
    }

    private function removePlayerPosition(): void
    {
        foreach ($this->layout as $y => $row) {
            $x = array_search('@', $row, true);
            if ($x !== false) {
                $this->layout[$y][$x] = ' ';
                break;
            }
        }
    }
}
