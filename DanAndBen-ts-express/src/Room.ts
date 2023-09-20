export default class Room {
    private xSize: number;
    private ySize: number;
    private xPlayerPosition: number;
    private yPlayerPosition: number;

    constructor(xSize: number, ySize: number) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.xPlayerPosition = 0;
        this.yPlayerPosition = 1;
    }

    toString(): string {
        let layout: string = '';

        layout += this.startWall();
        layout += this.middleSpace();
        layout += this.endWall();

        return layout;
    }

    public setNewPlayerPosition(xPlayerPosition: number, yPlayerPosition: number) {
        this.xPlayerPosition = xPlayerPosition;
        this.yPlayerPosition = yPlayerPosition;
    }

    private middleSpace() {
        let space: string = '';
        for (let i = 0; i < this.ySize; i++) {
            space += '#';
            let line = this.repeatCharacter(' ', this.xSize);
            if (i === this.yPlayerPosition) { // line with character
                line = this.repeatCharacter(' ', this.xPlayerPosition)
                +'@' + line.substring(this.xPlayerPosition+1);
            }
            space += line;
            space += '#\n';
        }

        return space;
    }

    private startWall() {
        let wall: string;
        wall = this.repeatCharacter('#', this.xSize + 2);
        wall += '\n';

        return wall;
    }

    private endWall() {
        let wall: string;
        wall = this.startWall();

        return '#|' + wall.substring(2);
    }

    private repeatCharacter(char: string, repeat: number) {
        let characterString = '';
        for (let i = 0; i < repeat; i++) {
            characterString += char
        }

        return characterString;
    }
}
