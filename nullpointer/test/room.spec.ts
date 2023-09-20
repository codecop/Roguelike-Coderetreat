import Room from '../src/Room';

describe('Room', () => {

    it('get room', () => {
        const room = new Room();

        const expectedRoom = "###########\n" +
            "#@        #\n" +
            "#         #\n" +
            "#         #\n" +
            "#         |\n" +
            "#         #\n" +
            "###########\n";

        expect(room.getRoom()).toBe(expectedRoom);
    });

});
