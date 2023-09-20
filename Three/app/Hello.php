<?php

namespace App;

class Hello {

    private $name;

    public function __construct() {
        $this->name = "World!";
    }

    public function getName(): String {
        return $this->name;
    }

    public function setName(String $name): void {
        $this->name = $name;
    }

}
