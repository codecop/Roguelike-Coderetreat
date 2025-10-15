<?php

namespace Tests;

use App\Database\RoomDatabase;

class RoomControllerTest extends TestCase
{

    protected function setUp(): void
    {
        parent::setUp();
        (new RoomDatabase)->deleteRoom(RoomDatabase::ALU_ROOM_DEFAULT);
    }

    /** @test */
    public function showRoom()
    {
        $response = $this->call('GET', '/aluroom');
        $expectedRoom = (new RoomDatabase())->getRoom(RoomDatabase::ALU_ROOM_DEFAULT);

        $response->assertOk();
        $response->assertHeader('Content-Type', 'application/json')
            ->assertJson([
                'layout' => $expectedRoom->render()
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

        $response = $this->call('POST', '/aluroom/walk?row=2&column=4');
        $response->assertCreated();

        $this->call('GET', '/aluroom')
            ->assertJson(['layout' => $expectedRoom]);
    }

    public function testWalkFrom3x2to1x4()
    {
        $expectedRoom = $this->fakeRoom(<<<EOL
            ##|###
            #   @#
            #    #
            #    #
            ######
            EOL
        );

        $this->call('POST', '/aluroom/walk?row=3&column=2');
        $this->call('POST', '/aluroom/walk?row=1&column=4');

        $this->call('GET', '/aluroom')->assertJson(['layout' => $expectedRoom]);
    }

    /** @test */
    public function roomShouldHaveADescription()
    {
        $response = $this->call('GET', '/aluroom');

        $response->assertOk();
        $json = json_decode($response->getContent(), true);
        self::assertNotEmpty($json['description']);
    }

    private function fakeRoom(string $layout): string
    {
        return str_replace("\r\n", "\n", $layout);
    }

}
