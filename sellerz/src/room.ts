import { generateRandomMap } from './generator';

export class Room {
  private map: string[];
  private playerPosition: { x: number; y: number };

  constructor() {
    this.map = this.regenerateRandomMap();
    this.playerPosition = { x: 1, y: 1 };
  }

  regenerateRandomMap() {
    let map = generateRandomMap();
    let mapHasDoor = map.some((row) => row.includes('|'));

    while (!mapHasDoor) {
      map = generateRandomMap();
      mapHasDoor = map.some((row) => row.includes('|'));
    }

    return map;
  }

  setNewPosition(row: number, column: number) {
    if (this.map[row][column] !== ' ' && this.map[row][column] !== '|') {
      throw new Error('Invalid position');
    }
    this.playerPosition = { x: column, y: row };
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
}
