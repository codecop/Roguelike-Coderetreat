<?php
use PHPUnit\Framework\TestCase;
require_once __DIR__ . '/../app/RoomLayout.php';

final class RoomLayoutPlayerTest extends TestCase
{
    private RoomLayout $room;

    protected function setUp(): void
    {
        parent::setUp();
        $this->room = new RoomLayout(5, 5, 1, 1);
    }

    /** @test */
    public function it_shows_player_start_position(): void
    {
        $layout = $this->room->serialize();
        $rows = explode("\n", trim($layout));
        $this->assertSame('@', $rows[1][1]);
    }

    /** @test */
    public function it_moves_player_to_new_position(): void
    {
        $this->room->setNewPosition(2, 1);
        $layout = $this->room->serialize();
        $rows = explode("\n", trim($layout));
        $this->assertSame(' ', $rows[1][1], 'Previous position should be blank');
        $this->assertSame('@', $rows[2][1], 'New position should show player');
    }

    /** @test */
    public function it_does_not_overwrite_walls_or_doors(): void
    {
        $wallRoom = new RoomLayout(5, 5, 0, 0);
        $layout = $wallRoom->serialize();
        $rows = explode("\n", trim($layout));
        $this->assertSame('#', $rows[0][0], 'Wall remains wall');

        $doorRoom = new RoomLayout(5, 5, 1, 1);
        $doorRoom->setNewPosition(3, 3);
        $layout = $doorRoom->serialize();
        $rows = explode("\n", trim($layout));
        $this->assertSame('|', $rows[3][3], 'Door remains door');
    }

    /** @test */
    public function it_shows_player_symbol_only_once(): void
    {
        $this->room->setNewPosition(2, 2);
        $layout = $this->room->serialize();
        $this->assertSame(1, substr_count($layout, '@'), 'Player symbol should be unique');
    }
}
