<?php

namespace Tests;

use App\Position;
use App\Room;
use PHPUnit\Framework\TestCase;


class RoomTest extends TestCase
{
    private static string $originalLayout;

    public static function setUpBeforeClass(): void
    {
        if (file_exists('./room.txt')) {
            self::$originalLayout = file_get_contents('./room.txt');
        } else {
            self::$originalLayout = '';
        }
    }

    public static function tearDownAfterClass(): void
    {
        if (self::$originalLayout !== '') {
            file_put_contents('./room.txt', self::$originalLayout);
        } else {
            @unlink('./room.txt');
        }
    }

    protected function setUp(): void
    {
        $file = './room.txt';

        if (file_exists($file)) {
            file_put_contents($file, '');
        } else {
            touch($file);
        }
    }

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

    /** @test */
    public function should_set_player_position_on_a_3x3_room()
    {
        $room = new Room(3, 3);
        $playerPosition = new Position(1, 1);

        $room->setNewPlayerPosition($playerPosition);

        $serializedRoom = $room->serialize();
        $expectedRoom = "###\n#@#\n###\n";
        $this->assertSame($expectedRoom, $serializedRoom);
    }

    /** @test */
    public function should_load_layout_from_disk()
    {
        $room = new Room();

        $room->loadFromDisk();
        $serializedRoom = $room->serialize();

        $expectedRoom = "###\n# #\n###\n";
        $this->assertSame($expectedRoom, $serializedRoom);
    }

}
