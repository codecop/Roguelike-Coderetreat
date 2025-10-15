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
    private $damage;
    private $lastCombatTime;

    public function __construct($id, $row = 1, $col = 1, $health = null, $skin = '@', $experience = 0, $level = 1, $damage = null, $lastCombatTime = null)
    {
        $this->id = $id;
        $this->row = $row;
        $this->col = $col;
        $this->skin = $skin;
        $this->experience = $experience;
        $this->level = $level;
        $this->lastCombatTime = $lastCombatTime;
        
        // Calculate max health based on level: 50 base + 5 per level beyond 1
        $maxHealth = 50 + (($level - 1) * 5);
        $this->health = $health ?? $maxHealth;
        
        // Calculate damage based on level: 5 base + 1 per level beyond 1
        $this->damage = $damage ?? (5 + ($level - 1));
    }

    public function getId()
    {
        return $this->id;
    }

    public function getHealth()
    {
        return $this->health;
    }

    public function getMaxHealth()
    {
        return 50 + (($this->level - 1) * 5);
    }

    public function setHealth($health)
    {
        $this->health = max(0, min($health, $this->getMaxHealth()));
    }

    public function getDamage()
    {
        return $this->damage;
    }

    public function getLastCombatTime()
    {
        return $this->lastCombatTime;
    }

    public function setLastCombatTime($time)
    {
        $this->lastCombatTime = $time;
    }

    public function canAttack($currentTime)
    {
        if ($this->lastCombatTime === null) {
            return true;
        }
        return ($currentTime - $this->lastCombatTime) >= 2;
    }

    public function attack()
    {
        return $this->damage;
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
            
            // Increase damage by 1 per level
            $this->damage++;
            
            // Fully heal the player on level up
            $this->health = $this->getMaxHealth();
        }
    }

    public function toArray()
    {
        return [
            'id' => $this->id,
            'health' => $this->health,
            'maxHealth' => $this->getMaxHealth(),
            'row' => $this->row,
            'col' => $this->col,
            'skin' => $this->skin,
            'experience' => $this->experience,
            'level' => $this->level,
            'damage' => $this->damage,
            'lastCombatTime' => $this->lastCombatTime,
        ];
    }

    public static function fromArray($data)
    {
        $player = new self(
            $data['id'],
            $data['row'] ?? 1,
            $data['col'] ?? 1,
            $data['health'] ?? null,
            $data['skin'] ?? '@',
            $data['experience'] ?? 0,
            $data['level'] ?? 1,
            $data['damage'] ?? null,
            $data['lastCombatTime'] ?? null
        );
        return $player;
    }
}
