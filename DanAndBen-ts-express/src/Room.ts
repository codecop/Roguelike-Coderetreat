export default class Room {
    private xSize: number;
    private ySize: number;
    private playerRow: number;
    private playerColumn: number;

    constructor(xSize: number, ySize: number) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.playerRow = 2;
        this.playerColumn = 1;
    }

    toString(): string {
        let layout: string = '';

        layout += this.startWall();
        layout += this.middleSpace();
        layout += this.endWall();

        return layout;
    }

    public setNewPlayerPosition(playerRow: number, playerColumn: number) {
        this.playerRow = playerRow;
        this.playerColumn = playerColumn;
    }

    private middleSpace() {
        let space: string = '';
        for (let row = 1; row <= this.ySize; row++) {
            space += '#';
            let line = this.repeatCharacter(' ', this.xSize);
            if (row === this.playerRow) { // line with character
                line = this.repeatCharacter(' ', this.playerColumn-1)
                +'@' + line.substring(this.playerColumn);
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
    
    public getDescription(): string {
        if(this.playerColumn === 1 && this.playerRow === 5) {
            return "Almost there!"
        }
        
        return '';
    }
}
