import Room from '../src/Room';

describe('Room', () => {

    it('the room should return the hardcoded layout', () => {
        const room = new Room();
        room.returnLayout();
        expect(room.returnLayout()).toEqual('######\n#@   #\n#    |\n#    #\n######');
    });

    it("should set the player's new position", () => {
        const room = new Room();
        room.setNewPosition(2, 3);
        expect(room.setNewPosition(2,3)).toEqual('######\n#    #\n#  @ |\n#    #\n######');
    });
});
