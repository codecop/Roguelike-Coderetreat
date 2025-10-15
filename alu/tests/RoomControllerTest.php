<?php

namespace Tests;

use App\Database\RoomDatabase;

class RoomControllerTest extends TestCase
{

    protected function setUp(): void
    {
        parent::setUp();
        (new RoomDatabase)->deleteRoom(RoomDatabase::ALU_ROOM);
    }

    /** @test */
    public function showRoom()
    {
        $response = $this->call('GET', '/aluroom');
        $expectedLayout = (new RoomDatabase())->getRoom(RoomDatabase::ALU_ROOM);

        $response->assertOk();
        $response->assertHeader('Content-Type', 'application/json')
            ->assertJson([
                'layout' => $expectedLayout
            ]);
    }

    /** @test */
    public function testUpdate()
    {
        $expectedRoom = $this->fakeRoom(<<<EOL
            ##|###
            #    #
            #   @#
            #    #
            ######
            EOL
        );

        $response = $this->call('POST', '/aluroom/walk?row=3&column=5');
        $response->assertCreated();

        $this->call('GET', '/aluroom')
            ->assertJson(['layout' => $expectedRoom]);
    }

    private function fakeRoom(string $layout): string
    {
        return str_replace("\r\n", "\n", $layout);
    }

}
