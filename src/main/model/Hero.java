package model;

import persistence.Writable;

//Represents a hero having a name, level, HP, attack, experience, experience required
public abstract class Hero implements Writable {

    private static int nextCharacterId = 1;
    private static int defaultLevel = 1;
    private static int defaultHP = 20;
    private static int defaultAttack = 5;
    private static int defaultExperience = 0;
    private static int defaultExperienceRequired = 10;

    private static int increaseHpAmount = 10;
    private static int increaseAttackAmount = 20;
    private static int increaseExperienceRequiredAmount = 20;

    protected int id = 1;
    protected String name;
    protected String heroType;
    protected int level;
    protected int totalHP;
    protected int currentHP;
    protected int attack;
    protected int experience;
    protected int experienceRequired;

    /*
     * REQUIRES: heroName has a non-zero length, heroType has a non-zero length
     * EFFECTS: name of character is set to heroName; character id is a
     *          positive integer not assigned to any other account;
     *          the class of the hero depends on the input letter
     *          level, HP, attack, defense, speed, experience and experience required is set to their default amounts
     */
    public Hero(String heroName, String heroType) {

        id = nextCharacterId++;
        name = heroName;
        this.heroType = heroType;

        level = defaultLevel;
        totalHP = defaultHP;
        currentHP = totalHP;
        attack = defaultAttack;
        experience = defaultExperience;
        experienceRequired = defaultExperienceRequired;

    }

    public String getName() {
        return name;
    }

    public String getHeroType() {
        return heroType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTotalHP() {
        return totalHP;
    }

    public void setTotalHP(int totalHP) {
        this.totalHP = totalHP;
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

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getExperienceRequired() {
        return experienceRequired;
    }

    public void setExperienceRequired(int experienceRequired) {
        this.experienceRequired = experienceRequired;
    }

    //MODIFIES: this
    //EFFECTS: add an amount of experience to hero experience
    //         if experience is greater or equal to experienceRequired, increase level by 1,
    //         increase hp and attack by a determined amount,
    //         subtract experience by experienceRequired, and increase experienceRequired by a determined amount
    //         repeat until experience is less than experienceRequired
    public abstract boolean gainExperience(int experience);

    //EFFECTS: returns true if hero's currentHP <= 0, false otherwise
    public boolean defeated() {
        if (currentHP <= 0) {
            return true;
        } else {
            return false;
        }

    }


}
