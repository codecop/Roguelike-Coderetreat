export default class Stats {

    private hp: number = 100;

    getHp(): number {
        return this.hp;
    }

    resetHp(): void {
        this.hp = 100;
    }

    takeDamage(): void {
        this.hp -= 1;
    }

    heal(): void {
        this.hp += 1;
    }
}
