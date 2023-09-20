<?php

namespace App;

class Layout
{

    public function __construct()
    {

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

    public function addDoor($position,$grid): string
    {
        return substr_replace($grid, "|", $position-1, 1);
    }

    public function setInMultiArray($grid): array
    {
        $result = [];
        $grid = str_split(str_replace("\n","",$grid), 15);
        foreach ($grid as $key => $value) {
            $result[] = str_split($value);
        }
        return $result;
    }
}
