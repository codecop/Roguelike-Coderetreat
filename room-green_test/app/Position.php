<?php

namespace App;

readonly class Position
{
    public int $xPos;
    public int $yPos;

    public function __construct($xPos, $yPos)
    {
        $this->xPos = $xPos;
        $this->yPos = $yPos;
    }
}
