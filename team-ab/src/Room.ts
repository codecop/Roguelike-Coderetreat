export default class Room {
  WALL = "#";
  FREE = " ";
  DOOR = "|";
  PLAYER = "@";
  PRESSURE_PLATE = "_";

  public layout: string = "";
  public doorIsOpen: boolean = false;
  private pressurePlatePosition = { row: 6, column: 1 };
  private doorCloseTimeout: NodeJS.Timeout | null = null;
  private numberOfDoorsOpen: number = 0;

  constructor(private innerWidth: number, private innerHeight: number) {
    if (innerWidth > 13 || innerHeight > 13) {
      throw new Error("Room inner dimensions too big, max is 13x13");
    }
  }

  getRowAt(y: number): string {
    const rows = this.layout.split("\n");
    return rows[y];
  }

  setNewPlayerPosition(column: number, row: number): void {
    if (
      column < 1 ||
      column > this.innerWidth ||
      row < 1 ||
      row > this.innerHeight
    ) {
      throw new Error("Player position out of bounds");
    }

    this.layout = this.layout.replace(this.PLAYER, this.FREE);

    this.placeObject(row, column, this.PLAYER);
  }

  generateLayout() {
    this.generateEmptyLayout();

    this.placeObject(
      this.pressurePlatePosition.row,
      this.pressurePlatePosition.column,
      this.PRESSURE_PLATE
    );
    this.placeObject(4, 8, this.DOOR);
  }

  private generateEmptyLayout() {
    let layout = this.WALL.repeat(this.innerWidth + 2) + "\n";
    layout += this.printRows(this.innerHeight);
    layout += this.WALL.repeat(this.innerWidth + 2) + "\n";
    this.layout = layout;
  }

  private printRows(numOfRows: number) {
    let string = "";
    for (let y = 0; y < numOfRows; y++) {
      string +=
        this.WALL + this.FREE.repeat(this.innerWidth) + this.WALL + "\n";
    }
    return string;
  }

  public interactWith(item: string): string {
    let message = "";
    if (item === this.PRESSURE_PLATE) {
      this.openDoor();
      message =
        "You stepped on a pressure plate. The door is now open. Hurry, it will close in 3 seconds!";
    } else {
      message = "Nothing happens.";
    }
    return message;
  }

  private openDoor() {
    if (this.doorIsOpen) {
      return;
    }
    
    this.doorIsOpen = true;
    this.numberOfDoorsOpen += 1;

    this.spawnObstacles();

    if (this.numberOfDoorsOpen > 1) {
      this.spawnMoreObstacles();
    }

    if (this.doorCloseTimeout) {
      clearTimeout(this.doorCloseTimeout);
    }

    this.doorCloseTimeout = setTimeout(() => {
      this.doorIsOpen = false;
      this.doorCloseTimeout = null;
    }, 3000);
    // Allow Node process (and Jest) to exit without waiting for this timeout
    (this.doorCloseTimeout as any).unref?.();
  }

  spawnMoreObstacles() {
    this.placeObject(1, 6, "#");
    this.placeObject(2, 6, "#");
    this.placeObject(3, 6, "#");
  }

  private spawnObstacles() {
    this.placeObject(4, 4, "#");
    this.placeObject(5, 4, "#");
    this.placeObject(6, 4, "#");
  }

  private placeObject(row: number, column: number, symbol: string) {
    const rows = this.layout.split("\n");
    rows[row] =
      rows[row].substring(0, column) + symbol + rows[row].substring(column + 1);
    this.layout = rows.join("\n");
  }
}
