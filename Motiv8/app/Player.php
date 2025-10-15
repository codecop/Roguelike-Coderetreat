<?php
namespace App;

class Player
{
    private $id;
    private $health;
    private $row;
    private $col;
    private $skin;
    private $experience;
    private $level;

    public function __construct($id, $row = 1, $col = 1, $health = 50, $skin = '@', $experience = 0, $level = 1)
    {
        $this->id = $id;
        $this->health = $health;
        $this->row = $row;
        $this->col = $col;
        $this->skin = $skin;
        $this->experience = $experience;
        $this->level = $level;
    }

    public function getId()
    {
        return $this->id;
    }

    public function getHealth()
    {
        return $this->health;
    }

    public function setHealth($health)
    {
        $this->health = max(0, $health);
    }

    public function takeDamage($damage)
    {
        $this->health = max(0, $this->health - $damage);
    }

    public function isAlive()
    {
        return $this->health > 0;
    }

    public function getRow()
    {
        return $this->row;
    }

    public function getCol()
    {
        return $this->col;
    }

    public function setPosition($row, $col)
    {
        $this->row = $row;
        $this->col = $col;
    }

    public function getSkin()
    {
        return $this->skin;
    }

    public function getExperience()
    {
        return $this->experience;
    }

    public function getLevel()
    {
        return $this->level;
    }

    public function addExperience($xp)
    {
        $this->experience += $xp;
        $this->checkLevelUp();
    }

    public function getExperienceToNextLevel()
    {
        // First level requires 10 XP
        $requiredXp = 10;
        
        // Each subsequent level requires 50% more than the previous
        for ($i = 2; $i <= $this->level; $i++) {
            $requiredXp = (int) floor($requiredXp * 1.5);
        }
        
        return $requiredXp;
    }

    private function checkLevelUp()
    {
        while ($this->experience >= $this->getExperienceToNextLevel()) {
            $this->experience -= $this->getExperienceToNextLevel();
            $this->level++;
        }
    }

    public function toArray()
    {
        return [
            'id' => $this->id,
            'health' => $this->health,
            'row' => $this->row,
            'col' => $this->col,
            'skin' => $this->skin,
            'experience' => $this->experience,
            'level' => $this->level,
        ];
    }

    public static function fromArray($data)
    {
        $player = new self(
            $data['id'],
            $data['row'] ?? 1,
            $data['col'] ?? 1,
            $data['health'] ?? 50,
            $data['skin'] ?? '@',
            $data['experience'] ?? 0,
            $data['level'] ?? 1
        );
        return $player;
    }
}
