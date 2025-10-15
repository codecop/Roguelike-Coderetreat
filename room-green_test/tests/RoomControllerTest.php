<?php

namespace Tests;

class RoomControllerTest extends TestCase
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

    /** @test */
    public function should_get_layout()
    {
        $response = $this->call('GET', '/green_test');

        $response->assertOk();
        $response->assertHeader('Content-Type', 'application/json')
            ->assertJson([ 'layout' => "###\n# #\n###\n" ]);
    }

    /** @test */
    public function should_walk_on_layout() {
        $response = $this->call('POST', '/green_test/walk?row=1&column=1');
        $response->assertCreated();

        $response = $this->call('GET', '/green_test');
        $response->assertHeader('Content-Type', 'application/json')
            ->assertJson([ 'layout' => "###\n#@#\n###\n" ]);
    }
}
