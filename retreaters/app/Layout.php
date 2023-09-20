<?php

namespace App;

class Layout
{

    public $doorPosition;
    public $grid;
    public function __construct($doorPosition = 5)
    {
        $this->doorPosition = $doorPosition;
        $this->grid = $this->setupGrid();
    }

    public function createLayout(): string
    {
        $layout = "";
        $first = "#";
        $last = "#\n";
        $blank = "             ";
        $hashtag = "#############";
        $layout = $first . $hashtag . $last;
        for ($y = 0; $y < 13; $y++) {
            $layout .= $first . $blank . $last;
        }
        return $layout .= $first . $hashtag . $last;
    }

    public function addDoor($position, $grid): string
    {
        return substr_replace($grid, "|", $position - 1, 1);
    }

    public function setInMultiArray($grid): array
    {
        $result = [];

        $grid = str_split(str_replace("\n", "", $grid), 15);
        foreach ($grid as $key => $value) {
            $result[] = str_split($value);
        }
        return $result;
    }

    public function setNewPosition($x, $y)
    {
        $this->grid[$x][$y] = '@';
    }

    private function setupGrid()
    {
        $grid = $this->createLayout();
        $grid = $this->addDoor($this->doorPosition, $grid);
        return $this->setInMultiArray($grid);
    }
}
