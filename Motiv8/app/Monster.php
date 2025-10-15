<?php
namespace App;

class Monster
{
    private $id;
    private $type;
    private $health;
    private $damage;
    private $critRate;
    private $skin;
    private $aggressive;
    private $row;
    private $col;

    public function __construct($id, $type, $row, $col, $config)
    {
        $this->id = $id;
        $this->type = $type;
        $this->row = $row;
        $this->col = $col;
        $this->health = $config['hp'] ?? 10;
        $this->damage = $config['damage'] ?? 5;
        $this->critRate = $config['critRate'] ?? 0.0;
        $this->skin = $config['skin'] ?? 'M';
        $this->aggressive = $config['aggressive'] ?? false;
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

    public function toArray()
    {
        return [
            'id' => $this->id,
            'type' => $this->type,
            'health' => $this->health,
            'damage' => $this->damage,
            'critRate' => $this->critRate,
            'skin' => $this->skin,
            'aggressive' => $this->aggressive,
            'row' => $this->row,
            'col' => $this->col,
        ];
    }

    public static function fromArray($data, $monstersConfig)
    {
        $type = $data['type'];
        $config = $monstersConfig[$type] ?? [];
        
        $monster = new self(
            $data['id'],
            $type,
            $data['row'],
            $data['col'],
            $config
        );
        
        $monster->setHealth($data['health']);
        
        return $monster;
    }
}
