import Game from '../src/Game';

describe('Game', () => {

    it('should have player', () => {
        const game = new Game();

        expect(game.player).toBeDefined();
    });

    it('should have room', () => {
        const game = new Game();

        expect(game.room).toBeDefined();
    });

    it('should have room with player on its start point', () => {
        const game = new Game();

        expect(game.getCurrentRoom()).toStrictEqual(
            "###########\n" +
            "#@        #\n" +
            "#         #\n" +
            "#         #\n" +
            "#         |\n" +
            "#         #\n" +
            "###########\n"
        );
    });

    it('should update room after player moves', () => {
        const game = new Game();

        game.movePlayer([9, 3]);

        expect(game.getCurrentRoom()).toStrictEqual(
            "###########\n" +
            "#         #\n" +
            "#         #\n" +
            "#        @#\n" +
            "#         |\n" +
            "#         #\n" +
            "###########\n"
        );
    });

    it('opens door if player steps on the door opener', () => {
        const game = new Game();

        expect(game.doorOpened).toBeFalsy();

        game.room.setDoorOpener([9, 5]);

        game.movePlayer([3, 4]);

        expect(game.doorOpened).toBeFalsy();

        game.movePlayer([9, 5]);

        expect(game.doorOpened).toBeTruthy();
    });

});
