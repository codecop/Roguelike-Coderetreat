<?php

namespace Tests;

use PHPUnit\Framework\TestCase;
use Mockery;
use App\Layout;

class LayoutTest extends TestCase
{

    protected $layout;
    protected function setUp(): void
    {
        parent::setUp();

        $this->layout = new Layout();
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

        $expectedGrid = [
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

        $this->assertEquals($expectedGrid, $grid);
    }
}
