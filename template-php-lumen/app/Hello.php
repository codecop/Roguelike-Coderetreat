<?php

namespace Rogue;

class Hello {

    private $name;

    public function __construct() {
        $this->name = "World!";
    }

    public function getName(): String {
        return $this->name;
    }

    public function setName($name): void {
        $this->name = $name;
    }

    public function nameAsJson(): String {
        $data = array(
            "name" => $this->getName()
        );
        return json_encode($data);
    }

}
