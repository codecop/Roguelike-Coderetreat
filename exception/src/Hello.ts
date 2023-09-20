export default class Hello {
    private name: string = "World!";

    getName(): string {
        return this.name;
    }

    setName(name: string): void {
        this.name = name;
    }
}
