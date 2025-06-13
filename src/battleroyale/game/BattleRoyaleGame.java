package battleroyale.game;

import battleroyale.characters.*;
import battleroyale.tools.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BattleRoyaleGame {
    private final List<GameCharacter> players = new ArrayList<>();
    private final BattleLog log = new BattleLog(1000);
    private final Random random = new Random();
    private final int difficulty;

    public BattleRoyaleGame(int difficulty) {
        this.difficulty = difficulty;
    }

    public void addPlayer(GameCharacter character) {
        players.add(character);
    }

    public void assignTool(GameCharacter c, Tool t) {
        if (t.checkModification(c)) {
            t.modify(c);
            log.add(c.getName() + " equipped " + t.getName());
        }
    }

    public void runBattle() {
        log.add("Battle starts!");
        while (aliveCount() > 1) {
            Collections.shuffle(players, random);
            for (GameCharacter p : new ArrayList<>(players)) {
                if (!p.isAlive()) continue;
                GameCharacter target = selectTarget(p);
                if (target != null) {
                    p.attack(target);
                    log.add(p.getName() + " attacked " + target.getName());
                    if (!target.isAlive()) {
                        log.add(target.getName() + " has fallen.");
                    }
                    if (aliveCount() <= 1) break;
                }
            }
        }
        log.add("Battle finished");
    }

    private GameCharacter selectTarget(GameCharacter attacker) {
        List<GameCharacter> alive = new ArrayList<>();
        for (GameCharacter c : players) {
            if (c.isAlive() && c != attacker) alive.add(c);
        }
        if (alive.isEmpty()) return null;
        return alive.get(random.nextInt(alive.size()));
    }

    private int aliveCount() {
        int count = 0;
        for (GameCharacter c : players) if (c.isAlive()) count++;
        return count;
    }

    public GameCharacter getWinner() {
        for (GameCharacter c : players) if (c.isAlive()) return c;
        return null;
    }

    public void saveResult(String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String s : log.getAll()) {
                bw.write(s);
                bw.newLine();
            }
            GameCharacter winner = getWinner();
            if (winner != null) {
                bw.write("Winner: " + winner.getName());
                bw.newLine();
            }
        }
    }

    public void printAssignments() {
        for (GameCharacter c : players) {
            System.out.println(c);
        }
    }
}
