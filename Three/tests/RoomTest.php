<?php

namespace Tests;

use App\Room;
use PHPUnit\Framework\TestCase;

class RoomTest extends TestCase {

    /**
     * @var int
     */
    private $width;
    /**
     * @var int
     */
    private $length;
    /**
     * @var Room
     */
    private $room;


    public function setUp(): void
    {
        $this->width = 10;
        $this->length = 10;
        $this->room = new Room($this->width, $this->length);
    }
    /**
     * @test
     */
    public function should_generate_advanced_map_structure()
    {
        $map = $this->room->generateMap();

        $this->assertIsArray($map[8]);
    }


    /**
     * @test
     */
    public function should_return_the_map_with_walls()
    {
        $this->room->generateMap();
        $this->room->addWalls();

        $this->assertEquals(array_fill(0, 10, "#"), $this->room->getMap()[0]);
    }

    /**
     * @test
     */
    public function should_return_the_map_with_door()
    {
        $this->room->generateMap();
        $this->room->addWalls();
        $this->room->addDoor();

        $this->assertEquals("|", $this->room->getMap()[2][9]);
    }

    /**
     * @test
     */
    public function should_return_stringified_map()
    {
        $this->room->generateMap();
        $this->room->addWalls();
        $this->room->addDoor();
        $expectedStringMap = "/n##########/n#        #/n#        |/n#        #/n#        #/n#        #/n#        #/n#        #/n#        #/n##########";

        $this->assertEquals($expectedStringMap, $this->room->getStringifiedMap());
    }
}
