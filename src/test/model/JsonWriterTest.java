package model;

import model.Hero;
import model.HeroMelee;
import model.HeroRanged;
import model.Party;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonTest;
import persistence.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
// template taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo"
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Party party = new Party();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyParty() {
        try {
            Party party = new Party();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyParty.json");
            writer.open();
            writer.write(party);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyParty.json");
            party = reader.read();
            assertEquals(0, party.getHeroListSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralParty() {
        try {
            Party party = new Party();
            Hero hero1 = new HeroMelee("Hero1", "Melee");
            Hero hero2 = new HeroRanged("Hero2", "Ranged");
            party.addHeroToList(hero1);
            party.addHeroToList(hero2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralParty.json");
            writer.open();
            writer.write(party);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralParty.json");
            party = reader.read();
            List<Hero> heroes = party.heroList;
            assertEquals(2, party.heroList.size());
            checkHero("Hero1", "Melee", heroes.get(0));
            checkHero("Hero2", "Ranged", heroes.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

