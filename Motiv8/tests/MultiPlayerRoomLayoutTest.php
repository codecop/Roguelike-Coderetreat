<?php
use PHPUnit\Framework\TestCase;
require_once __DIR__ . '/../app/RoomLayout.php';
require_once __DIR__ . '/../app/Player.php';
require_once __DIR__ . '/../app/Monster.php';

use App\Player;
use App\Monster;

class MultiPlayerRoomLayoutTest extends TestCase
{
    private $tempStateFile;

    protected function setUp(): void
    {
        $this->tempStateFile = __DIR__ . '/../storage/test_room_state_' . uniqid() . '.json';
    }

    protected function tearDown(): void
    {
        if (file_exists($this->tempStateFile)) {
            unlink($this->tempStateFile);
        }
    }

    public function testRoomWith15x15Size()
    {
        $room = new RoomLayout(15, 15, [], [], $this->tempStateFile);
        $layout = $room->serialize();
        $rows = explode("\n", trim($layout));
        
        $this->assertEquals(15, count($rows));
        $this->assertEquals(15, strlen($rows[0]));
    }

    public function testRoomWithMultiplePlayers()
    {
        $players = [
            new Player(1, 2, 2),
            new Player(2, 4, 4),
            new Player(3, 6, 6)
        ];
        
        $room = new RoomLayout(15, 15, $players, [], $this->tempStateFile);
        $layout = $room->serialize();
        
        // Count player symbols
        $playerCount = substr_count($layout, '@');
        $this->assertEquals(3, $playerCount);
    }

    public function testRoomWithMonsters()
    {
        $monstersConfig = [
            'goblin' => [
                'hp' => 20,
                'damage' => 5,
                'critRate' => 0.1,
                'skin' => 'G',
                'aggressive' => true
            ]
        ];
        
        $monsters = [
            new Monster(1, 'goblin', 5, 5, $monstersConfig['goblin']),
            new Monster(2, 'goblin', 7, 7, $monstersConfig['goblin'])
        ];
        
        $room = new RoomLayout(15, 15, [], $monsters, $this->tempStateFile);
        $layout = $room->serialize();
        
        $monsterCount = substr_count($layout, 'G');
        $this->assertEquals(2, $monsterCount);
    }

    public function testRoomStatePersistence()
    {
        $players = [
            new Player(1, 2, 2, 50),
            new Player(2, 4, 4, 50)
        ];
        
        $room1 = new RoomLayout(15, 15, $players, [], $this->tempStateFile);
        $players1 = $room1->getPlayers();
        
        // Create new instance with same state file
        $room2 = new RoomLayout(15, 15, [], [], $this->tempStateFile);
        $players2 = $room2->getPlayers();
        
        $this->assertCount(2, $players2);
        $this->assertEquals($players1[0]->getId(), $players2[0]->getId());
        $this->assertEquals($players1[0]->getRow(), $players2[0]->getRow());
        $this->assertEquals($players1[0]->getCol(), $players2[0]->getCol());
    }

    public function testPlayerMovementWithMultiplePlayers()
    {
        $players = [
            new Player(1, 2, 2),
            new Player(2, 4, 4)
        ];
        
        $room = new RoomLayout(15, 15, $players, [], $this->tempStateFile);
        $room->setNewPosition(3, 3, 1);
        
        $updatedPlayers = $room->getPlayers();
        $this->assertEquals(3, $updatedPlayers[0]->getRow());
        $this->assertEquals(3, $updatedPlayers[0]->getCol());
        
        // Second player should be unchanged
        $this->assertEquals(4, $updatedPlayers[1]->getRow());
        $this->assertEquals(4, $updatedPlayers[1]->getCol());
    }

    public function testCustomBoardSize()
    {
        $room = new RoomLayout(20, 25, [], [], $this->tempStateFile);
        
        $this->assertEquals(20, $room->getRows());
        $this->assertEquals(25, $room->getCols());
        
        $layout = $room->serialize();
        $rows = explode("\n", trim($layout));
        
        $this->assertEquals(20, count($rows));
        $this->assertEquals(25, strlen($rows[0]));
    }

    public function testDeadMonstersNotShownInLayout()
    {
        $monstersConfig = [
            'goblin' => [
                'hp' => 20,
                'damage' => 5,
                'critRate' => 0.1,
                'skin' => 'G',
                'aggressive' => true
            ]
        ];
        
        $monster = new Monster(1, 'goblin', 5, 5, $monstersConfig['goblin']);
        $monster->takeDamage(30); // Kill the monster
        
        $room = new RoomLayout(15, 15, [], [$monster], $this->tempStateFile);
        $layout = $room->serialize();
        
        $monsterCount = substr_count($layout, 'G');
        $this->assertEquals(0, $monsterCount);
    }
}
