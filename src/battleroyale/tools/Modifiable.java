package battleroyale.tools;

import battleroyale.characters.GameCharacter;

public interface Modifiable {
    boolean checkModification(GameCharacter character);
    void modify(GameCharacter character);
}