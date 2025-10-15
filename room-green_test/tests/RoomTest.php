<?php

namespace Tests;

use App\Position;
use App\Room;
use PHPUnit\Framework\TestCase;


class RoomTest extends TestCase
{

    /** @test */
    public function should_serialize_a_3x3_room_with_no_door()
    {
        $room = new Room(3, 3);

        $serializedRoom = $room->serialize();

        $expectedRoom = "###\n# #\n###\n";
        $this->assertSame($expectedRoom, $serializedRoom);
    }

    /** @test */
    public function should_serialize_a_4x4_room_with_no_door()
    {
        $room = new Room(4, 4);

        $serializedRoom = $room->serialize();

        $expectedRoom = "####\n#  #\n#  #\n####\n";
        $this->assertSame($expectedRoom, $serializedRoom);
    }

    /** @test */
    public function should_serialize_a_3x3_room_with_a_door()
    {
        $doorPosition = new Position(1, 1);
        $room = new Room(3, 3, $doorPosition);

        $serializedRoom = $room->serialize();

        $expectedRoom = "###\n#|#\n###\n";
        $this->assertSame($expectedRoom, $serializedRoom);
    }
}
