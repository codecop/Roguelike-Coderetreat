<?php
class RoomLayout
{
    private $rows;
    private $cols;
    private $playerRow;
    private $playerCol;
    private $prevPlayerRow;
    private $prevPlayerCol;
    private $stateFile;

    public function __construct($rows = 15, $cols = 15, $playerRow = 3, $playerCol = 3, $stateFile = null)
    {
        $this->rows = $rows;
        $this->cols = $cols;
        $this->stateFile = $stateFile ?? __DIR__ . '/../storage/room_state.json';
        if (file_exists($this->stateFile)) {
            $this->loadState();
        } else {
            $this->playerRow = $playerRow;
            $this->playerCol = $playerCol;
            $this->prevPlayerRow = null;
            $this->prevPlayerCol = null;
            $this->saveState();
        }
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
        $this->saveState();
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
                if ($r === $this->playerRow && $c === $this->playerCol) {
                    $row .= '@';
                    continue;
                }
                if ($r === 0 || $r === $this->rows - 1 || $c === 0 || $c === $this->cols - 1) {
                    $row .= '#';
                } elseif ($r === 3 && $c === 3) {
                    $row .= '|'; // hardcoded door
                } elseif (
                    isset($this->prevPlayerRow, $this->prevPlayerCol) &&
                    $this->prevPlayerRow !== null && $this->prevPlayerCol !== null &&
                    ($this->playerRow !== $this->prevPlayerRow || $this->playerCol !== $this->prevPlayerCol) &&
                    $r === $this->prevPlayerRow && $c === $this->prevPlayerCol
                ) {
                    $row .= ' ';
                } else {
                    $row .= ' ';
                }
            }
            $layout[] = $row;
        }
        return implode("\n", $layout) . "\n";
    }

    private function saveState()
    {
        $state = [
            'playerRow' => $this->playerRow,
            'playerCol' => $this->playerCol,
            'prevPlayerRow' => $this->prevPlayerRow,
            'prevPlayerCol' => $this->prevPlayerCol
        ];
        file_put_contents($this->stateFile, json_encode($state));
    }

    private function loadState()
    {
        $state = json_decode(file_get_contents($this->stateFile), true);
        $this->playerRow = $state['playerRow'] ?? 3;
        $this->playerCol = $state['playerCol'] ?? 3;
        $this->prevPlayerRow = $state['prevPlayerRow'] ?? null;
        $this->prevPlayerCol = $state['prevPlayerCol'] ?? null;
    }
}
