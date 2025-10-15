export default class Room {
  WALL = "#";
  FREE = " ";
  DOOR = "|";
  PLAYER = "@";

  public layout: string = "";
  public doorIsOpen: boolean = false;
  private pressurePlatePosition = { row: 6, column: 1 };
  private doorCloseTimeout: NodeJS.Timeout | null = null;

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
    if (column < 1 || column > this.innerWidth || row < 1 || row > this.innerHeight) {
      throw new Error("Player position out of bounds");
    }

    this.layout = this.layout.replace(this.PLAYER, this.FREE);

    const rows = this.layout.split("\n");
    rows[row] = rows[row].substring(0, column) + this.PLAYER + rows[row].substring(column + 1);
    this.layout = rows.join("\n");
  }

  generateLayout() {
    const doorPosition = Math.floor(this.innerHeight / 2) + 1;

    let layout = this.WALL.repeat(this.innerWidth + 2) + "\n";
    layout += this.printRows(doorPosition - 1);
    layout += this.WALL + this.FREE.repeat(this.innerWidth) + this.DOOR + "\n";
    layout += this.printRows(this.innerHeight - doorPosition);
    layout += this.WALL.repeat(this.innerWidth + 2) + "\n";
    this.layout = layout;
    this.placePressurePlate();
  }
  placePressurePlate() {
    const rows = this.layout.split("\n");
    const row = this.pressurePlatePosition.row;
    const column = this.pressurePlatePosition.column;
    rows[row] = rows[row].substring(0, column) + "_" + rows[row].substring(column + 1);
    this.layout = rows.join("\n");
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
    if (item === "_") {
      this.openDoor();
      message = "You stepped on a pressure plate. The door is now open. Hurry, it will close in 3 seconds!";
    } else {
      message = "Nothing happens.";
    }
    return message;
  }

  private openDoor() {
    this.doorIsOpen = true;
    // Reset any existing scheduled close
    if (this.doorCloseTimeout) {
      clearTimeout(this.doorCloseTimeout);
    }
    // Auto close after 3 seconds
    this.doorCloseTimeout = setTimeout(() => {
      this.doorIsOpen = false;
      this.doorCloseTimeout = null;
    }, 3000);
    // Allow Node process (and Jest) to exit without waiting for this timeout
    (this.doorCloseTimeout as any).unref?.();
  }
}
