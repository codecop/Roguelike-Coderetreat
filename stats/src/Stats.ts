const MAX_HP = 10;

export default class Stats {

    private hp: number = MAX_HP;
    private dynamic = {};

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

    private hasDynamic(id: string): boolean {
        return this.dynamic[id] != undefined;
    }

    private createDynamic(id: string): void {
        if (!this.hasDynamic(id)) {
            this.dynamic[id] = 0;
        }
    }

    getDynamic(id: string): number {
        if (this.hasDynamic(id)) {
            return this.dynamic[id];
        }
        return 0;
    }

    incDynamic(id: string): void {
        this.createDynamic(id);
        this.dynamic[id] += 1;
    }

    decDynamic(id: string): void {
        this.createDynamic(id);
        this.dynamic[id] -= 1;
    }

    resetDynamic(id: string): void {
        this.dynamic[id] = undefined;
    }

}
