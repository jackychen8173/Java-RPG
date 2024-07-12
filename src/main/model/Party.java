package model;

import exceptions.HeroAbsentException;
import exceptions.HeroDuplicateException;
import exceptions.PartyFullException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;

//Represents a party with one list for heroes in the party, one list for all heroes
public class Party implements Writable {

    private static final int MAX_PARTY = 3;

    public LinkedList<Hero> heroList;
    public LinkedList<Hero> activeParty;

    //EFFECTS: creates a new list for heroList and a new list for activeParty
    public Party() {

        heroList = new LinkedList<Hero>();
        activeParty = new LinkedList<Hero>();
    }


    //MODIFIES: this
    //EFFECTS: adds hero to hero list
    public void addHeroToList(Hero hero) {
        heroList.add(hero);
    }


    //MODIFIES: this
    //EFFECTS: adds hero to party
    public boolean addHeroToParty(Hero hero) throws PartyFullException, HeroDuplicateException {

        if (activeParty.size() == MAX_PARTY) {
            throw new PartyFullException();
        }

        if (activeParty.contains(hero)) {
            throw new HeroDuplicateException();
        }
        activeParty.add(hero);
        return true;
    }

    //MODIFIES: this
    //EFFECTS: removes hero from party
    public void removeHeroFromParty(Hero hero) throws HeroAbsentException {
        if (!activeParty.contains(hero)) {
            throw new HeroAbsentException();
        }
        activeParty.remove(hero);
    }


    //EFFECTS: returns number of heroes in the heroes list
    public int getHeroListSize() {
        return heroList.size();
    }

    //EFFECTS: returns number of heroes in the party
    public int getPartySize() {
        return activeParty.size();
    }

    //EFFECTS: returns true if party size is equal to max size (full)
    public boolean isPartyFull() {
        if (activeParty.size() == MAX_PARTY) {
            return true;
        }
        return false;
    }

    //EFFECTS: returns true if party size is equal to zero (empty)
    public boolean isPartyEmpty() {
        if (activeParty.size() == 0) {
            return true;
        }
        return false;
    }

    //EFFECTS: return true if party currentHP for all heroes is <= 0, false otherwise
    public boolean allDefeated() {

        int tracker = 0;
        for (Hero h: activeParty) {
            if (h.getCurrentHP() <= 0) {
                tracker++;
                if (tracker == activeParty.size()) {
                    return true;
                }
            }
        }

        return false;
    }

    //MODIFIES: this
    //EFFECTS: changes all hero's currentHP back to totalHP
    public void resetHP() {

        for (Hero h: activeParty) {
            h.setCurrentHP(h.getTotalHP());
        }
    }

    // Taken from JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Heroes", heroesToJson());
        return json;
    }

    // Method taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo"
    // EFFECTS: returns heroes in this party as a JSON array
    private JSONArray heroesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Hero h : heroList) {
            jsonArray.put(h.toJson());
        }

        return jsonArray;
    }
}
