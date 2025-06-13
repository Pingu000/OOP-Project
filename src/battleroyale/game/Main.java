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
            GameCharacter c = createCharacter(sc);
            Tool t = chooseTool(sc);
            game.addPlayer(c);
            game.assignTool(c, t);
        }
        Random rand = new Random();
        for (int i = 0; i < machine; i++) {
            GameCharacter c;
            switch (rand.nextInt(3)) {
                case 0:
                    c = new Warrior("NPC" + (i + 1));
                    break;
                case 1:
                    c = new Mage("NPC" + (i + 1));
                    break;
                default:
                    c = new Rogue("NPC" + (i + 1));
                    break;
            }
            Tool t;
            switch (rand.nextInt(3)) {
                case 0:
                    t = new Sword();
                    break;
                case 1:
                    t = new Shield();
                    break;
                default:
                    t = new Staff();
                    break;
            }
            game.addPlayer(c);
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
            System.out.print("Choose tool (Sword/Shield/Staff): ");
            String type = sc.nextLine().trim().toLowerCase();
            switch (type) {
                case "sword":
                    return new Sword();
                case "shield":
                    return new Shield();
                case "staff":
                    return new Staff();
            }
        }
    }
}
