<?php

namespace Tests;

use PHPUnit\Framework\TestCase;
use App\Room;

class GridTest extends TestCase {
    /**
     * @test
     */
    public function shouldReturn3x3GridWhenRoomIsGenerated() {
        $room = new Room(3, 3,1);

        $grid = $room->getGrid();

        $this->assertEquals(
            '###\n|@#\n###',
            $grid
        );
    }

}