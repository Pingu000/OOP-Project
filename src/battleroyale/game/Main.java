package battleroyale.game;

import battleroyale.characters.*;
import battleroyale.tools.*;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Number of human players: ");
        int human = Integer.parseInt(sc.nextLine());
        System.out.print("Number of machine players: ");
        int machine = Integer.parseInt(sc.nextLine());
        System.out.print("Difficulty level (1-3): ");
        int diff = Integer.parseInt(sc.nextLine());

        BattleRoyaleGame game = new BattleRoyaleGame(diff);

        for (int i = 0; i < human; i++) {
            showCharacterStats();
            GameCharacter c = createCharacter(sc);
            showToolStats();
            Tool t = chooseTool(sc);
            game.addPlayer(c);
            game.assignTool(c, t);
        }
        Random rand = new Random();
        for (int i = 0; i < machine; i++) {
            GameCharacter c = switch (rand.nextInt(3)) {
                case 0 -> new Warrior("NPC" + (i + 1));
                case 1 -> new Mage("NPC" + (i + 1));
                default -> new Rogue("NPC" + (i + 1));
            };
            Tool t = switch (rand.nextInt(3)) {
                case 0 -> new Sword();
                case 1 -> new Shield();
                default -> new Amulet();
            };
            game.addNPCPlayer(c);
            game.assignTool(c, t);
        }

        System.out.println("Assignments:");
        game.printAssignments();

        game.runBattle();

        GameCharacter winner = game.getWinner();
        if (winner != null) {
            System.out.println("Winner: " + winner.getName());
        }

        System.out.print("Save result to file? (y/n) ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            try {
                game.saveResult("result.txt");
                System.out.println("Result saved.");
            } catch (IOException e) {
                System.out.println("Error saving file: " + e.getMessage());
            }
        }
    }

    private static GameCharacter createCharacter(Scanner sc) {
        while (true) {
            System.out.print("Choose character type (Warrior/Mage/Rogue): ");
            String type = sc.nextLine().trim().toLowerCase();
            if (type.equals("warrior") || type.equals("mage") || type.equals("rogue")) {
                System.out.print("Enter name: ");
                String name = sc.nextLine().trim();
                switch (type) {
                    case "warrior":
                        return new Warrior(name);
                    case "mage":
                        return new Mage(name);
                    default:
                        return new Rogue(name);
                }
            }
        }
    }

    private static Tool chooseTool(Scanner sc) {
        while (true) {
            System.out.print("Choose tool (Sword/Shield/Amulet): ");
            String type = sc.nextLine().trim().toLowerCase();
            switch (type) {
                case "sword":
                    return new Sword();
                case "shield":
                    return new Shield();
                case "amulet":
                    return new Amulet();
            }
        }
    }

    private static void showCharacterStats() {
        System.out.println("Character stats:");
        System.out.println(new Warrior("Warrior"));
        System.out.println(new Mage("Mage"));
        System.out.println(new Rogue("Rogue"));
    }

    private static void showToolStats() {
        System.out.println("Tool stats:");
        System.out.println("Sword: +5 attack");
        System.out.println("Shield: +3 defense");
        System.out.println("Amulet: +20 health");
    }
}
