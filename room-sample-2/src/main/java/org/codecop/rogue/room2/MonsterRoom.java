package org.codecop.rogue.room2;

import java.util.Optional;

import org.codecop.rogue.stats.StatsClient;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Singleton;

@Singleton
public class MonsterRoom implements AnyRoom {

    private final StatsClient stats;

    public MonsterRoom(StatsClient stats) {
        this.stats = stats;
    }

    private static final char SYMBOL_PLAYER = '@';

    private final char[] initialLayout = ("" + //
            "#######\n" + //
            "#     #\n" + //
            "#     #\n" + //
            "#     |\n" + //
            "#     #\n" + //
            "#     #\n" + //
            "#######\n").toCharArray();
    private final int columns = new String(initialLayout).replaceAll("\n.*", "").length();

    private int tick;

    private int playerX = 3;
    private int playerY = 1;
    private int zombieX = 2;
    private int zombieY = 5;

    @Override
    public String layout() {
        zombieMoves();
        char[] layout = initialLayout.clone();
        setPlayerTo(layout);
        setZombieTo(layout);
        return new String(layout);
    }

    private void zombieMoves() {
        tick++;
        if (tick >= 3) {
            tick = 0;
            if (isNearPlayer()) {
                // has the Zombie been near?
                System.out.println("Zombie hits");
                stats.hit().subscribe(new Subscriber<HttpResponse<String>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                        System.out.println("POST to HP REQUESTED");
                    }

                    @Override
                    public void onNext(HttpResponse<String> t) {
                        System.out.println("POST to HP ANSWER: " + t.code());
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("POST to HP ERROR");
                        t.printStackTrace(System.err);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("POST to HP COMPLETE");
                    }
                });
            } else {
                // Zombie moves
                System.out.println("Zombie moves");
                if (playerX < zombieX) {
                    zombieX--;
                } else if (playerX > zombieX) {
                    zombieX++;
                } else if (playerY < zombieY) {
                    zombieY--;
                } else if (playerY > zombieY) {
                    zombieY++;
                }
            }
        }
    }

    private boolean isNearPlayer() {
        return Math.abs(playerX - zombieX) <= 1 && Math.abs(playerY - zombieY) <= 1;
    }

    private void setPlayerTo(char[] layout) {
        layout[asIndex(playerX, playerY)] = SYMBOL_PLAYER;
    }

    private void setZombieTo(char[] layout) {
        layout[asIndex(zombieX, zombieY)] = 'Z';
    }

    private int asIndex(int x, int y) {
        return y * (columns + 1) + x;
    }

    @Override
    public Optional<String> movesTo(Position newPosition) {
        playerX = newPosition.getColumn();
        playerY = newPosition.getRow();
        return Optional.empty();
    }

    @Override
    public String description() {
        return "A <Z>ombie is hear. Do not let it touch you.";
    }

}
