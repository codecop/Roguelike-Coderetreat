<?php

namespace Tests;

class RoomControllerTest extends TestCase
{
    /** @test */
    public function should_get_layout()
    {
        $response = $this->call('GET', '/green_test');

        $response->assertOk();
        $response->assertHeader('Content-Type', 'application/json')
            ->assertJson([ 'layout' => "###\n#|#\n###\n" ]);
    }
}
