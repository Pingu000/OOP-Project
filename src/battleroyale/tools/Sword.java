package battleroyale.tools;

import battleroyale.characters.GameCharacter;

public class Sword extends Tool {
    public Sword() {
        super("Sword", 5);
    }

    @Override
    public boolean checkModification(GameCharacter character) {
        return true; // any character can use
    }

    @Override
    public void modify(GameCharacter character) {
        try {
            java.lang.reflect.Field field = character.getClass().getSuperclass().getDeclaredField("attack");
            field.setAccessible(true);
            field.setInt(character, field.getInt(character) + bonus);
        } catch (Exception e) {
            // ignored
        }
    }
}