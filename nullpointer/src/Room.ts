export default class Room {
    private room: string[]
    private playerPosition: number
    constructor(playerPosition) {
        const roomString =
            "###########\n" +
            "#         #\n" +
            "#         #\n" +
            "#         #\n" +
            "#         |\n" +
            "#         #\n" +
            "###########\n"
        this.room = roomString.split('');

        this.playerPosition = this.convertPlayerArrayPositionToStringIndex(playerPosition);
        this.setPlayerPosition(playerPosition);

    }

    getRoom() {
        return this.room.join('');
    }

    setPlayerPosition(playerPosition){
        this.room.splice(this.playerPosition, 1, " ");

         this.playerPosition = this.convertPlayerArrayPositionToStringIndex(playerPosition);

        this.room.splice(this.playerPosition, 1, "@");
    }

    convertPlayerArrayPositionToStringIndex(playerPosition){
        const playerYIndex =  playerPosition[1] * (11 +1); // 11 = gridLength
        const playerXIndex = playerPosition[0];
        return playerXIndex + playerYIndex;
    }

}