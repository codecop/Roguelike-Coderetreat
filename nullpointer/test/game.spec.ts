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

        game.player.setPosition(9, 3);
        game.room.setPlayerPosition([9,3]);

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

});
