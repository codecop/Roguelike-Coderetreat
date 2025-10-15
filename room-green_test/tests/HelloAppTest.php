<?php

namespace Tests;

class HelloAppTest extends TestCase
{
    /** @before */
    public function beforeAll() {
        if (file_exists("./name.txt")) {
            unlink("./name.txt");
        }
    }

    /** @test */
    public function firstHello()
    {
        $this->markTestSkipped();
        $response = $this->call('GET', '/hello');

        $response->assertOk();
        $response->assertHeader('Content-Type', 'application/json')
                 ->assertJson([ 'name' => 'World!' ]);
    }

    /** @test */
    public function update()
    {
        $this->markTestSkipped();
        $response = $this->call('POST', '/hello?name=Peter');
        $response->assertCreated();

        $response = $this->call('GET', '/hello');
        $response->assertHeader('Content-Type', 'application/json')
                 ->assertJson([ 'name' => 'Peter' ]);
    }

}
