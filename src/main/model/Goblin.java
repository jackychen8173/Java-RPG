package model;

//represents a goblin having a name, level, hp, and attack
public class Goblin extends Monster {

    private static int defaultLevel = 1;
    private static int defaultHP = 20;
    private static int defaultAttack = 5;

    private static int secondStageLevel = 3;
    private static int secondStageHP = 30;
    private static int secondStageAttack = 7;


    //EFFECTS: name is assigned as Goblin, level, hp, and attack are set to default amounts
    public Goblin() {
        name = "Goblin";
        level = defaultLevel;
        totalHP = defaultHP;
        currentHP = totalHP;
        attack = defaultAttack;
    }

    //MODIFIES: this
    //EFFECTS: goblin's level, totalHP, and attack are set to a determined amount
    public void setStageTwo() {
        level = secondStageLevel;
        totalHP = secondStageHP;
        currentHP = totalHP;
        attack = secondStageAttack;
    }


}
