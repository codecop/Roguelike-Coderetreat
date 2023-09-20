<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It is a breeze. Simply tell Lumen the URIs it should respond to
| and give it the Closure to call when that URI is requested.
|
*/

$router->get("/hello", 'HelloController@get');
$router->post("/hello", 'HelloController@post');
$router->get("/retreaters", 'HelloController@layout');
$router->post("/retreaters", 'HelloController@setPosition');
