<?php

use PHPUnit\Framework\TestCase;

require_once __DIR__.'/../app/Player.php';

use App\Player;

class PlayerExperienceTest extends TestCase
{
    /** @test */
    public function it_initializes_player_with_zero_experience(): void
    {
        $player = new Player(1);

        $this->assertEquals(0, $player->getExperience());
        $this->assertEquals(1, $player->getLevel());
    }

    /** @test */
    public function it_adds_experience_to_player(): void
    {
        $player = new Player(1);
        $player->addExperience(5);

        $this->assertEquals(5, $player->getExperience());
    }

    /** @test */
    public function it_levels_up_at_10_experience(): void
    {
        $player = new Player(1);
        $player->addExperience(10);

        $this->assertEquals(2, $player->getLevel());
        $this->assertEquals(0, $player->getExperience());
    }

    /** @test */
    public function it_requires_15_experience_for_level_3(): void
    {
        $player = new Player(1);
        $player->addExperience(10); // Level 2
        $player->addExperience(15); // Level 3

        $this->assertEquals(3, $player->getLevel());
        $this->assertEquals(0, $player->getExperience());
    }

    /** @test */
    public function it_calculates_experience_required_for_next_level(): void
    {
        $player = new Player(1);

        // Level 1 -> 2 requires 10
        $this->assertEquals(10, $player->getExperienceToNextLevel());

        $player->addExperience(10); // Now level 2

        // Level 2 -> 3 requires 15 (10 * 1.5)
        $this->assertEquals(15, $player->getExperienceToNextLevel());

        $player->addExperience(15); // Now level 3

        // Level 3 -> 4 requires 22 (15 * 1.5 = 22.5, rounded down)
        $this->assertEquals(22, $player->getExperienceToNextLevel());
    }

    /** @test */
    public function it_handles_multiple_level_ups_from_single_experience_gain(): void
    {
        $player = new Player(1);
        $player->addExperience(50); // Should level up multiple times

        // 10 -> level 2, 15 -> level 3, 22 -> level 4, remaining 3
        $this->assertEquals(4, $player->getLevel());
        $this->assertEquals(3, $player->getExperience());
    }

    /** @test */
    public function it_retains_overflow_experience_after_level_up(): void
    {
        $player = new Player(1);
        $player->addExperience(12); // 10 to level up, 2 overflow

        $this->assertEquals(2, $player->getLevel());
        $this->assertEquals(2, $player->getExperience());
    }

    /** @test */
    public function it_serializes_experience_and_level_to_array(): void
    {
        $player = new Player(1);
        $player->addExperience(5);

        $data = $player->toArray();

        $this->assertEquals(5, $data['experience']);
        $this->assertEquals(1, $data['level']);
    }

    /** @test */
    public function it_deserializes_experience_and_level_from_array(): void
    {
        $data = [
            'id' => 1,
            'row' => 5,
            'col' => 5,
            'health' => 50,
            'skin' => '@',
            'experience' => 8,
            'level' => 2,
        ];

        $player = Player::fromArray($data);

        $this->assertEquals(8, $player->getExperience());
        $this->assertEquals(2, $player->getLevel());
    }

    /** @test */
    public function it_handles_missing_experience_in_deserialization(): void
    {
        $data = [
            'id' => 1,
            'row' => 5,
            'col' => 5,
            'health' => 50,
            'skin' => '@',
        ];

        $player = Player::fromArray($data);

        $this->assertEquals(0, $player->getExperience());
        $this->assertEquals(1, $player->getLevel());
    }

    /** @test */
    public function it_progresses_through_multiple_levels_correctly(): void
    {
        $player = new Player(1);

        // Level 1 -> 2: 10 XP
        $player->addExperience(10);
        $this->assertEquals(2, $player->getLevel());
        $this->assertEquals(15, $player->getExperienceToNextLevel());

        // Level 2 -> 3: 15 XP
        $player->addExperience(15);
        $this->assertEquals(3, $player->getLevel());
        $this->assertEquals(22, $player->getExperienceToNextLevel());

        // Level 3 -> 4: 22 XP (15 * 1.5 = 22.5, rounded down)
        $player->addExperience(22);
        $this->assertEquals(4, $player->getLevel());
        $this->assertEquals(33, $player->getExperienceToNextLevel()); // 22 * 1.5 = 33
    }
}
