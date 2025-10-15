<?php

use App\Game\Room;
use App\Game\RoomTile;

class RoomTest extends PHPUnit\Framework\TestCase
{

    const DEFAULT_ROOM = <<<EOL
        ##|#
        #  #
        #  #
        ####
        EOL;

    public function test_room_is_rendered_as_string()
    {
        $room = Room::create(self::DEFAULT_ROOM);

        $renderedRoom = $room->render();

        $this->assertIsString($renderedRoom);
    }

    public function test_room_always_has_a_door()
    {
        $room = Room::create(self::DEFAULT_ROOM);

        $renderedRoom = $room->render();

        $this->assertStringContainsString(RoomTile::DOOR->value, $renderedRoom);
    }


    public function test_room_has_always_walls()
    {
        $room = Room::create(self::DEFAULT_ROOM);

        $renderedRoom = $room->render();

        $this->assertStringContainsString(RoomTile::WALL->value, $renderedRoom);
    }

    public function test_room_cannot_be_larger_than_15x15()
    {
        $room = Room::create(self::DEFAULT_ROOM);

        $grid = $room->getTileGrid();

        $this->assertlessThan(15, count($grid));
        foreach ($grid as $row) {
            $this->assertlessThan(15, count($row));
        }
    }

    public function test_player_enters_door()
    {
        $room = Room::create(self::DEFAULT_ROOM);

        $room->enterPlayer();

        $this->assertEquals(RoomTile::PLAYER, $room->getTileAt(2, 0));
    }

    public function test_set_the_player_to_a_position()
    {
        $room = Room::create(<<<EOL
        ##|#
        #  #
        #  #
        ####
        EOL
        );
        $room->enterPlayer();

        $room->setPlayerPosition(3, 3);

        $this->assertEquals(RoomTile::PLAYER, $room->getTileAt(3, 3));
    }

    public function test_render_correct_string()
    {
        $roomString = <<<EOL
        ##|#
        #  #
        #  #
        ####
        EOL;
        $room = Room::create($roomString);

        $output = $room->render();
        $this->assertSame($roomString, $output);
    }

}