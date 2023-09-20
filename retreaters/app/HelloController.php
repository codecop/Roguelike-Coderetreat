<?php

namespace App;

use Illuminate\Http\Request;
use Laravel\Lumen\Routing\Controller;

class HelloController extends Controller {

    private $hello;

    public function __construct(Hello $hello)
    {
        $this->hello = $hello;
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
}
