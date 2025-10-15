export default class Room {
  WALL = "#";
  FREE = " ";
  DOOR = "|";
  PLAYER = "@";

  public layout: string = "";

  constructor(private innerWidth: number, private innerHeight: number) {
    if (innerWidth > 13 || innerHeight > 13) {
      throw new Error("Room inner dimensions too big, max is 13x13");
    }
  }

  getRowAt(y: number): string {
    const rows = this.layout.split("\n");
    return rows[y];
  }

  setNewPlayerPosition(column: number, row: number) {
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
  }

  private printRows(numOfRows: number) {
    let string = "";
    for (let y = 0; y < numOfRows; y++) {
      string +=
        this.WALL + this.FREE.repeat(this.innerWidth) + this.WALL + "\n";
    }
    return string;
  }
}
