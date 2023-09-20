export default class Room {
    private xSize: number;
    private ySize: number;

    constructor(xSize: number, ySize: number) {
        this.xSize = xSize;
        this.ySize = ySize;
    }

    toString(): string {
        let layout: string = '';
        
        layout += this.startAndEndWall();
        layout += this.middleSpace();
        layout += this.startAndEndWall();

        return layout;
    }

    private middleSpace() {
        let space: string = '';
        for (let i = 0; i < this.ySize; i++) {
            space += '#';
            for (let j = 0; j < this.xSize; j++) {
                space += ' ' // TODO use repeatChars
            }
            space += '#\n';
        }
        return space;
    }

    private startAndEndWall() {
        let wall: string = '';
        wall = this.repeatCharacter('#', this.xSize+2);
        wall += '\n';

        return wall;
    }

    private repeatCharacter(char: string, repeat: number) {
        let characterString = '';
        for (let i = 0; i < repeat; i++) {
            characterString += char
        }
        
        return characterString;
    }
}
