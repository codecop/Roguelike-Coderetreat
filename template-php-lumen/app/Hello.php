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

    public function load() {
        if (file_exists("./name.txt")) {
            $newName = json_decode(file_get_contents("./name.txt"), true);
            $this->setName($newName);
        }
    }

    public function save() {
        file_put_contents("./name.txt", json_encode($this->name));
    }

}
