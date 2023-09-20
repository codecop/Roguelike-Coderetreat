export default class Player {
    private position: Object;

    constructor() {
        this.position = [1,1];
    }

    getPosition() {
        return this.position;
    }

    setPosition(xPosition: number, yPosition: number) {
        this.position = [xPosition, yPosition];
    }
}