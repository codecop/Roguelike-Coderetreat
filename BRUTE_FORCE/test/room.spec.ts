import Room from '../src/Room';
import StatsClient from '../src/statsClient';

describe('Room', () => {

    it('the room should return the hardcoded layout', () => {
        const room = new Room();
        room.returnLayout();
        expect(room.returnLayout()).toEqual("######\n#@   #\n#  f |\n#    #\n######");
    });

    it("should set the player's new position", () => {
        const room = new Room();
        room.setNewPosition(2, 3);
        expect(room.setNewPosition(2,3)).toEqual('######\n#    #\n#  @ |\n#    #\n######');
    });
        it('should hurt the player if he walks near fire in the room', async () => {
            const room = new Room();

            // Spy on StatsClient prototype so the internal new StatsClient() calls are intercepted
            const hitSpy = jest.spyOn(StatsClient.prototype, 'hit').mockResolvedValue();
            const getHealthSpy = jest.spyOn(StatsClient.prototype, 'getHealth');

            // Mock initial and post-damage health values
            getHealthSpy
                .mockResolvedValueOnce(10) // starting health
                .mockResolvedValueOnce(9); // after damage

            // Move player adjacent (left) to fire (fire is at row 2, col 3)
            room.setNewPosition(2,2);

            // Trigger damage check
            await room.hurtIfNearFire(2,2);

            const startingHealth = await room.getHealth();
            const afterHealth = await room.getHealth();

            expect(hitSpy).toHaveBeenCalledTimes(1);
            expect(startingHealth).toBe(10);
            expect(afterHealth).toBe(9);

            hitSpy.mockRestore();
            getHealthSpy.mockRestore();
        });
     
});
