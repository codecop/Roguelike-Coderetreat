export default class Room {
    private room: string[]
    private playerPosition: number
    constructor(playerPosition = [1, 1]) {
        const roomString =
            "###########\n" +
            "#         #\n" +
            "#         #\n" +
            "#         #\n" +
            "#         |\n" +
            "#         #\n" +
            "###########\n"
        this.room = roomString.split('');

        this.playerPosition = this.convertArrayPositionToStringIndex(playerPosition);
        this.setPlayerPosition(playerPosition);

    }

    getRoom() {
        return this.room.join('');
    }

    setPlayerPosition(playerPosition){
        this.room.splice(this.playerPosition, 1, " ");

        this.playerPosition = this.convertArrayPositionToStringIndex(playerPosition);

        this.room.splice(this.playerPosition, 1, "@");
    }

    convertArrayPositionToStringIndex(playerPosition){
        const playerYIndex =  playerPosition[1] * (11 +1); // 11 = gridLength
        const playerXIndex = playerPosition[0];
        return playerXIndex + playerYIndex;
    }

    setDoorOpener(doorOpenerPosition) {
        this.room.splice(
            this.convertArrayPositionToStringIndex(doorOpenerPosition),
            1,
            "X"
        );
    }
    getNewField(column, row) {
        return this.room[this.convertArrayPositionToStringIndex([column, row])];
    }
}