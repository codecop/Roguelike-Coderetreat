import Player from './Player';
import Room from './Room';

export default class Game {
    public player;
    public room;

    constructor() {
        this.player = new Player();
        this.room = new Room(this.player.getPosition());
    }

    getCurrentRoom() {
        return this.room.getRoom();
    }
}