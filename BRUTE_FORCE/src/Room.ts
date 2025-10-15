export default class Room {
  player = "@";
  layout = "######\n#@   #\n#    |\n#    #\n######";
  returnLayout(): string {
    return this.layout;
  }

  private parseLayout(): string[][] {
    return this.layout.split("\n").map((line) => line.split(""));
  }

  private clearPlayer(grid: string[][]): void {
    for (let r = 0; r < grid.length; r++) {
      for (let c = 0; c < grid[r].length; c++) {
        if (grid[r][c] === this.player) grid[r][c] = " ";
      }
    }
  }
  private placePlayer(grid: string[][], row: number, column: number): void {
    grid[row][column] = this.player;
  }
  private serializeLayout(grid: string[][]): string {
    return grid.map((line) => line.join("")).join("\n");
  }
  private assertInBounds(row: number, column: number, grid: string[][]): void {
    if (
      row < 0 ||
      row >= grid.length ||
      column < 0 ||
      column >= grid[row].length
    ) {
      throw new Error(`Out of bounds: (${row}, ${column})`);
    }
  }
  setNewPosition(row: number, column: number): string {
    const grid = this.parseLayout();
    this.assertInBounds(row, column, grid);
    this.clearPlayer(grid);
    this.placePlayer(grid, row, column);
    this.layout = this.serializeLayout(grid);
    return this.layout;
  }
}
