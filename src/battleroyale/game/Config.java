package battleroyale.game;

import battleroyale.characters.*;
import battleroyale.tools.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Config {
    public static List<GameCharacter> loadCharacters(String file) throws IOException {
        List<GameCharacter> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0].trim();
                String name = parts[1].trim();
                switch (type.toLowerCase()) {
                    case "warrior": list.add(new Warrior(name)); break;
                    case "mage": list.add(new Mage(name)); break;
                    case "rogue": list.add(new Rogue(name)); break;
                }
            }
        }
        return list;
    }

    public static List<Tool> loadTools(String file) throws IOException {
        List<Tool> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String type = line.trim().toLowerCase();
                switch (type) {
                    case "sword": list.add(new Sword()); break;
                    case "shield": list.add(new Shield()); break;
                    case "staff": list.add(new Staff()); break;
                }
            }
        }
        return list;
    }
}
