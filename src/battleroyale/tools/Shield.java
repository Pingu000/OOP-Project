package battleroyale.tools;

import battleroyale.characters.GameCharacter;

public class Shield extends Tool {
    public Shield() {
        super("Shield", 3);
    }

    @Override
    public boolean checkModification(GameCharacter character) {
        return true;
    }

    @Override
    public void modify(GameCharacter character) {
        try {
            java.lang.reflect.Field field = character.getClass().getSuperclass().getDeclaredField("defense");
            field.setAccessible(true);
            field.setInt(character, field.getInt(character) + bonus);
        } catch (Exception e) {
        }
    }
}
