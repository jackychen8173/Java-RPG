package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroTest {

    private Hero testHero;

    @Test
    public void testConstructorMelee() {

        testHero = new HeroMelee("Hero", "Melee");
        assertEquals ("Hero", testHero.getName());
        assertEquals("Melee", testHero.getHeroType());
        assertEquals(1, testHero.getLevel());
        assertEquals(20, testHero.getTotalHP());
        assertEquals(5, testHero.getAttack());
        assertEquals(0, testHero.getExperience());
        assertEquals(10, testHero.getExperienceRequired());
    }


    @Test
    public void testConstructorRanged() {

        testHero = new HeroRanged("Hero", "Ranged");
        assertEquals("Hero", testHero.getName());
        assertEquals("Ranged", testHero.getHeroType());
    }

    @Test
    public void testGainExperienceNoLevelUpMelee() {

        testHero = new HeroMelee("Hero", "a");
        testHero.gainExperience(5);
        assertEquals(5,testHero.getExperience());
        assertEquals(10, testHero.getExperienceRequired());
        assertEquals(1, testHero.getLevel());
    }

    @Test
    public void testGainExperienceLevelUpOnceMelee() {
        testHero = new HeroMelee("Hero", "a");
        testHero.gainExperience(10);
        assertEquals(0, testHero.getExperience());
        assertEquals(30, testHero.getExperienceRequired());
        assertEquals(2, testHero.getLevel());
    }

    @Test
    public void testGainExperienceManyLevelsMelee() {
        testHero = new HeroMelee("Hero", "a");
        testHero.gainExperience(50);
        assertEquals(10, testHero.getExperience());
        assertEquals(50, testHero.getExperienceRequired());
        assertEquals(3, testHero.getLevel());
    }

    @Test
    public void testGainExperienceNoLevelUpRanged() {

        testHero = new HeroRanged("Hero", "a");
        testHero.gainExperience(5);
        assertEquals(5,testHero.getExperience());
        assertEquals(10, testHero.getExperienceRequired());
        assertEquals(1, testHero.getLevel());
    }

    @Test
    public void testGainExperienceLevelUpOnceRanged() {
        testHero = new HeroRanged("Hero", "a");
        testHero.gainExperience(10);
        assertEquals(0, testHero.getExperience());
        assertEquals(30, testHero.getExperienceRequired());
        assertEquals(2, testHero.getLevel());
    }

    @Test
    public void testGainExperienceManyLevelsRanged() {
        testHero = new HeroRanged("Hero", "a");
        testHero.gainExperience(50);
        assertEquals(10, testHero.getExperience());
        assertEquals(50, testHero.getExperienceRequired());
        assertEquals(3, testHero.getLevel());
    }

    @Test
    public void testDefeatedTrue() {
        testHero = new HeroMelee("Hero", "Melee");
        testHero.setCurrentHP(0);
        assertTrue(testHero.defeated());
        assertEquals(0, testHero.getCurrentHP());
    }

    @Test
    public void testDefeatedFalse() {
        testHero = new HeroMelee("Hero", "Melee");
        testHero.setCurrentHP(1);
        assertFalse(testHero.defeated());
        assertEquals(1, testHero.getCurrentHP());
    }

    @Test
    public void testSetValues() {
        testHero = new HeroMelee("Hero", "Melee");
        testHero.setLevel(2);
        assertEquals(2, testHero.getLevel());
        testHero.setTotalHP(1);
        assertEquals(1, testHero.getTotalHP());
        testHero.setAttack(1);
        assertEquals(1, testHero.getAttack());
        testHero.setExperience(1);
        assertEquals(1, testHero.getExperience());
        testHero.setExperienceRequired(1);
        assertEquals(1, testHero.getExperienceRequired());
    }
}