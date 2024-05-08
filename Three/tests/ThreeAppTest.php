<?php

namespace Tests;

class ThreeAppTest extends TestCase
{
    /** @test */
    public function should_return_initial_json_map()
    {
        $response = $this->call('GET', '/three');

        $response->assertOk();
        $response->assertHeader('Content-Type', 'application/json')
                 ->assertJson([ 'layout' => "##########\n#@       #\n#        |\n#        #\n#        #\n#        #\n#        #\n#        #\n#        #\n##########\n" ]);
    }

    /** @test */
    public function update()
    {
        $response = $this->call('POST', '/three/walk?row=3&column=5');
        $response->assertCreated();
    }

}
