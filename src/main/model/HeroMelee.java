package model;

import org.json.JSONObject
;

//represents a hero of type melee
public class HeroMelee extends Hero {

    private static final int increaseHpAmount = 10;
    private static int increaseAttackAmount = 5;
    private static int increaseExperienceRequiredAmount = 20;

    public HeroMelee(String heroName, String heroClass) {
        super(heroName, heroClass);
    }

    //MODIFIES: this
    //EFFECTS: add an amount of experience to hero experience
    //         if experience is greater or equal to experienceRequired, increase level by 1,
    //         increase hp and attack by a determined amount,
    //         subtract experience by experienceRequired, and increase experienceRequired by a determined amount
    //         repeat until experience is less than experienceRequired
    @Override
    public boolean gainExperience(int experience) {
        this.experience += experience;
        if (this.experience >= experienceRequired) {
            while (this.experience >= experienceRequired) {
                level++;
                totalHP += increaseHpAmount;
                attack += increaseAttackAmount;

                this.experience -= experienceRequired;
                experienceRequired += increaseExperienceRequiredAmount;
            }
            return true;
        }
        return false;

    }

    // Method taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo"
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("type", heroType);
        json.put("level", level);
        json.put("attack", attack);
        json.put("totalHP", totalHP);
        json.put("experience", experience);
        json.put("experienceRequired", experienceRequired);
        return json;
    }
}
