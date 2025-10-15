export default class Room {
  player = "@";
  returnLayout(): string {
    const layout = "######\n#    #\n#    |\n#    #\n######";
    // console.log(layout);
    return layout;
  }

  setNewPosition(row: number, column: number): string {
    const layout = this.returnLayout()
      .split("\n")
      .map((line) => line.split(""));
    layout[row][column] = this.player;
    const newLayout = layout.map((line) => line.join("")).join("\n");
    return newLayout;
  }
}
