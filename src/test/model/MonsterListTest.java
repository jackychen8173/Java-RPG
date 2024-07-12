package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MonsterListTest {

    MonsterList ml;

    @BeforeEach
    public void setup() {
        ml = new MonsterList();
    }

    @Test
    public void testStageOne() {
        ml.setStageOne();
        assertEquals(3, ml.monsterList.size());
        assertEquals("Goblin", ml.monsterList.get(0).getName());
        assertEquals("Goblin", ml.monsterList.get(1).getName());
        assertEquals("Goblin", ml.monsterList.get(2).getName());
        assertEquals(20, ml.monsterList.get(0).getTotalHP());
        assertEquals(20, ml.monsterList.get(1).getTotalHP());
        assertEquals(20, ml.monsterList.get(2).getTotalHP());
        ml.resetMonsters();
    }

    @Test
    public void testStageTwo() {
        ml.setStageTwo();
        assertEquals(3, ml.monsterList.size());
        assertEquals("Goblin", ml.monsterList.get(0).getName());
        assertEquals("Goblin", ml.monsterList.get(1).getName());
        assertEquals("Goblin", ml.monsterList.get(2).getName());
        assertEquals(30, ml.monsterList.get(0).getTotalHP());
        assertEquals(30, ml.monsterList.get(1).getTotalHP());
        assertEquals(30, ml.monsterList.get(2).getTotalHP());
        ml.resetMonsters();
    }

    @Test
    public void testStageThree() {
        ml.setStageThree();
        assertEquals(3, ml.monsterList.size());
        assertEquals("Orc", ml.monsterList.get(0).getName());
        assertEquals("Goblin", ml.monsterList.get(1).getName());
        assertEquals("Goblin", ml.monsterList.get(2).getName());
        assertEquals(50, ml.monsterList.get(0).getTotalHP());
        assertEquals(30, ml.monsterList.get(1).getTotalHP());
        assertEquals(30, ml.monsterList.get(2).getTotalHP());
        ml.resetMonsters();
    }

    @Test
    public void testStageFour() {
        ml.setStageFour();
        assertEquals(3, ml.monsterList.size());
        assertEquals("Orc", ml.monsterList.get(0).getName());
        assertEquals("Orc", ml.monsterList.get(1).getName());
        assertEquals("Orc", ml.monsterList.get(2).getName());
        assertEquals(50, ml.monsterList.get(0).getTotalHP());
        assertEquals(50, ml.monsterList.get(1).getTotalHP());
        assertEquals(50, ml.monsterList.get(2).getTotalHP());
        ml.resetMonsters();
    }

    @Test
    public void testStageFive() {
        ml.setStageFive();
        assertEquals(1, ml.monsterList.size());
        assertEquals("Dragon", ml.monsterList.get(0).getName());
        assertEquals(200, ml.monsterList.get(0).getTotalHP());
        ml.resetMonsters();
    }

    @Test
    public void testAllDefeatedTrue() {
        ml.setStageOne();
        ml.monsterList.get(0).setCurrentHP(0);
        ml.monsterList.get(1).setCurrentHP(0);
        ml.monsterList.get(2).setCurrentHP(-2);
        assertTrue(ml.allDefeated());
        ml.resetMonsters();
    }

    @Test
    public void testAllDefeatedFalse() {
        ml.setStageOne();
        ml.monsterList.get(0).setCurrentHP(10);
        ml.monsterList.get(1).setCurrentHP(10);
        ml.monsterList.get(2).setCurrentHP(1);
        assertFalse(ml.allDefeated());
        ml.monsterList.get(0).setCurrentHP(10);
        ml.monsterList.get(1).setCurrentHP(0);
        ml.monsterList.get(2).setCurrentHP(1);
        assertFalse(ml.allDefeated());
        ml.monsterList.get(0).setCurrentHP(0);
        ml.monsterList.get(1).setCurrentHP(0);
        ml.monsterList.get(2).setCurrentHP(1);
        assertFalse(ml.allDefeated());
        ml.resetMonsters();
    }

    @Test
    public void testResetMonsters() {
        ml.setStageOne();
        ml.resetMonsters();
        assertEquals(0, ml.monsterList.size());
    }
}
