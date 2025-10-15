# Combat System Documentation

## Overview

The game now features an automatic time-based combat system where players and monsters fight when they are adjacent to each other.

## Combat Mechanics

### Attack Timing
- Both players and monsters have a **2-second cooldown** between attacks
- Combat is triggered by calling the `/room/combat` endpoint
- The system checks if 2 seconds have passed since the last attack before allowing a new one

### Combat Resolution

When a player is adjacent to a monster (including diagonally):
1. **Player attacks first** (if cooldown allows)
   - Deals damage equal to player's damage stat
   - Monster health is reduced
   - If monster dies, player gains experience
2. **Monster counterattacks** (if still alive and cooldown allows)
   - Deals damage based on monster's damage stat (with potential crits)
   - Player health is reduced

### Adjacency Detection

Players and monsters are considered adjacent if they are within 1 tile of each other in any direction:
```
M . .
. P .    Monster (M) is adjacent to Player (P)
. . .
```

## Health Bars

Both players and monsters now track:
- **Current Health**: How much HP they have now
- **Max Health**: Maximum HP capacity

### Player Max Health
- Base: 50 HP at level 1
- Growth: +5 HP per level
- Formula: `maxHealth = 50 + ((level - 1) × 5)`

### Monster Max Health
- Defined in `monsters_config.json` per monster type
- Example:
  ```json
  {
    "Goblin": {
      "hp": 20,
      "damage": 5,
      ...
    }
  }
  ```

## API Endpoint

### POST `/room/combat`

Processes combat between all adjacent players and monsters.

**Request:**
```bash
POST /room/combat
```

**Response:**
```json
{
  "layout": "...",
  "players": [
    {
      "id": 1,
      "health": 45,
      "maxHealth": 50,
      "damage": 5,
      "level": 1,
      "experience": 3,
      "lastCombatTime": 1697385600.123,
      ...
    }
  ],
  "monsters": [
    {
      "id": 1,
      "type": "Goblin",
      "health": 15,
      "maxHealth": 20,
      "damage": 5,
      "lastCombatTime": 1697385600.123,
      ...
    }
  ],
  "combatLog": [
    {
      "attacker": "player",
      "attackerId": 1,
      "target": "monster",
      "targetId": 1,
      "damage": 5,
      "monsterHealth": 15,
      "monsterMaxHealth": 20,
      "monsterAlive": true
    },
    {
      "attacker": "monster",
      "attackerId": 1,
      "target": "player",
      "targetId": 1,
      "damage": 5,
      "playerHealth": 45,
      "playerMaxHealth": 50,
      "playerAlive": true
    }
  ],
  "combatOccurred": true
}
```

### Combat Log Structure

Each combat event includes:
- **attacker**: "player" or "monster"
- **attackerId**: ID of the attacker
- **target**: "player" or "monster"
- **targetId**: ID of the target
- **damage**: Amount of damage dealt
- **targetHealth**: Current health after damage
- **targetMaxHealth**: Maximum health
- **targetAlive**: Whether the target survived
- **experienceGained** (if monster died): XP awarded to player
- **playerLevel** (if monster died): Player's level (may have increased)

## Combat Loop Example

### Client-Side Implementation

To create a continuous combat experience, call the combat endpoint every 2 seconds:

```javascript
// Start combat loop
setInterval(async () => {
  const response = await fetch('/room/combat', {
    method: 'POST'
  });
  
  const data = await response.json();
  
  if (data.combatOccurred) {
    // Update UI with combat log
    data.combatLog.forEach(event => {
      if (event.attacker === 'player') {
        console.log(`Player dealt ${event.damage} damage to monster`);
        if (!event.monsterAlive) {
          console.log(`Monster defeated! Gained ${event.experienceGained} XP`);
        }
      } else {
        console.log(`Monster dealt ${event.damage} damage to player`);
      }
    });
    
    // Update health bars
    updateHealthBars(data.players, data.monsters);
  }
}, 2000); // Every 2 seconds
```

## Health Bar Display Example

```javascript
function createHealthBar(current, max) {
  const percentage = (current / max) * 100;
  return `
    <div class="health-bar">
      <div class="health-fill" style="width: ${percentage}%"></div>
      <span class="health-text">${current}/${max}</span>
    </div>
  `;
}

// For each player
players.forEach(player => {
  const healthBar = createHealthBar(player.health, player.maxHealth);
  // Render health bar in UI
});

// For each monster
monsters.forEach(monster => {
  const healthBar = createHealthBar(monster.health, monster.maxHealth);
  // Render health bar in UI
});
```

## Experience Rewards

When a monster is defeated:
1. Player gains experience based on monster type (from `monsters_config.json`)
2. If enough XP is gained, player may level up
3. On level up:
   - Player's max health increases by 5
   - Player's damage increases by 1
   - Player is fully healed
4. Combat log includes `experienceGained` and updated `playerLevel`

## State Persistence

All combat-related data is automatically saved:
- Player health and last combat time
- Monster health and last combat time
- Player experience and level progress

This ensures combat state persists between sessions and page refreshes.

## Monster Attack Mechanics

Monsters can perform critical hits:
- **Critical Rate**: Defined per monster type in config
- **Critical Damage**: 2× normal damage
- Example: Goblin with 5 damage and 10% crit rate can deal 5 or 10 damage

## Tips for Implementation

1. **Call `/room/combat` regularly**: Set up a 2-second interval on the client
2. **Check `combatOccurred`**: Only update UI if combat actually happened
3. **Display health bars**: Show current/max health for all entities
4. **Show combat feedback**: Display damage numbers and combat events
5. **Handle death**: Remove dead monsters from display, show game over for dead players
6. **Level up notifications**: Alert players when they level up from combat

## Future Enhancements

Potential additions to the combat system:
- Player attack animations
- Monster movement AI
- Special abilities and skills
- Equipment and weapon modifiers
- Status effects (poison, stun, etc.)
- Different attack ranges
- Area-of-effect attacks
