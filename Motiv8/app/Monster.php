<?php
namespace App;

class Monster
{
    private $id;
    private $type;
    private $health;
    private $maxHealth;
    private $damage;
    private $critRate;
    private $skin;
    private $aggressive;
    private $row;
    private $col;
    private $lastCombatTime;

    public function __construct($id, $type, $row, $col, $config)
    {
        $this->id = $id;
        $this->type = $type;
        $this->row = $row;
        $this->col = $col;
        
        // Config can be the full config array or the specific monster config
        $monsterConfig = isset($config[$type]) ? $config[$type] : $config;
        
        $this->maxHealth = $monsterConfig['hp'] ?? 10;
        $this->health = $this->maxHealth;
        $this->damage = $monsterConfig['damage'] ?? 5;
        $this->critRate = $monsterConfig['critRate'] ?? 0.0;
        $this->skin = $monsterConfig['skin'] ?? 'M';
        $this->aggressive = $monsterConfig['aggressive'] ?? false;
        $this->lastCombatTime = null;
    }

    public function getId()
    {
        return $this->id;
    }

    public function getType()
    {
        return $this->type;
    }

    public function getHealth()
    {
        return $this->health;
    }

    public function getMaxHealth()
    {
        return $this->maxHealth;
    }

    public function setHealth($health)
    {
        $this->health = max(0, min($health, $this->maxHealth));
    }

    public function takeDamage($damage)
    {
        $this->health = max(0, $this->health - $damage);
    }

    public function isAlive()
    {
        return $this->health > 0;
    }

    public function getDamage()
    {
        return $this->damage;
    }

    public function getCritRate()
    {
        return $this->critRate;
    }

    public function getSkin()
    {
        return $this->skin;
    }

    public function isAggressive()
    {
        return $this->aggressive;
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

    public function attack()
    {
        $isCrit = (mt_rand() / mt_getrandmax()) < $this->critRate;
        return $isCrit ? $this->damage * 2 : $this->damage;
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

    public function toArray()
    {
        return [
            'id' => $this->id,
            'type' => $this->type,
            'health' => $this->health,
            'maxHealth' => $this->maxHealth,
            'damage' => $this->damage,
            'critRate' => $this->critRate,
            'skin' => $this->skin,
            'aggressive' => $this->aggressive,
            'row' => $this->row,
            'col' => $this->col,
            'lastCombatTime' => $this->lastCombatTime,
        ];
    }

    public static function fromArray($data, $monstersConfig)
    {
        $type = $data['type'];
        $config = $monstersConfig;
        
        $monster = new self(
            $data['id'],
            $type,
            $data['row'],
            $data['col'],
            $config
        );
        
        $monster->setHealth($data['health']);
        $monster->setLastCombatTime($data['lastCombatTime'] ?? null);
        
        return $monster;
    }
}
