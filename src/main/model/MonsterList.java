package model;

import java.util.LinkedList;

//Represents a list of monsters
public class MonsterList {

    public LinkedList<Monster> monsterList;

    //EFFECTS: creates a new list of monsters
    public MonsterList() {

        monsterList = new LinkedList<Monster>();

    }

    //MODIFIES: this
    //EFFECTS: adds three goblins to the monster list
    public void setStageOne() {
        Goblin g1 = new Goblin();
        Goblin g2 = new Goblin();
        Goblin g3 = new Goblin();
        monsterList.add(g1);
        monsterList.add(g2);
        monsterList.add(g3);
    }

    //MODIFIES: this
    //EFFECTS: adds three goblins set to stage two to the monster list
    public void setStageTwo() {
        Goblin g1 = new Goblin();
        Goblin g2 = new Goblin();
        Goblin g3 = new Goblin();
        g1.setStageTwo();
        g2.setStageTwo();
        g3.setStageTwo();

        monsterList.add(g1);
        monsterList.add(g2);
        monsterList.add(g3);
    }

    //MODIFIES: this
    //EFFECTS: adds two goblins set to stage two and one orc to the monster list
    public void setStageThree() {
        Goblin g1 = new Goblin();
        Goblin g2 = new Goblin();
        g1.setStageTwo();
        g2.setStageTwo();
        Orc o1 = new Orc();

        monsterList.add(o1);
        monsterList.add(g1);
        monsterList.add(g2);
    }

    //MODIFIES: this
    //EFFECTS: adds three orcs to the monster list
    public void setStageFour() {
        Orc o1 = new Orc();
        Orc o2 = new Orc();
        Orc o3 = new Orc();

        monsterList.add(o1);
        monsterList.add(o2);
        monsterList.add(o3);
    }

    //MODIFIES: this
    //EFFECTS: adds one dragon to the monster list
    public void setStageFive() {
        Dragon d1 = new Dragon();

        monsterList.add(d1);
    }

    //EFFECTS: returns true if all monsters in monsterList is <= 0, false otherwise
    public boolean allDefeated() {

        int tracker = 0;
        for (Monster m: monsterList) {
            if (m.getCurrentHP() <= 0) {
                tracker++;
                if (tracker == monsterList.size()) {
                    return true;
                }
            }
        }

        return false;
    }

    //MODIFIES: this
    //EFFECTS: removes all the monsters in the monsterList
    public void resetMonsters() {

        monsterList.clear();
    }

}
