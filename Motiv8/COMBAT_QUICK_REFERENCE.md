# Combat System - Quick Reference

## ✅ Automatic Combat

Combat now happens automatically! No need for manual combat endpoint calls.

### When Combat Triggers

1. **GET `/room`** - Combat processes automatically (every fetch)
2. **POST `/room/walk`** - Combat processes after movement
3. **POST `/room/combat`** - Manual combat trigger (still available)

### Client Setup

Since your client fetches `/room` every 0.1 seconds, combat will automatically process:

```javascript
// Your existing code already triggers combat!
setInterval(() => {
  fetch('/room')
    .then(res => res.json())
    .then(data => {
      // Check if combat occurred
      if (data.combatOccurred) {
        console.log('Combat happened!', data.combatLog);
        // Update UI with combat events
      }
      
      // Update game state
      updatePlayers(data.players);
      updateMonsters(data.monsters);
      updateLayout(data.layout);
    });
}, 100); // Every 0.1 seconds
```

## Combat Cooldown

- **2-second cooldown** between attacks
- Even though `/room` is called every 0.1s, attacks only happen every 2s
- Prevents spam and creates turn-based feel

## Response Format

All endpoints now return:

```json
{
  "layout": "...",
  "players": [...],
  "monsters": [...],
  "combatLog": [
    {
      "attacker": "player",
      "attackerId": 1,
      "target": "monster",
      "targetId": 2,
      "damage": 5,
      "monsterHealth": 15,
      "monsterMaxHealth": 20,
      "monsterAlive": true
    }
  ],
  "combatOccurred": true
}
```

## Combat Log Events

### Player Attack
```json
{
  "attacker": "player",
  "attackerId": 1,
  "target": "monster",
  "targetId": 2,
  "damage": 5,
  "monsterHealth": 15,
  "monsterMaxHealth": 20,
  "monsterAlive": true,
  "experienceGained": 2,  // Only if monster died
  "playerLevel": 2         // Only if monster died
}
```

### Monster Attack
```json
{
  "attacker": "monster",
  "attackerId": 2,
  "target": "player",
  "targetId": 1,
  "damage": 5,
  "playerHealth": 45,
  "playerMaxHealth": 50,
  "playerAlive": true
}
```

## UI Integration Tips

### Display Combat Feedback

```javascript
if (data.combatOccurred) {
  data.combatLog.forEach(event => {
    if (event.attacker === 'player') {
      showDamageNumber(event.targetId, event.damage, 'player-damage');
      
      if (!event.monsterAlive) {
        showMessage(`Monster defeated! +${event.experienceGained} XP`);
        if (event.playerLevel) {
          showMessage(`Level Up! Now level ${event.playerLevel}`);
        }
      }
    } else {
      showDamageNumber(event.targetId, event.damage, 'monster-damage');
      
      if (!event.playerAlive) {
        showMessage('You died!');
        // Handle game over
      }
    }
  });
}
```

### Update Health Bars

```javascript
// Update player health bars
data.players.forEach(player => {
  const healthPercent = (player.health / player.maxHealth) * 100;
  updateHealthBar(`player-${player.id}`, healthPercent, player.health, player.maxHealth);
});

// Update monster health bars
data.monsters.forEach(monster => {
  const healthPercent = (monster.health / monster.maxHealth) * 100;
  updateHealthBar(`monster-${monster.id}`, healthPercent, monster.health, monster.maxHealth);
});
```

## State Persistence

All combat data is automatically saved to `storage/room_state.json`:
- Player health and combat times
- Monster health and combat times  
- Experience and levels
- All updates are persisted immediately

## Testing Combat

1. Create a game with difficulty (to spawn monsters):
   ```bash
   POST /create-game
   {
     "boardsizeX": 20,
     "boardsizeY": 15,
     "playerCount": 1,
     "difficulty": "easy"
   }
   ```

2. Move player next to a monster:
   ```bash
   POST /room/walk
   {
     "row": 5,
     "column": 5,
     "playerId": 1
   }
   ```

3. Combat happens automatically!
   - Check `combatLog` in response
   - Check `storage/room_state.json` for updated health/times

4. Continue fetching `/room` every 0.1s
   - New attacks every 2 seconds
   - Experience gained when monsters die
   - Level ups happen automatically

## No Changes Needed!

Your existing client code should work perfectly. Just check for:
- `combatOccurred` flag
- `combatLog` array for combat events
- Updated `health` and `maxHealth` values
