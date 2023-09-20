<?php

namespace Tests;

use PHPUnit\Framework\TestCase;
use Mockery;
use App\Layout;

class LayoutTest extends TestCase
{

    protected $layout;
    protected $expectedGrid;
    protected function setUp(): void
    {
        parent::setUp();

        $this->layout = new Layout(5);

        $this->expectedGrid = [
            0 => ['#', '#', '#', '#', '|', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'],
            1 => ['#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'],
            2 => ['#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'],
            3 => ['#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'],
            4 => ['#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'],
            5 => ['#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'],
            6 => ['#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'],
            7 => ['#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'],
            8 => ['#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'],
            9 => ['#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'],
            10 => ['#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'],
            11 => ['#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'],
            12 => ['#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'],
            13 => ['#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'],
            14 => ['#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#']
        ];
    }


    public function test_should_initialize_a_layout()
    {
        $grid = $this->layout->createLayout();

        $expectedGrid = "###############\n#             #\n#             #\n#             #\n#             #\n#             #\n#             #\n#             #\n#             #\n#             #\n#             #\n#             #\n#             #\n#             #\n###############\n";

        $this->assertEquals($expectedGrid, $grid);
    }


    public function test_add_door_to_layout()
    {
        $grid = $this->layout->createLayout();
        $grid = $this->layout->addDoor(5, $grid);

        $expectedGrid = "####|##########\n#             #\n#             #\n#             #\n#             #\n#             #\n#             #\n#             #\n#             #\n#             #\n#             #\n#             #\n#             #\n#             #\n###############\n";

        $this->assertEquals($expectedGrid, $grid);
    }


    public function test_should_create_array_for_every_row()
    {
        $grid = $this->layout->createLayout();
        $grid = $this->layout->addDoor(5, $grid);
        $grid = $this->layout->setInMultiArray($grid);

        $this->assertEquals($this->expectedGrid, $grid);
    }

    public function test_should_position_player_on_the_grid()
    {
        $this->layout->setNewPosition(3, 4);

        $this->assertEquals("@", $this->layout->grid[3][4]);
    }
    
}
