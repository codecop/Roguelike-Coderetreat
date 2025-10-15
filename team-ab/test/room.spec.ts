import Room from "../src/Room";

describe("Room", () => {
  beforeAll(() => {
    jest.useFakeTimers();
  });
  afterAll(() => {
    jest.useRealTimers();
  });

  it("prints layout", () => {
    const room = new Room(7, 6);
    const layout =
      "#########\n" +
      "#       #\n" +
      "#       #\n" +
      "#       #\n" +
      "#       |\n" +
      "#       #\n" +
      "#_      #\n" +
      "#########\n";
    room.generateLayout();
    expect(room.layout).toBe(layout);
  });

  it("raises error for too big dimensions", () => {
    expect(() => new Room(14, 14)).toThrow(
      "Room inner dimensions too big, max is 13x13"
    );
  });

  it("initializes player position", () => {
    const room = new Room(7, 6);
    room.generateLayout();
    room.setNewPlayerPosition(3, 4);
    expect(room.getRowAt(4)).toContain("#  @    |");
  });

  it("sets new player position", () => {
    const room = new Room(7, 6);
    room.generateLayout();
    room.setNewPlayerPosition(2, 2);
    expect(room.getRowAt(2)).toBe("# @     #");

    // Ensure only one @ exists after moving
    expect((room.layout.match(/@/g) || []).length).toBe(1);

    room.setNewPlayerPosition(4, 4);
    expect(room.getRowAt(4)).toBe("#   @   |");

    // Ensure still only one @ exists after moving again
    expect((room.layout.match(/@/g) || []).length).toBe(1);
  });

  it("displays the pressure plate", () => {
    const room = new Room(5, 7);
    room.generateLayout();
    expect(room.getRowAt(6)).toContain("#_    #");
  });

  it("closes the door automatically after 1 seconds", () => {
    const room = new Room(5, 5);
    room.generateLayout();

    room.interactWith("_");
    expect(room.doorIsOpen).toBe(true);
    jest.advanceTimersByTime(999);

    expect(room.doorIsOpen).toBe(true);

    jest.advanceTimersByTime(1);
    expect(room.doorIsOpen).toBe(false);
  });

  it("closes the door automatically second time after 3 seconds", () => {
    const room = new Room(5, 5);
    room.generateLayout();

    room.interactWith("_");
    expect(room.doorIsOpen).toBe(true);
    
    jest.advanceTimersByTime(1000);
    
    expect(room.doorIsOpen).toBe(false);
    room.interactWith("_");
    expect(room.doorIsOpen).toBe(true);
    
    jest.advanceTimersByTime(2999);
    
    expect(room.doorIsOpen).toBe(true);
  });

  it("spawns walls when player opens the door", () => {
    const room = new Room(7, 6);
    room.generateLayout();
    expect(room.doorIsOpen).toBe(false);
    room.interactWith("_");
    expect(room.doorIsOpen).toBe(true);

    expect(room.getRowAt(3)).toBe("#       #");
    expect(room.getRowAt(4)).toBe("#   #   |");
    expect(room.getRowAt(5)).toBe("#   #   #");
    expect(room.getRowAt(6)).toBe("#_  #   #");
  });

  it("spawns even more walls when player opens the door a second time", () => {
    const room = new Room(7, 6);
    room.generateLayout();
    expect(room.doorIsOpen).toBe(false);
    room.interactWith("_");
    expect(room.doorIsOpen).toBe(true);

    jest.advanceTimersByTime(5000);

    expect(room.doorIsOpen).toBe(false);
    room.interactWith("_");
    expect(room.doorIsOpen).toBe(true);

    expect(room.getRowAt(1)).toBe("#     # #");
    expect(room.getRowAt(2)).toBe("#     # #");
    expect(room.getRowAt(3)).toBe("#     # #");
    expect(room.getRowAt(4)).toBe("#   # # |");
    expect(room.getRowAt(5)).toBe("#   #   #");
    expect(room.getRowAt(6)).toBe("#_  #   #");
  });
});
