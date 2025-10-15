<?php

namespace Tests;

use App\Room;
use PHPUnit\Framework\TestCase;


class RoomTest extends TestCase
{

    /**
     * @test
     */
    public function should_serialize_a_3x3_room_with_no_door()
    {
        $room = new Room(3, 3);

        $serializedRoom = $room->serialize();

        $expectedRoom = "###\n# #\n###\n";
        $this->assertSame($expectedRoom, $serializedRoom);
    }

}
