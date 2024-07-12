package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MonsterTest {

    @Test
    void testGoblinStageOne() {
        Goblin g = new Goblin();
        assertEquals("Goblin", g.getName());
        assertEquals(1, g.getLevel());
        assertEquals(20, g.getTotalHP());
        assertEquals(20, g.getCurrentHP());
        assertEquals(5, g.getAttack());
    }

    @Test
    void testGoblinStageTwo() {
        Goblin g = new Goblin();
        g.setStageTwo();
        assertEquals("Goblin", g.getName());
        assertEquals(3, g.getLevel());
        assertEquals(30, g.getTotalHP());
        assertEquals(30, g.getCurrentHP());
        assertEquals(7, g.getAttack());
    }

    @Test
    void testOrc() {
        Orc o = new Orc();
        assertEquals("Orc", o.getName());
        assertEquals(5, o.getLevel());
        assertEquals(50, o.getTotalHP());
        assertEquals(50, o.getCurrentHP());
        assertEquals(15, o.getAttack());
    }

    @Test
    void testDragon() {
        Dragon d = new Dragon();
        assertEquals("Dragon", d.getName());
        assertEquals(20, d.getLevel());
        assertEquals(200, d.getTotalHP());
        assertEquals(200, d.getCurrentHP());
        assertEquals(50, d.getAttack());
    }

    @Test
    void testDefeatedTrue() {
        Monster d = new Dragon();
        d.setCurrentHP(0);
        assertTrue(d.defeated());
    }

    @Test
    void testDefeatedFalse() {
        Monster d = new Dragon();
        d.setCurrentHP(1);
        assertFalse(d.defeated());
    }
}
