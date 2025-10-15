export default class Room {
  player = "@";
  layout = "######\n#@   #\n#    |\n#    #\n######";
  returnLayout(): string {
    return this.layout;
  }
  //TODO: refactor the loops, break it into smaller functions
  setNewPosition(row: number, column: number): string {
    const layout = this.returnLayout()
      .split("\n")
      .map((line) => line.split(""));
    for (let r = 0; r < layout.length; r++) {
      for (let c = 0; c < layout[r].length; c++) {
        if (layout[r][c] === this.player) {
          layout[r][c] = " ";
        }
      }
    }
    layout[row][column] = this.player;
    const newLayout = layout.map((line) => line.join("")).join("\n");
    this.layout = newLayout;
    return newLayout;
  }
}
