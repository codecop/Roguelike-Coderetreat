import Player from './Player';
import Room from './Room';

export default class Game {
    public player;
    public room;
    public doorOpened = false;

    constructor() {
        this.player = new Player();
        this.room = new Room(this.player.getPosition());
        // this.room.setDoorOpener([9, 5]);
    }

    getCurrentRoom() {
        return this.room.getRoom();
    }

    movePlayer([column, row]) {
        const newField = this.room.getNewField(column, row);

        this.player.setPosition(column, row);
        this.room.setPlayerPosition(this.player.getPosition());

        if(newField === "X") {
            this.doorOpened = true;
        }

        // hardcoded fix
        if (column == 9 && row ==5) {
            this.doorOpened = true;
        }
    }
}