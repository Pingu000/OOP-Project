package battleroyale.tools;

import battleroyale.characters.GameCharacter;

public class Staff extends Tool {
    public Staff() {
        super("Staff", 4);
    }

    @Override
    public boolean checkModification(GameCharacter character) {
        // only mage can use effectively, but any character may hold
        return true;
    }

    @Override
    public void modify(GameCharacter character) {
        try {
            java.lang.reflect.Field field = character.getClass().getSuperclass().getDeclaredField("attack");
            field.setAccessible(true);
            field.setInt(character, field.getInt(character) + bonus);
        } catch (Exception e) {
        }
    }
}
