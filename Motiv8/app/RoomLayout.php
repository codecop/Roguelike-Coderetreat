<?php
require_once __DIR__ . '/Player.php';
require_once __DIR__ . '/Monster.php';

use App\Player;
use App\Monster;

class RoomLayout
{
    private $rows;
    private $cols;
    private $players;
    private $monsters;
    private $stateFile;
    private $monstersConfig;
    private $difficulty;

    public function __construct($rows = 15, $cols = 15, $players = [], $monsters = [], $stateFile = null, $difficulty = 'easy')
    {
        $this->rows = $rows;
        $this->cols = $cols;
        $this->stateFile = $stateFile ?? __DIR__ . '/../storage/room_state.json';
        $this->monstersConfig = $this->loadMonstersConfig();
        $this->difficulty = $difficulty;
        $this->monsters = []; // Initialize as empty array
        
        if (file_exists($this->stateFile)) {
            $this->loadState();
        } else {
            // If no players provided, create a default one
            if (empty($players)) {
                $this->players = [new Player(1, 3, 3)];
            } else {
                $this->players = $players;
            }
            
            // If no monsters provided, spawn based on difficulty
            if (empty($monsters)) {
                $this->monsters = $this->spawnMonstersByDifficulty();
            } else {
                $this->monsters = $monsters;
            }
            
            $this->saveState();
        }
    }

    private function loadMonstersConfig()
    {
        $configPath = __DIR__ . '/../storage/monsters_config.json';
        if (file_exists($configPath)) {
            return json_decode(file_get_contents($configPath), true);
        }
        return [];
    }

    private function spawnMonstersByDifficulty()
    {
        $difficultyRanges = [
            'easy' => ['min' => 1, 'max' => 2],
            'medium' => ['min' => 3, 'max' => 4],
            'hard' => ['min' => 5, 'max' => 7],
            'hell' => ['min' => 8, 'max' => 15],
        ];

        $range = $difficultyRanges[$this->difficulty] ?? $difficultyRanges['easy'];
        $monsterCount = rand($range['min'], $range['max']);

        // Filter monsters by difficulty
        $availableMonsters = [];
        foreach ($this->monstersConfig as $type => $config) {
            if (isset($config['difficulties']) && in_array($this->difficulty, $config['difficulties'])) {
                $availableMonsters[] = $type;
            }
        }

        if (empty($availableMonsters)) {
            return [];
        }

        $monsters = [];
        for ($i = 0; $i < $monsterCount; $i++) {
            $type = $availableMonsters[array_rand($availableMonsters)];
            $position = $this->getRandomEmptyPosition();
            
            $monsters[] = new Monster(
                $i + 1,
                $type,
                $position['row'],
                $position['col'],
                $this->monstersConfig
            );
        }

        return $monsters;
    }

    private function getRandomEmptyPosition()
    {
        $maxAttempts = 100;
        $attempts = 0;

        while ($attempts < $maxAttempts) {
            $row = rand(1, $this->rows - 2);
            $col = rand(1, $this->cols - 2);

            // Check if position is not wall/door and not occupied
            if (!$this->isWallOrDoor($row, $col) && !$this->isPositionOccupied($row, $col)) {
                return ['row' => $row, 'col' => $col];
            }

            $attempts++;
        }

        // Fallback position
        return ['row' => $this->rows - 3, 'col' => $this->cols - 3];
    }

    private function isPositionOccupied($row, $col)
    {
        foreach ($this->players as $player) {
            if ($player->getRow() === $row && $player->getCol() === $col) {
                return true;
            }
        }

        foreach ($this->monsters as $monster) {
            if ($monster->getRow() === $row && $monster->getCol() === $col) {
                return true;
            }
        }

        return false;
    }

    public function setNewPosition($row, $col, $playerId = null)
    {
        if ($this->isWallOrDoor($row, $col)) {
            return false;
        }
        
        // Find the player to move
        $player = null;
        if ($playerId === null && count($this->players) > 0) {
            $player = $this->players[0];
        } else {
            foreach ($this->players as $p) {
                if ($p->getId() === $playerId) {
                    $player = $p;
                    break;
                }
            }
        }
        
        if ($player) {
            $player->setPosition($row, $col);
            $this->saveState();
            return true;
        }
        
        return false;
    }

    public function getPlayers()
    {
        return $this->players;
    }

    public function getMonsters()
    {
        return $this->monsters;
    }

    public function getRows()
    {
        return $this->rows;
    }

    public function getCols()
    {
        return $this->cols;
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
                // Check if any player is at this position
                $playerAtPosition = null;
                foreach ($this->players as $player) {
                    if ($player->getRow() === $r && $player->getCol() === $c) {
                        $playerAtPosition = $player;
                        break;
                    }
                }
                
                if ($playerAtPosition) {
                    $row .= $playerAtPosition->getSkin();
                    continue;
                }
                
                // Check if any monster is at this position
                $monsterAtPosition = null;
                foreach ($this->monsters as $monster) {
                    if ($monster->getRow() === $r && $monster->getCol() === $c && $monster->isAlive()) {
                        $monsterAtPosition = $monster;
                        break;
                    }
                }
                
                if ($monsterAtPosition) {
                    $row .= $monsterAtPosition->getSkin();
                    continue;
                }
                
                if ($r === 0 || $r === $this->rows - 1 || $c === 0 || $c === $this->cols - 1) {
                    $row .= '#';
                } elseif ($r === 3 && $c === 3) {
                    $row .= '|'; // hardcoded door
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
        $playersData = [];
        foreach ($this->players as $player) {
            $playersData[] = $player->toArray();
        }
        
        $monstersData = [];
        foreach ($this->monsters as $monster) {
            $monstersData[] = $monster->toArray();
        }
        
        $state = [
            'rows' => $this->rows,
            'cols' => $this->cols,
            'players' => $playersData,
            'monsters' => $monstersData,
            'difficulty' => $this->difficulty,
        ];
        file_put_contents($this->stateFile, json_encode($state, JSON_PRETTY_PRINT));
    }

    private function loadState()
    {
        $state = json_decode(file_get_contents($this->stateFile), true);
        $this->rows = $state['rows'] ?? 15;
        $this->cols = $state['cols'] ?? 15;
        $this->difficulty = $state['difficulty'] ?? 'easy';
        
        $this->players = [];
        if (isset($state['players'])) {
            foreach ($state['players'] as $playerData) {
                $this->players[] = Player::fromArray($playerData);
            }
        }
        
        $this->monsters = [];
        if (isset($state['monsters'])) {
            foreach ($state['monsters'] as $monsterData) {
                $this->monsters[] = Monster::fromArray($monsterData, $this->monstersConfig);
            }
        }
    }
}
