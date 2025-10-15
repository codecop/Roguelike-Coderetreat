<?php
use PHPUnit\Framework\TestCase;
require_once __DIR__ . '/../app/Player.php';

use App\Player;

class PlayerTest extends TestCase
{
    public function testPlayerCreationWithDefaults()
    {
        $player = new Player(1);
        $this->assertEquals(1, $player->getId());
        $this->assertEquals(50, $player->getHealth());
        $this->assertEquals(1, $player->getRow());
        $this->assertEquals(1, $player->getCol());
        $this->assertEquals('@', $player->getSkin());
        $this->assertTrue($player->isAlive());
    }

    public function testPlayerCreationWithCustomValues()
    {
        $player = new Player(2, 5, 7, 100, 'P');
        $this->assertEquals(2, $player->getId());
        $this->assertEquals(100, $player->getHealth());
        $this->assertEquals(5, $player->getRow());
        $this->assertEquals(7, $player->getCol());
        $this->assertEquals('P', $player->getSkin());
    }

    public function testPlayerTakesDamage()
    {
        $player = new Player(1);
        $player->takeDamage(20);
        $this->assertEquals(30, $player->getHealth());
        $this->assertTrue($player->isAlive());
    }

    public function testPlayerDiesWhenHealthReachesZero()
    {
        $player = new Player(1);
        $player->takeDamage(60);
        $this->assertEquals(0, $player->getHealth());
        $this->assertFalse($player->isAlive());
    }

    public function testPlayerCannotHaveNegativeHealth()
    {
        $player = new Player(1);
        $player->takeDamage(100);
        $this->assertEquals(0, $player->getHealth());
    }

    public function testPlayerPositionCanBeUpdated()
    {
        $player = new Player(1, 1, 1);
        $player->setPosition(5, 5);
        $this->assertEquals(5, $player->getRow());
        $this->assertEquals(5, $player->getCol());
    }

    public function testPlayerToArray()
    {
        $player = new Player(1, 3, 4, 50, '@');
        $array = $player->toArray();
        
        $this->assertIsArray($array);
        $this->assertEquals(1, $array['id']);
        $this->assertEquals(50, $array['health']);
        $this->assertEquals(3, $array['row']);
        $this->assertEquals(4, $array['col']);
        $this->assertEquals('@', $array['skin']);
    }

    public function testPlayerFromArray()
    {
        $data = [
            'id' => 2,
            'health' => 75,
            'row' => 6,
            'col' => 8,
            'skin' => 'X'
        ];
        
        $player = Player::fromArray($data);
        
        $this->assertEquals(2, $player->getId());
        $this->assertEquals(75, $player->getHealth());
        $this->assertEquals(6, $player->getRow());
        $this->assertEquals(8, $player->getCol());
        $this->assertEquals('X', $player->getSkin());
    }
}
