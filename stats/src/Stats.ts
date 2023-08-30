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

    hasDynamic(id: string): boolean {
        return this.dynamic[id] != undefined;
    }

    createDynamic(id: string): void {
        if (this.dynamic[id] == undefined) {
            this.dynamic[id] = 0;
        }
    }

    getDynamic(id: string): number {
        return this.dynamic[id];
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
