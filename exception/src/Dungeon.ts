export default class Dungeon {
    private currentPlayerPosition: Array<number> = [1,1]

    setNewPosition(row: number, column: number) : void{
        this.currentPlayerPosition = [row,column]
    }

    print(): string {

        let layout = "";

        Array(5).fill(null).forEach((_,row) => {
            const isBottomLine = row === 4;
            Array(5).fill(null).forEach((_, column) => {
                layout += this.createField(row, column, isBottomLine);
            })
            if(!isBottomLine) {
                layout += "\n";
              }
        });
        
        return layout;
    }

    private createField(row: number, column: number, isBottomLine: boolean) {
        const hasDoor = column === 0 && row === 1;
        if (hasDoor) {
            return "|";
        } 
        const hasPlayer = column === this.currentPlayerPosition[1] && row === this.currentPlayerPosition[0];
        if (hasPlayer) {
            console.log(this.currentPlayerPosition)
            return "@";
        }
        const isTopOrBottomLine = row === 0 || isBottomLine;
        const isLeftOrRightLine = column === 0 || column === 4;
        if (!isTopOrBottomLine && !isLeftOrRightLine) {
            return " ";
        }
        return "#";

        
    }
}
