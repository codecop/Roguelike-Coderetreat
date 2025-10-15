<?php
use PHPUnit\Framework\TestCase;
require_once __DIR__ . '/../app/Monster.php';

use App\Monster;

class MonsterTest extends TestCase
{
    private $monstersConfig;

    protected function setUp(): void
    {
        $this->monstersConfig = [
            'goblin' => [
                'hp' => 20,
                'damage' => 5,
                'critRate' => 0.1,
                'skin' => 'G',
                'aggressive' => true
            ],
            'slime' => [
                'hp' => 10,
                'damage' => 3,
                'critRate' => 0.05,
                'skin' => 's',
                'aggressive' => false
            ]
        ];
    }

    public function testMonsterCreation()
    {
        $monster = new Monster(1, 'goblin', 5, 5, $this->monstersConfig['goblin']);
        
        $this->assertEquals(1, $monster->getId());
        $this->assertEquals('goblin', $monster->getType());
        $this->assertEquals(20, $monster->getHealth());
        $this->assertEquals(5, $monster->getDamage());
        $this->assertEquals(0.1, $monster->getCritRate());
        $this->assertEquals('G', $monster->getSkin());
        $this->assertTrue($monster->isAggressive());
        $this->assertEquals(5, $monster->getRow());
        $this->assertEquals(5, $monster->getCol());
    }

    public function testMonsterTakesDamage()
    {
        $monster = new Monster(1, 'goblin', 5, 5, $this->monstersConfig['goblin']);
        $monster->takeDamage(10);
        
        $this->assertEquals(10, $monster->getHealth());
        $this->assertTrue($monster->isAlive());
    }

    public function testMonsterDiesWhenHealthReachesZero()
    {
        $monster = new Monster(1, 'goblin', 5, 5, $this->monstersConfig['goblin']);
        $monster->takeDamage(30);
        
        $this->assertEquals(0, $monster->getHealth());
        $this->assertFalse($monster->isAlive());
    }

    public function testMonsterAttackReturnsDamage()
    {
        $monster = new Monster(1, 'goblin', 5, 5, $this->monstersConfig['goblin']);
        $damage = $monster->attack();
        
        // Damage should be either normal (5) or crit (10)
        $this->assertContains($damage, [5, 10]);
    }

    public function testNonAggressiveMonster()
    {
        $monster = new Monster(1, 'slime', 3, 3, $this->monstersConfig['slime']);
        
        $this->assertFalse($monster->isAggressive());
        $this->assertEquals('s', $monster->getSkin());
    }

    public function testMonsterPositionCanBeUpdated()
    {
        $monster = new Monster(1, 'goblin', 5, 5, $this->monstersConfig['goblin']);
        $monster->setPosition(7, 8);
        
        $this->assertEquals(7, $monster->getRow());
        $this->assertEquals(8, $monster->getCol());
    }

    public function testMonsterToArray()
    {
        $monster = new Monster(1, 'goblin', 5, 5, $this->monstersConfig['goblin']);
        $array = $monster->toArray();
        
        $this->assertIsArray($array);
        $this->assertEquals(1, $array['id']);
        $this->assertEquals('goblin', $array['type']);
        $this->assertEquals(20, $array['health']);
        $this->assertEquals(5, $array['damage']);
        $this->assertEquals('G', $array['skin']);
        $this->assertTrue($array['aggressive']);
    }

    public function testMonsterFromArray()
    {
        $data = [
            'id' => 2,
            'type' => 'slime',
            'health' => 8,
            'damage' => 3,
            'critRate' => 0.05,
            'skin' => 's',
            'aggressive' => false,
            'row' => 4,
            'col' => 6
        ];
        
        $monster = Monster::fromArray($data, $this->monstersConfig);
        
        $this->assertEquals(2, $monster->getId());
        $this->assertEquals('slime', $monster->getType());
        $this->assertEquals(8, $monster->getHealth());
        $this->assertEquals(4, $monster->getRow());
        $this->assertEquals(6, $monster->getCol());
    }
}
