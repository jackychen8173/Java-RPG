package persistence;

import model.Hero;
import model.HeroMelee;
import model.HeroRanged;
import model.Party;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads party from JSON data stored in file
// Code template taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo"
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads party from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Party read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseHeroList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses party from JSON object and returns it
    private Party parseHeroList(JSONObject jsonObject) {
        Party party = new Party();
        addHeroes(party, jsonObject);
        return party;
    }

    // MODIFIES: party
    // EFFECTS: parses heroes from JSON object and adds them to party
    private void addHeroes(Party party, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Heroes");
        for (Object json : jsonArray) {
            JSONObject nextHero = (JSONObject) json;
            addHero(party, nextHero);
        }
    }

    // MODIFIES: party
    // EFFECTS: parses hero from JSON object and adds it to party
    private void addHero(Party party, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String type = jsonObject.getString("type");
        int level = jsonObject.getInt("level");
        int attack = jsonObject.getInt("attack");
        int totalHP = jsonObject.getInt("totalHP");
        int experience = jsonObject.getInt("experience");
        int experienceRequired = jsonObject.getInt("experienceRequired");

        Hero hero;
        if (type.equals("Melee")) {
            hero = new HeroMelee(name, type);
        } else {
            hero = new HeroRanged(name, type);
        }
        hero.setLevel(level);
        hero.setAttack(attack);
        hero.setTotalHP(totalHP);
        hero.setCurrentHP(totalHP);
        hero.setExperience(experience);
        hero.setExperienceRequired(experienceRequired);

        party.addHeroToList(hero);
    }


}
