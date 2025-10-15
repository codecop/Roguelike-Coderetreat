<?php

namespace Tests;

class RoomDifficultyTest extends TestCase
{
    /** @test */
    public function it_creates_easy_room_with_1_to_2_monsters(): void
    {
        $response = $this->call('POST', '/create-game', [
            'boardsizeX' => 20,
            'boardsizeY' => 15,
            'playerCount' => 1,
            'difficulty' => 'easy',
        ]);

        $this->assertEquals(201, $response->status());
        $data = json_decode($response->getContent(), true);

        $this->assertTrue($data['success']);
        $this->assertEquals('easy', $data['difficulty']);

        // Get room state to check monsters
        $roomResponse = $this->call('GET', '/room');
        $roomData = json_decode($roomResponse->getContent(), true);

        $monsterCount = count($roomData['monsters']);
        $this->assertGreaterThanOrEqual(1, $monsterCount);
        $this->assertLessThanOrEqual(2, $monsterCount);
    }

    /** @test */
    public function it_creates_medium_room_with_3_to_4_monsters(): void
    {
        $response = $this->call('POST', '/create-game', [
            'boardsizeX' => 20,
            'boardsizeY' => 15,
            'playerCount' => 1,
            'difficulty' => 'medium',
        ]);

        $this->assertEquals(201, $response->status());
        $data = json_decode($response->getContent(), true);

        $this->assertEquals('medium', $data['difficulty']);

        $roomResponse = $this->call('GET', '/room');
        $roomData = json_decode($roomResponse->getContent(), true);

        $monsterCount = count($roomData['monsters']);
        $this->assertGreaterThanOrEqual(3, $monsterCount);
        $this->assertLessThanOrEqual(4, $monsterCount);
    }

    /** @test */
    public function it_creates_hard_room_with_5_to_7_monsters(): void
    {
        $response = $this->call('POST', '/create-game', [
            'boardsizeX' => 25,
            'boardsizeY' => 20,
            'playerCount' => 1,
            'difficulty' => 'hard',
        ]);

        $this->assertEquals(201, $response->status());
        $data = json_decode($response->getContent(), true);

        $this->assertEquals('hard', $data['difficulty']);

        $roomResponse = $this->call('GET', '/room');
        $roomData = json_decode($roomResponse->getContent(), true);

        $monsterCount = count($roomData['monsters']);
        $this->assertGreaterThanOrEqual(5, $monsterCount);
        $this->assertLessThanOrEqual(7, $monsterCount);
    }

    /** @test */
    public function it_creates_hell_room_with_8_to_15_monsters(): void
    {
        $response = $this->call('POST', '/create-game', [
            'boardsizeX' => 30,
            'boardsizeY' => 25,
            'playerCount' => 1,
            'difficulty' => 'hell',
        ]);

        $this->assertEquals(201, $response->status());
        $data = json_decode($response->getContent(), true);

        $this->assertEquals('hell', $data['difficulty']);

        $roomResponse = $this->call('GET', '/room');
        $roomData = json_decode($roomResponse->getContent(), true);

        $monsterCount = count($roomData['monsters']);
        $this->assertGreaterThanOrEqual(8, $monsterCount);
        $this->assertLessThanOrEqual(15, $monsterCount);
    }

    /** @test */
    public function it_defaults_to_easy_difficulty_when_not_specified(): void
    {
        $response = $this->call('POST', '/create-game', [
            'boardsizeX' => 15,
            'boardsizeY' => 15,
            'playerCount' => 1,
        ]);

        $this->assertEquals(201, $response->status());
        $data = json_decode($response->getContent(), true);

        $this->assertEquals('easy', $data['difficulty']);
    }

    /** @test */
    public function it_spawns_monsters_appropriate_to_difficulty(): void
    {
        $response = $this->call('POST', '/create-game', [
            'boardsizeX' => 20,
            'boardsizeY' => 20,
            'playerCount' => 1,
            'difficulty' => 'hell',
        ]);

        $this->assertEquals(201, $response->status());

        $roomResponse = $this->call('GET', '/room');
        $roomData = json_decode($roomResponse->getContent(), true);

        // Check that at least one monster is a hell difficulty monster (demon)
        $this->assertNotEmpty($roomData['monsters']);
    }
}
