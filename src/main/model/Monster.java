package model;

//represents a monster having a name, level, HP, and attack
public abstract class Monster {

    protected String name;
    protected int level;
    protected int totalHP;
    protected int currentHP;
    protected int attack;

    public Monster() {

    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getTotalHP() {
        return totalHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public int getAttack() {
        return attack;
    }

    //EFFECTS: returns true if currentHP <= 0, false otherwise
    public boolean defeated() {
        if (currentHP <= 0) {
            return true;
        } else {
            return false;
        }

    }
}
