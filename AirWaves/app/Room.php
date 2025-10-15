<?php

namespace App;

class Room {

    private $width;
    private $height;
    private $doors;
    private $grid = [];

    public function __construct($width, $height, $doors) {
        $this->width = $width;
        $this->height = $height;
        $this->doors = $doors;

        for ($i = 0; $i < $this->height; $i++) {
            $this->grid[] = array_fill(0, $this->width, '#');
        }

        $this->fillWithEmptySpaces();
        $this->setRandomDoors($this->doors);
        $this->setPlayerPosition(1,1);
        return $this->grid;
    }

    public function getGrid() {
        $gridRows = array_map(function($row) {
            return implode('', $row);
        }, $this->grid);
        $formattedGrid = implode("\n", $gridRows);
        return $formattedGrid;
    }

    public function setPlayerPosition($row, $col){
        $this->grid[$row][$col] = '@';
        return $this->grid;
    }

    private function fillWithEmptySpaces(){
        for ($i = 1; $i < $this->height-1; $i++) {
            for ($j = 1; $j < $this->width - 1; $j++) {
                $this->grid[$i][$j] = ' ';
            }
        }
    }

    /**
     * @throws \ErrorException
     */
    private function setRandomDoors($doors){
        $corners = 4;
        $maxDoorsAllowed = ($this->width + $this->height) * 2 - $corners;

        if($doors > $maxDoorsAllowed){
            throw new \ErrorException("too many doors");
        }

        $this->grid[1][0] = '|';
    }
}
