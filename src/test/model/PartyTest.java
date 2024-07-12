package model;

import exceptions.HeroAbsentException;
import exceptions.HeroDuplicateException;
import exceptions.PartyFullException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PartyTest {

    private Party testParty;
    private Hero hero;

    @Test
    public void testAddHeroToList() {

        testParty = new Party();
        hero = new HeroMelee("Hero", "a");
        testParty.addHeroToList(hero);
        assertEquals(1, testParty.getHeroListSize());
    }


    @Test
    public void testRemoveHeroFromPartyNoException() {
        testParty = new Party();
        hero = new HeroMelee("Hero", "a");
        testParty.activeParty.add(hero);
        assertEquals(1, testParty.getPartySize());
        try {
            testParty.removeHeroFromParty(hero);
        } catch (HeroAbsentException e) {
            fail("Exception was caught");
        }
        assertEquals(0, testParty.getPartySize());
    }

    @Test
    public void testRemoveHeroFromPartyHeroAbsentException() {
        testParty = new Party();
        hero = new HeroMelee("Hero", "Melee");
        Hero hero2 = new HeroMelee("Hero2", "Melee");
        testParty.activeParty.add(hero);
        assertEquals(1, testParty.getPartySize());
        try {
            testParty.removeHeroFromParty(hero2);
            fail("Exception was expected");
        } catch (HeroAbsentException e) {
            //expected
        }
    }

    @Test
    public void testAddHeroToPartyNoException() {
        testParty = new Party();
        assertFalse(testParty.isPartyFull());
        hero = new HeroMelee("Hero", "a");
        try {
            testParty.addHeroToParty(hero);
        } catch (PartyFullException e) {
            fail("PartyFullException was caught");
        } catch (HeroDuplicateException e) {
            fail("HeroDuplicateException was caught");
        }
        assertFalse(testParty.isPartyFull());
    }

    @Test
    public void testAddHeroToPartyPartyFullException() {
        testParty = new Party();
        hero = new HeroMelee("Hero", "a");
        Hero hero2 = new HeroMelee("hero2", "Melee");
        Hero hero3 = new HeroMelee("hero3", "Melee");
        Hero hero4 = new HeroMelee("hero4", "Melee");
        try {
            testParty.addHeroToParty(hero);
            testParty.addHeroToParty(hero2);
            testParty.addHeroToParty(hero3);
            assertTrue(testParty.isPartyFull());
            testParty.addHeroToParty(hero4);
            fail("PartyFullException should have been caught");
        } catch (PartyFullException e) {
            //expected
        } catch (HeroDuplicateException e) {
            fail("HeroDuplicateException was caught");
        }
    }

    @Test
    public void testAddHeroToPartyHeroDuplicateException() {
        testParty = new Party();
        hero = new HeroMelee("Hero", "a");
        try {
            testParty.addHeroToParty(hero);
            testParty.addHeroToParty(hero);
            fail("HeroDuplicateException should have been caught");
        } catch (PartyFullException e) {
            fail("PartyFullException was caught");
        } catch (HeroDuplicateException e) {
            //expected
        }
    }

    @Test
    public void testGetHeroListSize() {
        testParty = new Party();
        hero = new HeroMelee("Hero", "a");
        testParty.addHeroToList(hero);
        assertEquals(1, testParty.getHeroListSize());
    }

    @Test
    public void testGetPartySize() {
        testParty = new Party();
        hero = new HeroMelee("Hero", "a");
        testParty.activeParty.add(hero);
        assertEquals(1, testParty.getPartySize());
    }

    @Test
    public void testPartyEmptyTrue() {
        testParty = new Party();
        assertTrue(testParty.isPartyEmpty());
    }


    @Test
    public void testPartyEmptyFalse() {
        testParty = new Party();
        hero = new HeroMelee("Hero", "a");
        testParty.activeParty.add(hero);
        assertFalse(testParty.isPartyEmpty());
    }

    @Test
    public void testAllDefeatedTrue() {
        testParty = new Party();
        hero = new HeroMelee("Hero", "Melee");
        Hero hero2 = new HeroRanged("Hero2", "Ranged");
        testParty.activeParty.add(hero);
        testParty.activeParty.add(hero2);
        hero.setCurrentHP(0);
        hero2.setCurrentHP(0);
        assertTrue(testParty.allDefeated());
    }

    @Test
    public void testAllDefeatedFalse() {
        testParty = new Party();
        hero = new HeroMelee("Hero", "Melee");
        Hero hero2 = new HeroRanged("Hero2", "Ranged");
        testParty.activeParty.add(hero);
        testParty.activeParty.add(hero2);
        hero.setCurrentHP(1);
        hero2.setCurrentHP(0);
        assertFalse(testParty.allDefeated());
    }

    @Test
    public void testResetHP() {
        testParty = new Party();
        testParty.resetHP();
        hero = new HeroMelee("Hero", "Melee");
        Hero hero2 = new HeroRanged("Hero2", "Ranged");
        Hero hero3 = new HeroRanged("Hero3", "Ranged");
        testParty.activeParty.add(hero);
        testParty.activeParty.add(hero2);
        hero.setCurrentHP(1);
        hero2.setCurrentHP(0);
        testParty.resetHP();
        assertEquals(hero.getCurrentHP(), hero.getTotalHP());
        assertEquals(hero2.getCurrentHP(), hero2.getTotalHP());
        assertEquals(hero3.getCurrentHP(), hero3.getTotalHP());
    }

}
