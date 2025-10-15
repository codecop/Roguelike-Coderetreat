import { generateRandomMap } from './generator';

const randomRoomDescriptions = [
  'A dark, damp cave with the sound of dripping water echoing through the chambers.',
  'A grand hall filled with ancient tapestries and a large chandelier hanging from the ceiling.',
  'A cozy room with a fireplace crackling in the corner and a plush armchair.',
  'A laboratory cluttered with strange potions and alchemical equipment.',
  'A library lined with towering bookshelves, filled with dusty tomes and scrolls.',
];

const generalRoomDescription =
  'The monsters are all around you, be careful! Switch the lever to open the doors and escape. For more fun you can also find portals that will teleport you to another location in the room.';

const randomMovementMessages = [
  'You hear a faint rustling sound.',
  'A cold breeze brushes past you.',
  'The floor creaks under your feet.',
  'You feel a slight vibration in the ground.',
  'A distant howl echoes through the room.',
];

const directions = [
  { x: 0, y: -1 }, // Up
  { x: 0, y: 1 }, // Down
  { x: -1, y: 0 }, // Left
  { x: 1, y: 0 }, // Right
];

export class Room {
  private openDoors: boolean;
  private map: string[];
  private description: string;
  private ticksCounter: number = 0;
  private playerPosition: { x: number; y: number };

  constructor() {
    this.map = this.regenerateRandomMap();
    this.description = this.generateRandomDescription();
    this.playerPosition = { x: 1, y: 1 };
    this.openDoors = false;
  }

  private generateRandomDescription(): string {
    const randomIndex = Math.floor(Math.random() * randomRoomDescriptions.length);
    return generalRoomDescription + ' ' + randomRoomDescriptions[randomIndex];
  }

  hasAllNeededElements(map: string[], amountOfMonsters: number): boolean {
    console.log(amountOfMonsters);
    const mapHasDoor = map.some((row) => row.includes('|'));
    const mapHasButton = map.some((row) => row.includes('l'));
    const mapHasMonster = map.some((row) => row.includes('M'));
    const mapHasGoodNumberOfPortals = map.join('\n').split('P').length - 1 === 3;

    return mapHasDoor && mapHasButton && mapHasMonster && mapHasGoodNumberOfPortals;
  }

  regenerateRandomMap() {
    const AMOUNT_OF_MONSTERS = 1;
    let map = generateRandomMap(AMOUNT_OF_MONSTERS);
    let conditionsFullfilled = this.hasAllNeededElements(map, AMOUNT_OF_MONSTERS);

    while (!conditionsFullfilled) {
      map = generateRandomMap(AMOUNT_OF_MONSTERS);
      conditionsFullfilled = this.hasAllNeededElements(map, AMOUNT_OF_MONSTERS);
    }

    return map;
  }

  monsterCollision() {
    console.log('You are next to a monster!');

    fetch('http://localhost:8002/stats/hp?action=hit', { method: 'POST' }).catch((error) => {
      console.error('Error hitting player:', error);
    });
  }

  buttonCollision() {
    console.log('You pressed the Botond! The doors are now open.');
    this.openTheDoors();
  }

  portalCollision(x: number, y: number) {
    console.log('You entered a portal! You get very dizzy and a bit lost.');
    const currentPortalPosition = { x, y };
    const otherPortalPositions: { x: number; y: number }[] = [];

    console.log('curr', currentPortalPosition);

    for (let row = 0; row < this.map.length; row++) {
      for (let col = 0; col < this.map[row].length; col++) {
        if (
          this.map[row][col] === 'P' &&
          !(col === currentPortalPosition.x && row === currentPortalPosition.y)
        ) {
          otherPortalPositions.push({ x: col, y: row });
        }
      }
    }

    console.log('others', otherPortalPositions);

    // choose a random other portal to teleport to
    if (otherPortalPositions.length > 0) {
      const randomIndex = Math.floor(Math.random() * otherPortalPositions.length);
      const newPortal = otherPortalPositions[randomIndex];
      console.log(newPortal);

      // set player position to a tile next to the portal
      const availableDirections = directions.filter((dir) => {
        const newX = newPortal.x + dir.x;
        const newY = newPortal.y + dir.y;
        return this.map[newY]?.[newX] === ' ';
      });

      if (availableDirections.length > 0) {
        const randomDirIndex = Math.floor(Math.random() * availableDirections.length);
        const chosenDirection = availableDirections[randomDirIndex];
        this.playerPosition = {
          x: newPortal.x + chosenDirection.x,
          y: newPortal.y + chosenDirection.y,
        };
      }
    }
  }

  checkPositionEvents() {
    for (const direction of directions) {
      const newX = this.playerPosition.x + direction.x;
      const newY = this.playerPosition.y + direction.y;

      if (this.map[newY]?.[newX] === 'M') {
        this.monsterCollision();
      }
      if (this.map[newY]?.[newX] === 'l') {
        this.buttonCollision();
      }
      if (this.map[newY]?.[newX] === 'P') {
        this.portalCollision(newX, newY);
      }
    }
  }

  setNewPosition(row: number, column: number) {
    if (this.map[row][column] !== ' ' && this.map[row][column] !== '|') {
      throw new Error('Invalid position');
    }
    this.playerPosition = { x: column, y: row };

    this.checkPositionEvents();

    const randomIndex = Math.floor(Math.random() * randomMovementMessages.length);
    return randomMovementMessages[randomIndex];
  }

  private replaceCharAt(str: string, startPos: number): string {
    return str.slice(0, startPos) + '@' + str.slice(startPos + 1);
  }

  moveMonsters() {
    const monsterPositions: { x: number; y: number }[] = [];
    for (let y = 0; y < this.map.length; y++) {
      for (let x = 0; x < this.map[y].length; x++) {
        if (this.map[y][x] === 'M') monsterPositions.push({ x, y });
      }
    }

    for (const pos of monsterPositions) {
      const possibleDirections = directions.filter((dir) => {
        const newX = pos.x + dir.x;
        const newY = pos.y + dir.y;
        return (
          this.map[newY]?.[newX] === ' ' &&
          !(newX === this.playerPosition.x && newY === this.playerPosition.y)
        );
      });

      if (possibleDirections.length > 0) {
        const randomDirection =
          possibleDirections[Math.floor(Math.random() * possibleDirections.length)];
        const newX = pos.x + randomDirection.x;
        const newY = pos.y + randomDirection.y;

        // Move monster to new position
        const rowArray = this.map[pos.y].split('');
        rowArray[pos.x] = ' ';
        this.map[pos.y] = rowArray.join('');

        const newRowArray = this.map[newY].split('');
        newRowArray[newX] = 'M';
        this.map[newY] = newRowArray.join('');
      }
    }
    for (const direction of directions) {
      const newX = this.playerPosition.x + direction.x;
      const newY = this.playerPosition.y + direction.y;

      if (this.map[newY]?.[newX] === 'M') {
        this.monsterCollision();
      }
    }
  }

  getMap(): string {
    this.ticksCounter += 1;
    if (this.ticksCounter >= 4) {
      this.ticksCounter = 0;
      this.moveMonsters();
    }

    const playerRow = this.map[this.playerPosition.y];
    const mapWithPlayerPosition = [...this.map];
    mapWithPlayerPosition[this.playerPosition.y] = this.replaceCharAt(
      playerRow,
      this.playerPosition.x,
    );
    return mapWithPlayerPosition.join('\n');
  }

  openTheDoors() {
    this.openDoors = true;
  }

  getDescription(): string {
    return this.description;
  }

  getOpenDoors(): boolean {
    return this.openDoors;
  }
}
