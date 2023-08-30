const MAX_HP = 10;

export default class Stats {

    private hp: number = MAX_HP;

    getHp(): number {
        return this.hp;
    }

    resetHp(): void {
        this.hp = MAX_HP;
    }

    takeDamage(): void {
        this.hp -= 1;
        if (this.hp < 0) {
            this.hp = 0;
        }
    }

    heal(): void {
        this.hp += 1;
        if (this.hp > MAX_HP) {
            this.hp = MAX_HP;
        }
    }

    alive(): boolean {
        return this.hp > 0;
    }
}
