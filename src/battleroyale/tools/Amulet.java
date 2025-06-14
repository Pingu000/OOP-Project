package battleroyale.tools;

import battleroyale.characters.GameCharacter;

public class Amulet extends Tool {
    public Amulet() {
        super("Amulet", 20);
    }

    @Override
    public boolean checkModification(GameCharacter character) {
        return true;
    }

    @Override
    public void modify(GameCharacter character) {
        character.heal(bonus);
    }
}
