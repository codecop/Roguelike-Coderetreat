<?php

namespace App;

/**
 * @property array $map
 */
class Room
{

    private $width;
    private $length;

    public function __construct($width, $length)
    {
        $this->width = $width;
        $this->length = $length;
    }

    public function generateMap(): array
    {
        $test = array_fill(0, $this->width, " ");
        $this->map = array_fill(0, $this->length, $test);

        return $this->map;
    }

    public function addWalls()
    {
        $this->map[0] = array_fill(0, $this->length, "#");
        $this->map[$this->width - 1] = array_fill(0, $this->length, "#");
        foreach ($this->map as &$column) {
            $column[0] = $column[9] = "#";
        }
    }

    public function getMap(): array
    {
        return $this->map;
    }

    public function addDoor()
    {
        $this->map[2][9] = "|";
    }

    public function getStringifiedMap()
    {
        $stringifiedMap = "";
        foreach ($this->map as $column) {
            $stringifiedMap .= "/n";
            foreach ($column as $cell) {
                $stringifiedMap .= $cell;
            }
        }
        return $stringifiedMap;
    }
}