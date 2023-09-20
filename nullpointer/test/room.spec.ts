import Room from '../src/Room';

describe('Room', () => {

    it('get room', () => {
        const room = new Room();

        const expectedRoom = "###########" +
            "#         #" +
            "#         #" +
            "#         #" +
            "#         |" +
            "#         #" +
            "###########";

        expect(room.getRoom()).toBe(expectedRoom);
    });

});
