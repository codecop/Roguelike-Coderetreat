import Room from '../src/Room';

describe('Room', () => {

    it('returns 1x1 room layout in string', () => {
        const room = new Room(1, 1);
        expect(room.toString()).toBe(`###
# #
#|#
`
        );
    });

    it('return 2x2 room layout in string', () => {
        const twoByTwoRoom = new Room(2, 2);
        expect(twoByTwoRoom.toString()).toBe(`####
#  #
#@ #
#|##
`)
    })

    it('return 2x2 room with character', () => {
        const twoByTwoRoomWithCharacter = new Room(2,2);
        expect(twoByTwoRoomWithCharacter.toString()).toBe(`####
#  #
#@ #
#|##
`)
    })
    
    it('returns room with moved character', () => {
        const room = new Room(2,2);
        
        room.setNewPlayerPosition(2,2);
        
        expect(room.toString()).toBe(`####
#  #
# @#
#|##
`)
    })
});
