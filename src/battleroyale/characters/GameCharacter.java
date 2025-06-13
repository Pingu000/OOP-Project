package battleroyale.characters;

public abstract class GameCharacter implements Comparable<GameCharacter> {
    protected String name;
    protected int health;
    protected int attack;
    protected int defense;

    public GameCharacter(String name, int health, int attack, int defense) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void receiveDamage(int dmg) {
        int actual = dmg - defense;
        if (actual < 0) actual = 0;
        health -= actual;
    }

    public void heal(int amount) {
        health += amount;
    }

    public void attack(GameCharacter target) {
        target.receiveDamage(attack);
    }

    @Override
    public String toString() {
        return String.format("%s (HP:%d ATK:%d DEF:%d)", name, health, attack, defense);
    }

    @Override
    public int compareTo(GameCharacter o) {
        return Integer.compare(this.health, o.health);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GameCharacter c = (GameCharacter) obj;
        return name.equals(c.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
