<?php

namespace App;

use Illuminate\Http\Request;
use Laravel\Lumen\Routing\Controller;

class HelloController extends Controller {

    private $hello;
    private $layout;

    public function __construct(Hello $hello, Layout $layout)
    {
        $this->hello = $hello;
        $this->layout = $layout;
    }

    public function get(Request $request) {
        $data = array(
            "name" => $this->hello->getName()
        );
        $content = json_encode($data);

        return response($content, 200)
               ->header('Content-Type', 'application/json');
    }

    public function post(Request $request) {
        if ($request->exists('name')) {
            $newName = $request->input('name');
            $this->hello->setName($newName);
            return response('', 201);
        }
        return response('', 400);
    }

    public function layout()
    {
        print_r($this->layout->grid);
    }

    public function setPosition(Request $request)
    {
        $positionX = $request->input('row');
        $positionY = $request->input('column');
        $this->layout->setNewPosition($positionX, $positionY);
        $this->layout();
    }
}
