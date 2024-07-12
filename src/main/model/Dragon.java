package model;

//represents a dragon with a level, hp, and attack
public class Dragon extends Monster {

    private static int defaultLevel = 20;
    private static int defaultHP = 200;
    private static int defaultAttack = 50;

    //EFFECTS: name is assigned as Dragon, level, hp, and attack are set to default amounts
    public Dragon() {
        name = "Dragon";
        level = defaultLevel;
        totalHP = defaultHP;
        currentHP = totalHP;
        attack = defaultAttack;
    }
}
