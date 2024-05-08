<?php

namespace App;

class Room
{

    private $map;
    private $width;
    private $length;
    private $playerPostion;

    public function __construct($width = 10, $length = 10)
    {
        if(!$this->map) {
            $this->width = $width;
            $this->length = $length;
            $this->setupRoom();
        }
    }

    public function setupRoom()
    {
        $this->generateMap();
        $this->addWalls();
        $this->addDoor();
        $this->addPlayer();
    }

    public function generateMap(): void
    {
        $test = array_fill(0, $this->width, " ");
        $this->map = array_fill(0, $this->length, $test);
    }

    public function addWalls()
    {
        $this->map[0] = array_fill(0, $this->length, "#");
        $this->map[$this->width - 1] = array_fill(0, $this->length, "#");
        foreach ($this->map as &$column) {
            $column[0] = $column[9] = "#";
        }
    }

    public function addDoor()
    {
        $this->map[2][9] = "|";
    }

    public function addPlayer(): void
    {
        $this->map[1][1] = '@';
        $this->playerPostion = [1, 1];
    }

    public function getMap(): array
    {
        return $this->map;
    }

    public function getStringifiedMap(): string
    {
        $stringifiedMap = "";
        foreach ($this->map as $column) {
            foreach ($column as $cell) {
                $stringifiedMap .= $cell;
            }
            $stringifiedMap .= "\n";
        }
        return $stringifiedMap;
    }

    public function setPlayerPosition(int $column, int $row)
    {
        $this->map[$this->playerPostion[0]][$this->playerPostion[1]] = ' ';
        $this->playerPostion = [$column, $row];
        $this->map[$column][$row] = '@';
        //file_put_contents("map.txt", json_encode($this->map));
    }
}