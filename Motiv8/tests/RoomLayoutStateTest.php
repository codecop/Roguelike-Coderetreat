<?php
use PHPUnit\Framework\TestCase;
require_once __DIR__ . '/../app/RoomLayout.php';

class RoomLayoutStateTest extends TestCase
{
    private $stateFile;

    protected function setUp(): void
    {
        $this->stateFile = __DIR__ . '/../storage/test_room_state.json';
        if (file_exists($this->stateFile)) {
            unlink($this->stateFile);
        }
    }

    protected function tearDown(): void
    {
        if (file_exists($this->stateFile)) {
            unlink($this->stateFile);
        }
    }

    /** @test */
    public function it_saves_state_after_movement(): void
    {
        $room = new RoomLayout(5, 5, 1, 1, $this->stateFile);
        $room->setNewPosition(2, 2);
        $this->assertFileExists($this->stateFile);
        $state = json_decode(file_get_contents($this->stateFile), true);
        $this->assertSame(2, $state['playerRow']);
        $this->assertSame(2, $state['playerCol']);
    }

    /** @test */
    public function it_loads_state_from_file(): void
    {
        $room = new RoomLayout(5, 5, 1, 1, $this->stateFile);
        $room->setNewPosition(2, 2);
        // Create new instance, should load state from file
        $room2 = new RoomLayout(5, 5, 1, 1, $this->stateFile);
        $layout = $room2->serialize();
        $rows = explode("\n", trim($layout));
        $this->assertSame('@', $rows[2][2]);
    }
}
