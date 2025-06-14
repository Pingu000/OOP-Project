package battleroyale.game;

import battleroyale.characters.*;
import battleroyale.tools.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BattleRoyaleGame {
    private final List<GameCharacter> players = new ArrayList<>();
    private final Set<GameCharacter> humanPlayers = new HashSet<>();
    private final BattleLog log = new BattleLog(1000);
    private final Random random = new Random();
    private final int difficulty;

    public BattleRoyaleGame(int difficulty) {
        this.difficulty = difficulty;
    }

    public void addPlayer(GameCharacter character) {
        players.add(character);
        humanPlayers.add(character);
    }

    public void addNPCPlayer(GameCharacter character) {
        character.increaseAttack(difficulty * 5);
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
        System.out.println("Battle starts!");
        Scanner sc = new Scanner(System.in);
        int round = 1;
        while (aliveCount() > 1) {
            System.out.println("--- Round " + round + " ---");
            log.add("Round " + round);
            Set<GameCharacter> defending = new HashSet<>();
            Collections.shuffle(players, random);
            for (GameCharacter p : new ArrayList<>(players)) {
                if (!p.isAlive()) continue;

                if (humanPlayers.contains(p)) {
                    System.out.print(p.getName() + " choose action (attack/defend/heal): ");
                    String action = sc.nextLine().trim().toLowerCase();
                    if (action.equals("heal")) {
                        p.heal(25);
                        log.add(p.getName() + " healed");
                        System.out.println(p.getName() + " healed (HP: " + p.getHealth() + ")");
                    } else if (action.equals("defend")) {
                        defending.add(p);
                        log.add(p.getName() + " is defending");
                        System.out.println(p.getName() + " is defending");
                    } else { // attack
                        GameCharacter target = chooseTargetFromInput(p, sc);
                        if (target != null) {
                            performAttack(p, target, defending);
                            if (aliveCount() <= 1) break;
                        }
                    }
                } else { // NPC behaviour
                    GameCharacter target = selectTarget(p);
                    if (target != null) {
                        performAttack(p, target, defending);
                        if (aliveCount() <= 1) break;
                    }
                }
            }
            printStatus();
            round++;
        }
        log.add("Battle finished");
        System.out.println("Battle finished");
    }

    private void printStatus() {
        for (GameCharacter c : players) {
            if (c.isAlive()) {
                System.out.println(c.getName() + ": " + c.getHealth() + " HP");
            }
        }
    }

    private GameCharacter selectTarget(GameCharacter attacker) {
        List<GameCharacter> alive = new ArrayList<>();
        for (GameCharacter c : players) {
            if (c.isAlive() && c != attacker) alive.add(c);
        }
        if (alive.isEmpty()) return null;
        return alive.get(random.nextInt(alive.size()));
    }

    private GameCharacter chooseTargetFromInput(GameCharacter attacker, Scanner sc) {
        List<GameCharacter> alive = new ArrayList<>();
        for (GameCharacter c : players) {
            if (c.isAlive() && c != attacker) alive.add(c);
        }
        if (alive.isEmpty()) return null;
        while (true) {
            System.out.println("Choose target:");
            for (int i = 0; i < alive.size(); i++) {
                System.out.println((i + 1) + ". " + alive.get(i).getName());
            }
            String in = sc.nextLine().trim();
            try {
                int idx = Integer.parseInt(in) - 1;
                if (idx >= 0 && idx < alive.size()) {
                    return alive.get(idx);
                }
            } catch (NumberFormatException ignore) {
            }
            for (GameCharacter c : alive) {
                if (c.getName().equalsIgnoreCase(in)) return c;
            }
        }
    }

    private void performAttack(GameCharacter attacker, GameCharacter target, Set<GameCharacter> defending) {
        int damage = attacker.getAttack();
        if (defending.contains(target)) {
            damage /= 2;
        }
        target.receiveDamage(damage);
        log.add(attacker.getName() + " attacked " + target.getName());
        System.out.println(attacker.getName() + " attacked " + target.getName() +
                " (" + target.getName() + " HP: " + target.getHealth() + ")");
        if (!target.isAlive()) {
            log.add(target.getName() + " has fallen.");
            System.out.println(target.getName() + " has fallen.");
        }
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