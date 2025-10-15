export default class Room {
  player = "@";
  layout = "######\n#    #\n#    |\n#    #\n######";
  returnLayout(): string {
    return this.layout;
  }

  setNewPosition(row: number, column: number): string {
    const layout = this.returnLayout()
      .split("\n")
      .map((line) => line.split(""));
    layout[row][column] = this.player;
    const newLayout = layout.map((line) => line.join("")).join("\n");
    this.layout = newLayout;
    return newLayout;
  }
}
