# Implemented Features

## Room Difficulty System

Rooms can now be created with different difficulty levels that determine the number of monsters spawned:

- **Easy**: 1-2 monsters
- **Medium**: 3-4 monsters  
- **Hard**: 5-7 monsters
- **Hell**: 8-15 monsters

### Usage

When creating a game, pass the `difficulty` parameter:

```php
POST /create-game
{
    "boardsizeX": 20,
    "boardsizeY": 15,
    "playerCount": 1,
    "difficulty": "hard"  // Options: easy, medium, hard, hell
}
```

If no difficulty is specified, it defaults to **easy**.

### Monster Spawning

Monsters are automatically spawned based on difficulty:
- Each difficulty has appropriate monster types from `monsters_config.json`
- Monsters are placed randomly in empty positions
- Monster types are filtered by the `difficulties` array in the config

## Player Experience & Leveling System

Players now have an experience and leveling system:

### Core Mechanics

- **Starting Level**: 1
- **Starting Experience**: 0
- **First Level Up**: 10 XP (Level 1 → 2)
- **Subsequent Levels**: Each level requires 50% more XP than the previous
  - Level 2 → 3: 15 XP
  - Level 3 → 4: 22 XP (15 × 1.5, rounded down)
  - Level 4 → 5: 33 XP (22 × 1.5)
  - And so on...

### Player Stats Progression

#### Health
- **Base Health**: 50 HP at level 1
- **Per Level**: +5 max HP
- **Formula**: `maxHealth = 50 + ((level - 1) × 5)`
- **Examples**:
  - Level 1: 50 HP
  - Level 2: 55 HP
  - Level 3: 60 HP
  - Level 10: 95 HP

#### Damage
- **Base Damage**: 5 at level 1
- **Per Level**: +1 damage
- **Formula**: `damage = 5 + (level - 1)`
- **Examples**:
  - Level 1: 5 damage
  - Level 2: 6 damage
  - Level 3: 7 damage
  - Level 10: 14 damage

### Level Up Benefits

When a player levels up:
1. Their max health increases by 5
2. Their damage increases by 1
3. **They are fully healed** to their new max health

### API Response

Player data now includes experience and level information:

```json
{
    "id": 1,
    "health": 55,
    "maxHealth": 55,
    "row": 1,
    "col": 1,
    "skin": "@",
    "experience": 5,
    "level": 2,
    "damage": 6
}
```

### Adding Experience

```php
$player->addExperience($amount);
```

The system automatically:
- Adds the experience
- Checks for level ups
- Handles multiple level ups from a single XP gain
- Carries over overflow experience to the next level

### Example Progression

```php
$player = new Player(1);
// Level 1: 50 HP, 5 damage, 0 XP

$player->addExperience(10);  
// Level 2: 55 HP (fully healed), 6 damage, 0 XP

$player->addExperience(15);  
// Level 3: 60 HP (fully healed), 7 damage, 0 XP

$player->addExperience(25);  
// Level 4: 65 HP (fully healed), 8 damage, 3 XP overflow
```

## State Persistence

All player data (including experience, level, and damage) is saved to the room state file and persists between sessions.

## Monster Configuration

The `storage/monsters_config.json` file now includes difficulty filtering:

```json
{
  "Goblin": {
    "hp": 20,
    "experience": 2,
    "damage": 5,
    "critRate": 0.1,
    "skin": "G",
    "aggressive": true,
    "difficulties": ["easy", "medium"]
  }
}
```

Monsters will only spawn in rooms matching their difficulty levels.
