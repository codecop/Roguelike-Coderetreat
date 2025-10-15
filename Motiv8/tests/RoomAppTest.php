<?php
use Laravel\Lumen\Testing\TestCase;

class RoomAppTest extends TestCase
{
    /**
     * Creates the application.
     * @return \Laravel\Lumen\Application
     */
    public function createApplication()
    {
        return require __DIR__ . '/../bootstrap/app.php';
    }

    /** @test */
    public function it_returns_room_layout_on_get()
    {
        $response = $this->get('/room');
        $response->seeStatusCode(200)
                 ->seeJsonStructure(['layout']);
    }

    /** @test */
    public function it_updates_player_position_on_post()
    {
        $response = $this->post('/room/walk?row=2&column=2');
        $response->seeStatusCode(200)
                 ->seeJsonStructure(['layout'])
                 ->seeJsonContains(['layout' => $this->getExpectedLayout(2, 2)]);
    }

    private function getExpectedLayout($row, $col)
    {
        $room = new \RoomLayout(5, 5, $row, $col);
        return $room->serialize();
    }
}
