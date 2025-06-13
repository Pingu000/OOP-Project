package battleroyale.tools;

import battleroyale.characters.GameCharacter;

public abstract class Tool implements Modifiable {
    protected String name;
    protected int bonus;

    public Tool(String name, int bonus) {
        this.name = name;
        this.bonus = bonus;
    }

    public String getName() {
        return name;
    }

    public int getBonus() {
        return bonus;
    }

    @Override
    public String toString() {
        return name + "(bonus:" + bonus + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tool tool = (Tool) obj;
        return name.equals(tool.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
