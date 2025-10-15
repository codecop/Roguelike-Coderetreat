import { Room } from '../src/room';

describe('Room', () => {
  it('should open the doors correctly', () => {
    const room = new Room();

    expect(room.getOpenDoors()).toBe(false);
    room.openTheDoors();
    expect(room.getOpenDoors()).toBe(true);
  }); 
});
