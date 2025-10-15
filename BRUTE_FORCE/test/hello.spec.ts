import Room from '../src/Room';

describe('Room', () => {

    it('the room should return the hardcoded layout', () => {
        const room = new Room();
        room.returnLayout();
        expect(room.returnLayout()).toEqual('######\n#    #\n#    |\n#    #\n######');
    });

});
