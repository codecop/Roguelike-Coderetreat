<?php
use PHPUnit\Framework\TestCase;
require_once __DIR__ . '/../app/RoomLayout.php';

class RoomLayoutTest extends TestCase
{
    public function testRoomHasWallsAroundFreeSpace()
    {
        $room = new RoomLayout();
        $layout = $room->serialize();
        $rows = explode("\n", trim($layout));
        $this->assertGreaterThan(0, count($rows));
        $width = strlen($rows[0]);
        foreach ($rows as $i => $row) {
            if ($i === 0 || $i === count($rows) - 1) {
                $this->assertMatchesRegularExpression('/^#+$/', $row);
            } else {
                $this->assertTrue($row[0] === '#' && $row[$width-1] === '#');
            }
        }
    }

    public function testRoomMaxSizeIs15x15()
    {
        $room = new RoomLayout();
        $layout = $room->serialize();
        $rows = explode("\n", trim($layout));
        $this->assertLessThanOrEqual(15, count($rows));
        foreach ($rows as $row) {
            $this->assertLessThanOrEqual(15, strlen($row));
        }
    }

    public function testRoomHasAtLeastOneDoor()
    {
        $room = new RoomLayout();
        $layout = $room->serialize();
        $this->assertStringContainsString('|', $layout);
    }

    public function testSerializationFormat()
    {
        $room = new RoomLayout();
        $layout = $room->serialize();
        $this->assertMatchesRegularExpression('/^[#| ]+$/m', $layout);
    }
}
