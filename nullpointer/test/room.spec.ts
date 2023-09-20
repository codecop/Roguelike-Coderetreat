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

    it('get room with open door field represented by an X', () => {
        const room = new Room();

        room.setDoorOpener([9, 5]);

        const expectedRoom = "###########\n" +
            "#@        #\n" +
            "#         #\n" +
            "#         #\n" +
            "#         |\n" +
            "#        X#\n" +
            "###########\n";

        expect(room.getRoom()).toBe(expectedRoom);
    });

});
