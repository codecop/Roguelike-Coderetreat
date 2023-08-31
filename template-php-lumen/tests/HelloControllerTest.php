<?php

namespace Tests;

class HelloControllerTest extends TestCase
{
    /** @test */
    public function firstHello()
    {
        $response = $this->call('GET', '/hello');

        $response->assertOk();
        $response->assertHeader('Content-Type', 'application/json')
                 ->assertJson([ 'name' => 'World!' ]);
    }

    /** @test */
    public function update()
    {
        $response = $this->call('POST', '/hello', [ 'name' => 'Peter' ]);
        $response->assertCreated();

        $response = $this->call('GET', '/hello');
        $response->assertHeader('Content-Type', 'application/json')
                 ->assertJson([ 'name' => 'Peter' ]);
    }

}
