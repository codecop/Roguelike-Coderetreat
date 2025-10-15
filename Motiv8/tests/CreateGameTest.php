<?php

namespace Tests;

class CreateGameTest extends TestCase
{
    public function testCreateGameWithValidParameters()
    {
        $response = $this->call('POST', '/create-game', [
            'boardsizeX' => 20,
            'boardsizeY' => 15,
            'playerCount' => 3
        ]);

        $this->assertEquals(201, $response->status());
        $data = json_decode($response->getContent(), true);
        
        $this->assertTrue($data['success']);
        $this->assertEquals(20, $data['boardSize']['x']);
        $this->assertEquals(15, $data['boardSize']['y']);
        $this->assertCount(3, $data['players']);
        
        // Check each player has starting health of 50
        foreach ($data['players'] as $player) {
            $this->assertEquals(50, $player['health']);
            $this->assertArrayHasKey('id', $player);
            $this->assertArrayHasKey('row', $player);
            $this->assertArrayHasKey('col', $player);
        }
    }

    public function testCreateGameWithSinglePlayer()
    {
        $response = $this->call('POST', '/create-game', [
            'boardsizeX' => 10,
            'boardsizeY' => 10,
            'playerCount' => 1
        ]);

        $this->assertEquals(201, $response->status());
        $data = json_decode($response->getContent(), true);
        
        $this->assertCount(1, $data['players']);
        $this->assertEquals(50, $data['players'][0]['health']);
    }

    public function testCreateGameWithMaximumPlayers()
    {
        $response = $this->call('POST', '/create-game', [
            'boardsizeX' => 30,
            'boardsizeY' => 30,
            'playerCount' => 10
        ]);

        $this->assertEquals(201, $response->status());
        $data = json_decode($response->getContent(), true);
        
        $this->assertCount(10, $data['players']);
    }

    public function testCreateGameFailsWithMissingParameters()
    {
        $response = $this->call('POST', '/create-game', [
            'boardsizeX' => 20
        ]);

        $this->assertEquals(422, $response->status());
    }

    public function testCreateGameFailsWithInvalidBoardSize()
    {
        $response = $this->call('POST', '/create-game', [
            'boardsizeX' => 2,
            'boardsizeY' => 10,
            'playerCount' => 2
        ]);

        $this->assertEquals(422, $response->status());
    }

    public function testCreateGameResetsExistingState()
    {
        // Create first game
        $response1 = $this->call('POST', '/create-game', [
            'boardsizeX' => 10,
            'boardsizeY' => 10,
            'playerCount' => 2
        ]);

        $this->assertEquals(201, $response1->status());

        // Create second game with different parameters
        $response2 = $this->call('POST', '/create-game', [
            'boardsizeX' => 15,
            'boardsizeY' => 15,
            'playerCount' => 3
        ]);

        $this->assertEquals(201, $response2->status());
        $data = json_decode($response2->getContent(), true);
        
        $this->assertEquals(15, $data['boardSize']['x']);
        $this->assertCount(3, $data['players']);
    }

    public function testGetRoomAfterCreateGame()
    {
        // Create game
        $this->call('POST', '/create-game', [
            'boardsizeX' => 15,
            'boardsizeY' => 15,
            'playerCount' => 2
        ]);

        // Get room state
        $response = $this->call('GET', '/room');
        $this->assertEquals(200, $response->status());
        
        $data = json_decode($response->getContent(), true);
        $this->assertArrayHasKey('layout', $data);
        $this->assertArrayHasKey('players', $data);
        $this->assertCount(2, $data['players']);
    }
}
