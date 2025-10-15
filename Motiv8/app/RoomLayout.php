<?php
class RoomLayout
{
    private $rows;
    private $cols;
    private $playerRow;
    private $playerCol;
    private $prevPlayerRow;
    private $prevPlayerCol;

    public function __construct($rows = 7, $cols = 7, $playerRow = 3, $playerCol = 3)
    {
        $this->rows = $rows;
        $this->cols = $cols;
        $this->playerRow = $playerRow;
        $this->playerCol = $playerCol;
    }

    public function setNewPosition($row, $col)
    {
        if ($this->isWallOrDoor($row, $col)) {
            return;
        }
        $this->prevPlayerRow = $this->playerRow;
        $this->prevPlayerCol = $this->playerCol;
        $this->playerRow = $row;
        $this->playerCol = $col;
    }

    private function isWallOrDoor($row, $col)
    {
        if ($row === 0 || $row === $this->rows - 1 || $col === 0 || $col === $this->cols - 1) {
            return true; // wall
        }
        if ($row === 3 && $col === 3) {
            return true; // door
        }
        return false;
    }

    public function serialize(): string
    {
        $layout = [];
        for ($r = 0; $r < $this->rows; $r++) {
            $row = '';
            for ($c = 0; $c < $this->cols; $c++) {
                if ($r === 0 || $r === $this->rows - 1 || $c === 0 || $c === $this->cols - 1) {
                    $row .= '#';
                } elseif ($r === 3 && $c === 3) {
                    $row .= '|'; // hardcoded door
                } elseif ($r === $this->playerRow && $c === $this->playerCol) {
                    if (!$this->isWallOrDoor($r, $c)) {
                        $row .= '@';
                    } else {
                        $row .= ($r === 3 && $c === 3) ? '|' : '#';
                    }
                } elseif (isset($this->prevPlayerRow) && isset($this->prevPlayerCol) && $r === $this->prevPlayerRow && $c === $this->prevPlayerCol) {
                    $row .= ' ';
                } else {
                    $row .= ' ';
                }
            }
            $layout[] = $row;
        }
        return implode("\n", $layout) . "\n";
    }
}
