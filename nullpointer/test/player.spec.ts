import Player from '../src/Player';

describe('Player', () => {

    it('should exist', () => {
        const player = new Player();

        expect(player).toBeDefined();
    });

    it('should have starting position', () => {
        const player = new Player();

        expect(player.getPosition()).toStrictEqual([1,1]);
    });

    it('should update position', () => {
        const player = new Player();

        player.setPosition(2,3);


        expect(player.getPosition()).toStrictEqual([2,3]);
    });



});
