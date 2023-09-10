package org.codecop.rogue.stats;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class StatsHp {

    private int hp;
    private boolean alive;

    public StatsHp() {
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

}
