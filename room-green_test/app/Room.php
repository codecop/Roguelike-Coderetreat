<?php

namespace App;

class Room
{
    private int $width;
    private int $height;

    public function __construct($width, $height)
    {
        $this->width = $width;
        $this->height = $height;
    }

    public function serialize()
    {
        return "###\n# #\n###\n";
    }
}
