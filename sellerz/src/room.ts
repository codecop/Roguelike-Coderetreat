import { generateRandomMap } from './generator';

const randomRoomDescriptions = [
  'A dark, damp cave with the sound of dripping water echoing through the chambers.',
  'A grand hall filled with ancient tapestries and a large chandelier hanging from the ceiling.',
  'A cozy room with a fireplace crackling in the corner and a plush armchair.',
  'A laboratory cluttered with strange potions and alchemical equipment.',
  'A library lined with towering bookshelves, filled with dusty tomes and scrolls.',
];

const generalRoomDescription =
  'The monsters (M) are all around you, be careful! Press the button (B) to open the doors and escape.';

const randomMovementMessages = [
  'You hear a faint rustling sound.',
  'A cold breeze brushes past you.',
  'The floor creaks under your feet.',
  'You feel a slight vibration in the ground.',
  'A distant howl echoes through the room.',
];

export class Room {
  private openDoors: boolean;
  private map: string[];
  private description: string;
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
    const mapHasButton = map.some((row) => row.includes('B'));
    const mapHasMonster = map.some((row) => row.includes('M'));

    return mapHasDoor && mapHasButton && mapHasMonster;
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

    fetch('http://localhost:8002/stats/hp?action=hit', { method: 'POST' })
      .then((response) => response.json())
      .then((data) => {
        console.log('What?', data);
      })
      .catch((error) => {
        console.error('Error hitting player:', error);
      });
  }

  buttonCollision() {
    console.log('You pressed the Botond! The doors are now open.');
    this.openTheDoors();
  }

  checkPositionEvents() {
    const directions = [
      { x: 0, y: -1 }, // Up
      { x: 0, y: 1 }, // Down
      { x: -1, y: 0 }, // Left
      { x: 1, y: 0 }, // Right
    ];

    for (const direction of directions) {
      const newX = this.playerPosition.x + direction.x;
      const newY = this.playerPosition.y + direction.y;

      if (this.map[newY]?.[newX] === 'M') {
        this.monsterCollision();
      }
      if (this.map[newY]?.[newX] === 'B') {
        this.buttonCollision();
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
    const randomMovementMessage = randomMovementMessages[randomIndex];
    return randomMovementMessage;
  }

  private replaceCharAt(str: string, startPos: number): string {
    return str.slice(0, startPos) + '@' + str.slice(startPos + 1);
  }

  getMap(): string {
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
