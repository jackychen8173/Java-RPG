package model;

//represents an orc with a level, hp, and attack
public class Orc extends Monster {

    private static int defaultLevel = 5;
    private static int defaultHP = 50;
    private static int defaultAttack = 15;

    //EFFECTS: name is assigned as Orc, level, hp, and attack are set to default amounts
    public Orc() {
        name = "Orc";
        level = defaultLevel;
        totalHP = defaultHP;
        currentHP = totalHP;
        attack = defaultAttack;
    }
}
