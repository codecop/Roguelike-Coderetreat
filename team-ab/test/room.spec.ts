import Room from "../src/Room";

describe("Room", () => {
  it("prints layout", () => {
    const room = new Room(7, 6);
    const layout = 
    "#########\n" + 
    "#       #\n" +
    "#       #\n" +
    "#       #\n" +
    "#       |\n" +
    "#       #\n" +
    "#       #\n" +
    "#########\n";
    expect(room.print()).toBe(layout);
  });
});
