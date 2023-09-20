import Dungeon from '../src/Dungeon';

describe('Dungeon', () => {

    // it('should print a wall block', () => {
    //     const dungeon = new Dungeon();

    //     const dungeonField = dungeon.generate(1,1)
    //     expect(dungeonField).toBe('#');
    // });

    // it('should print a wall', () => {
    //     const dungeon = new Dungeon();

    //     const dungeonField = dungeon.generate(1,3)
    //     expect(dungeonField).toBe('###');
    // });

    // it('should print a room with size 3', () => {
    //     const dungeon = new Dungeon();

    //     const dungeonField = dungeon.generate(3,3)
    //     expect(dungeonField).toBe(`
    //     ###
    //     # #
    //     ###
    //     `);
    // });

    it('should print a default layout of 5x5 with door at 1,0', () => {
        const dungeon = new Dungeon();

        const layout = dungeon.print()
        expect(layout).toBe(`#####\n|   #\n#   #\n#   #\n#####`);
    });

    it('should print a default layout with a player at 1,1', () => {
        const dungeon = new Dungeon();
        dungeon.setNewPosition(1,1);
        
        const layout = dungeon.print()
        expect(layout).toBe(`#####\n|@  #\n#   #\n#   #\n#####`);
    });

    it('should change player position one field to the right', () => {
        const dungeon = new Dungeon();
        dungeon.setNewPosition(1,1);
        dungeon.setNewPosition(2,1);
        
        const layout = dungeon.print()
        expect(layout).toBe(`#####\n| @ #\n#   #\n#   #\n#####`);
    });

});

