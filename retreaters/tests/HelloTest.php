<?php

namespace Tests;

use PHPUnit\Framework\TestCase;
use App\Hello;

class HelloTest extends TestCase {

    /**
     * @test
     */
    public function shouldGreet() {
        $hello = new Hello();

        $name = $hello->getName();

        $this->assertEquals("World!", $name);
    }

}
