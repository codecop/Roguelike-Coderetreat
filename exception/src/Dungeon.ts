export default class Dungeon {
    private currentPlayerPosition: Array<number> = [1,1]

    setNewPosition(x: number, y: number) : void{
        this.currentPlayerPosition = [x,y]
    }

    print(): string {

        let layout = "";

        Array(5).fill(null).forEach((_,y) => {
            const isBottomLine =  y === 4;
            Array(5).fill(null).forEach((_, x) => {
                layout += this.createField(y, x, isBottomLine);
            })
            if(!isBottomLine) {
                layout += "\n";
              }
        });
        
        return layout;
    }

    private createField(y: number, x: number, isBottomLine: boolean) {
        const hasDoor = y === 1 && x === 0;
        if (hasDoor) {
            return "|";
        } 
        const hasPlayer = y === this.currentPlayerPosition[1] && x === this.currentPlayerPosition[0];
        if (hasPlayer) {
            return "@";
        }
        const isTopOrBottomLine = y === 0 || isBottomLine;
        const isLeftOrRightLine = x === 0 || x === 4;
        if (!isTopOrBottomLine && !isLeftOrRightLine) {
            return " ";
        }
        return "#";

        
    }
}
