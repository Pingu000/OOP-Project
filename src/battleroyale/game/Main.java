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

        List<GameCharacter> availableChars;
        List<Tool> availableTools;
        try {
            availableChars = Config.loadCharacters("characters.txt");
            availableTools = Config.loadTools("tools.txt");
        } catch (IOException e) {
            System.out.println("Failed to load files, using defaults");
            availableChars = Arrays.asList(new Warrior("W1"), new Mage("M1"), new Rogue("R1"));
            availableTools = Arrays.asList(new Sword(), new Shield(), new Staff());
        }

        if (availableChars.size() < human + machine || availableTools.size() < human + machine) {
            System.out.println("Not enough resources to start game");
            return;
        }

        BattleRoyaleGame game = new BattleRoyaleGame(diff);

        for (int i = 0; i < human; i++) {
            GameCharacter c = chooseCharacter(sc, availableChars);
            Tool t = chooseTool(sc, availableTools);
            game.addPlayer(c);
            game.assignTool(c, t);
        }
        Random rand = new Random();
        for (int i = 0; i < machine; i++) {
            GameCharacter c = availableChars.remove(rand.nextInt(availableChars.size()));
            Tool t = availableTools.remove(rand.nextInt(availableTools.size()));
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

    private static GameCharacter chooseCharacter(Scanner sc, List<GameCharacter> list) {
        while (true) {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + 1 + ": " + list.get(i));
            }
            System.out.print("Choose character: ");
            int opt = Integer.parseInt(sc.nextLine()) - 1;
            if (opt >= 0 && opt < list.size()) {
                return list.remove(opt);
            }
        }
    }

    private static Tool chooseTool(Scanner sc, List<Tool> list) {
        while (true) {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + 1 + ": " + list.get(i));
            }
            System.out.print("Choose tool: ");
            int opt = Integer.parseInt(sc.nextLine()) - 1;
            if (opt >= 0 && opt < list.size()) {
                return list.remove(opt);
            }
        }
    }
}