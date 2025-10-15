export default class Room {
    constructor(private innerWidth: number, private innerHeight: number) {}
    
    print(): string {
        const wall = "#";
        const free = " ";
        const door = "|";
        const doorPosition = Math.ceil(this.innerWidth / 2);

        let string = wall.repeat(this.innerWidth + 2) + "\n";
        

        for (let y = 0; y < doorPosition - 1; y++) {
            let line = wall + free.repeat(this.innerWidth) + wall;
            string += line + "\n";
        }

        string += wall + free.repeat(this.innerWidth) + door + "\n";

        for (let y = doorPosition; y < this.innerHeight; y++) {
            let line = wall + free.repeat(this.innerWidth) + wall;
            string += line + "\n";
        }
        string += wall.repeat(this.innerWidth + 2) + "\n";

        return string;
    }
}
