package persistence;

import model.Hero;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Code template taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo"
public class JsonTest {
    protected void checkHero(String name, String type, Hero hero) {
        assertEquals(name, hero.getName());
        assertEquals(type, hero.getHeroType());
    }
}
