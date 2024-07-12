package model;

import model.Hero;
import model.Party;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
// template taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo"
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Party party = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyParty() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyParty.json");
        try {
            Party party = reader.read();
            assertEquals(0, party.getHeroListSize());
            assertTrue(party.heroList.isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralParty() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralParty.json");
        try {
            Party party = reader.read();
            List<Hero> heroes = party.heroList;
            assertEquals(2, party.getHeroListSize());
            checkHero("Hero1", "Melee", heroes.get(0));
            checkHero("Hero2", "Ranged", heroes.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

