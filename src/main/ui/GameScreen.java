package ui;

import exceptions.HeroAbsentException;
import exceptions.HeroDuplicateException;
import exceptions.PartyFullException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GameScreen {

    private static final String JSON_STORE = "./data/Party.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Scanner input;
    private Party party;
    private Random random;
    private int stage = 1;
    private MonsterList monsters = new MonsterList();

    // Method taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo"
    // EFFECTS: constructs party and runs application
    public GameScreen() throws FileNotFoundException {

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runGame() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nHumanity must be saved another time...");
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doViewParty();
        } else if (command.equals("b")) {
            doTraining();
        } else if (command.equals("c")) {
            doBattle();
        } else if (command.equals("s")) {
            saveParty();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    //EFFECTS: initializes input, party, and random, goes to FirstEvent
    public void init() {

        input = new Scanner(System.in);
        party = new Party();
        random = new Random();
        firstEvent();

    }

    // Method taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo"
    // EFFECTS: saves party to file
    private void saveParty() {
        try {
            jsonWriter.open();
            jsonWriter.write(party);
            jsonWriter.close();
            System.out.println("Saved Heroes");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file");
        }
        System.out.println();
    }

    // Method taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo"
    // MODIFIES: this
    // EFFECTS: loads party from file
    private void loadParty() {
        try {
            party = jsonReader.read();
            System.out.println("Loaded Heroes");
        } catch (IOException e) {
            System.out.println("Unable to read from file");
        }
        System.out.println();
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("Options:");
        System.out.println("a -> Party Options");
        System.out.println("b -> Training");
        System.out.println("c -> Battle");
        System.out.println("s -> Save Heroes");
        System.out.println("q -> Quit Game");
    }

    //EFFECTS: story text is printed out, if answer is a goes to loadParty, else goes to createFirstCharacter
    public void firstEvent() {

        System.out.println("War has been erupting for ages between humans and monsters.");
        delay();
        System.out.println("After many years, humanity has been forced to the brink of decimation.");
        delay();
        System.out.println("Pushed to the edge, humanity will fight back with its strongest forces.");
        delay();
        System.out.println("All hope is on these new heroes to save the world.");
        System.out.println();
        System.out.println("Options:");
        System.out.println("a -> Load Heroes");
        System.out.println("b -> Create Hero");
        System.out.println();
        String answer = input.next();

        if (answer.equals("a")) {
            loadParty();
        } else {
            createFirstCharacter();
        }

    }

    //MODIFIES: this
    //EFFECTS: creates a hero with name and either type melee or ranged, adds hero to party and heroList
    public void createFirstCharacter() {
        System.out.println("The new hero, what shall the name be?");
        String name = input.next();

        System.out.println("Choose hero type:");
        System.out.println("a. Melee");
        System.out.println("b. Ranged");
        String heroClass = input.next();
        Hero hero;

        if (heroClass.equals("a")) {
            hero = new HeroMelee(name, "Melee");
        } else {
            hero = new HeroRanged(name, "Ranged");
        }

        party.addHeroToList(hero);
        try {
            party.addHeroToParty(hero);
        } catch (PartyFullException e) {
            System.out.println();
        } catch (HeroDuplicateException e) {
            System.out.println();
        }
        System.out.println();
    }


    //EFFECTS: prints out the party screen and processes user command on where to go next
    public void doViewParty() {

        System.out.println("Party Options: ");
        System.out.println("a-> View Party Heroes Stats");
        System.out.println("b-> Add Hero to Party");
        System.out.println("c-> Remove Hero from Party");
        System.out.println("d-> Recruit Hero");
        System.out.println("e-> View All Heroes");
        System.out.println("Any other key-> Return to Menu");
        String partyCommand = input.next();
        processPartyCommand(partyCommand);
        System.out.println();

    }

    // MODIFIES: this
    // EFFECTS: processes user command in party
    private void processPartyCommand(String command) {

        if (command.equals("a")) {
            doViewHeroesStats();
        } else if (command.equals("b")) {
            doAddHeroToParty();
        } else if (command.equals("c")) {
            doRemoveHeroFromParty();
        } else if (command.equals("d")) {
            doAddHeroToHeroList();
        } else if (command.equals("e")) {
            doViewAllHeroes();
        }
    }

    //EFFECTS: prints out the stats of all the heroes in the party
    public void doViewHeroesStats() {

        System.out.println("Stats for Heroes in Party:");
        System.out.println();
        for (Hero hero : party.activeParty) {

            System.out.println("Name: " + hero.getName());
            System.out.println("Type: " + hero.getHeroType());
            System.out.println("Level: " + hero.getLevel());
            System.out.println("HP: " + hero.getTotalHP());
            System.out.println("Attack: " + hero.getAttack());
            System.out.println("Experience at Current Level: " + hero.getExperience());
            System.out.println("Experience Required for Next Level: " + hero.getExperienceRequired());
            System.out.println();

        }
    }

    //MODIFIES: this
    //EFFECTS: goes through all the heroes and asks if user wants to add to party, and adds hero to party
    public void doAddHeroToParty() {

        String answer;

        if (party.isPartyFull()) {
            System.out.println("Sorry, party is full!");
        }

        for (Hero hero : party.heroList) {
            if ((party.activeParty.contains(hero))) {
                System.out.println();
            } else if (party.isPartyFull()) {
                System.out.println();
            } else {
                System.out.println("Would you like to add " + hero.getName() + " to the party?");
                System.out.println("y-> yes");
                System.out.println("n-> no");
                answer = input.next();

                if (answer.equals("y")) {
                    party.activeParty.add(hero);
                    System.out.println("Hero has been added.");
                }


            }
        }

    }

    //MODIFIES: this
    //EFFECTS: prints out every hero name in party and if user inputs hero name the hero is removed from party
    public void doRemoveHeroFromParty() {

        String answer;

        if (party.isPartyEmpty()) {
            System.out.println("Sorry, party is empty.");
        } else {

            System.out.println("Who would you like to remove? (Enter hero name)");
            for (Hero h : party.activeParty) {
                System.out.println(h.getName());
            }
            answer = input.next();

            for (Hero h : party.activeParty) {
                if (answer.equals(h.getName())) {
                    try {
                        party.removeHeroFromParty(h);
                        System.out.println(h.getName() + " has been removed");
                    } catch (HeroAbsentException e) {
                        System.out.println("Error, hero was not removed");
                    }
                }
            }
            System.out.println();
        }

    }

    //MODIFIES: this
    //EFFECTS: creates new hero with name and type melee or ranged
    public void doAddHeroToHeroList() {

        System.out.println("Would you like to recruit another hero?");
        System.out.println("y -> yes");
        System.out.println("n -> no");

        String answer = input.next();
        if (answer.equals("y")) {
            System.out.println("Choose your hero name:");
            String name = input.next();
            System.out.println("Choose your hero's class");
            System.out.println("a. Melee ");
            System.out.println("b. Ranged");
            String heroClass = input.next();
            Hero hero;

            if (heroClass.equals("a")) {
                hero = new HeroMelee(name, "Melee");
            } else {
                hero = new HeroRanged(name, "Ranged");
            }

            party.addHeroToList(hero);

            System.out.println("Hero has been added");
        }
        System.out.println();

    }

    //EFFECTS: prints out all heroes and their level
    public void doViewAllHeroes() {
        System.out.println("All Characters:");

        for (Hero hero : party.heroList) {
            System.out.println(hero.getName() + "  Level: " + hero.getLevel());
            System.out.println();
        }
    }


    //EFFECTS: prints training screen
    public void doTraining() {

        System.out.println("Party Options: ");
        System.out.println("a-> Train Hero");
        System.out.println("any other key-> Quit to Menu");
        String trainingCommand = input.next();
        processTrainingCommand(trainingCommand);
        System.out.println();
    }


    // MODIFIES: this
    // EFFECTS: processes user command in training
    private void processTrainingCommand(String command) {

        if (command.equals("a")) {
            trainHeroes();
        }
    }

    //MODIFIES: this
    //EFFECTS: user selects a hero in party and the hero gains a random amount of experience,
    //         may print level up
    public void trainHeroes() {

        String answer;
        int experience;

        if (party.isPartyEmpty()) {
            System.out.println("Party is empty, there is no one to train.");
        } else {
            System.out.println("Who would you like to train? (Enter hero name)");
            for (Hero h : party.activeParty) {
                System.out.println(h.getName());
            }
            answer = input.next();

            for (Hero h : party.activeParty) {
                if (answer.equals(h.getName())) {
                    experience = random.nextInt(10) + 5;
                    System.out.println(h.getName() + " got " + experience + " experience!");

                    if (h.gainExperience(experience)) {
                        System.out.println(h.getName() + " leveled up!");
                        System.out.println();
                    }
                }
            }

        }
    }


    //EFFECTS: prints battle screen
    public void doBattle() {

        System.out.println("Battle Options: ");
        System.out.println("a -> Battle Monsters");
        System.out.println("any other key -> Return to Menu");
        String battleCommand = input.next();
        processBattleCommand(battleCommand);
        System.out.println();
    }

    // MODIFIES: this
    // EFFECTS: processes user command in battle
    private void processBattleCommand(String command) {

        if (command.equals("a")) {
            doBattleScreen();
        }
    }

    //MODIFIES: this
    //EFFECTS: checks if party is empty or stage is greater than 5,
    //         resets hero health and monsters, goes to battle
    private void doBattleScreen() {

        if (party.isPartyEmpty()) {
            System.out.println("Sorry, your party is empty and cannot go into battle.");
        } else if (stage > 5) {
            System.out.println("You've Won!");
            System.out.println("That seems to have been the last of the monsters... for now");
            System.out.println("Thanks for playing!");
        } else {
            selectStage();
            System.out.println();
            battle();
            party.resetHP();
            monsters.resetMonsters();
        }

    }

    //MODIFIES: this
    //EFFECTS: sets the monsters depending on which stage it is
    private void selectStage() {
        delay();
        if (stage == 1) {
            System.out.println("Stage 1: Goblin Attack");
            monsters.setStageOne();
        } else if (stage == 2) {
            System.out.println("Stage 2: Stronger Goblins");
            monsters.setStageTwo();
        } else if (stage == 3) {
            System.out.println("Stage 3: Orc Discovery");
            monsters.setStageThree();
        } else if (stage == 4) {
            System.out.println("Stage 4: Orc Base");
            monsters.setStageFour();
        } else {
            System.out.println("Final Battle: DRAGON!");
            monsters.setStageFive();
        }
    }

    //EFFECTS: starts battle, prints out the heroes and monsters with their name, health and attack
    private void battle() {

        while (!party.allDefeated() && !monsters.allDefeated()) {
            for (Hero h : party.activeParty) {
                System.out.println(h.getName());
                System.out.println("Level: " + h.getLevel());
                System.out.println("HP: " + h.getCurrentHP());
                System.out.println("Attack: " + h.getAttack());
                System.out.println();
                System.out.println();
            }
            delay();
            for (Monster m : monsters.monsterList) {
                System.out.println(m.getName());
                System.out.println("HP:" + m.getCurrentHP());
                System.out.println("Attack: " + m.getAttack());
                System.out.println();
                System.out.println();
            }
            delay();

            attacker();

        }

    }

    //EFFECTS: randomly selects an attacker
    private void attacker() {

        int attacker;
        attacker = random.nextInt(6) + 1;

        if (attacker == 1) {
            checkAttackFirstMonster();
        } else if (attacker == 2) {
            checkAttackSecondMonster();
        } else if (attacker == 3) {
            checkAttackThirdMonster();
        } else if (attacker == 4) {
            checkAttackFirstHero();
        } else if (attacker == 5) {
            checkAttackSecondHero();
        } else {
            checkAttackThirdHero();
        }
        delay();
    }

    //EFFECTS: checks to see if the first monster is not defeated, performs monsterAttack
    private void checkAttackFirstMonster() {
        if (!monsters.monsterList.get(0).defeated() && monsters.monsterList.get(0) != null) {

            if (!party.activeParty.get(0).defeated() && party.activeParty.get(0) != null) {
                monsterAttack(party.activeParty.get(0), monsters.monsterList.get(0));
            } else if (!party.activeParty.get(1).defeated() && party.activeParty.get(1) != null) {
                monsterAttack(party.activeParty.get(1), monsters.monsterList.get(0));
            } else if (!party.activeParty.get(2).defeated() && party.activeParty.get(2) != null) {
                monsterAttack(party.activeParty.get(2), monsters.monsterList.get(0));
            }

        } else {
            attacker();
        }
    }

    //EFFECTS: checks to see if the second monster is not defeated, performs monsterAttack
    private void checkAttackSecondMonster() {

        if (monsters.monsterList.size() >= 2) {
            if (!monsters.monsterList.get(1).defeated() && monsters.monsterList.get(1) != null) {

                if (!party.activeParty.get(0).defeated() && party.activeParty.get(0) != null) {
                    monsterAttack(party.activeParty.get(0), monsters.monsterList.get(1));
                } else if (!party.activeParty.get(1).defeated() && party.activeParty.get(1) != null) {
                    monsterAttack(party.activeParty.get(1), monsters.monsterList.get(1));
                } else if (!party.activeParty.get(2).defeated() && party.activeParty.get(2) != null) {
                    monsterAttack(party.activeParty.get(2), monsters.monsterList.get(1));
                }

            } else {
                attacker();
            }
        } else {
            attacker();
        }

    }

    //EFFECTS: checks to see if the third monster is not defeated, performs monsterAttack
    private void checkAttackThirdMonster() {
        if (monsters.monsterList.size() == 3) {
            if (!monsters.monsterList.get(2).defeated() && monsters.monsterList.get(2) != null) {

                if (!party.activeParty.get(0).defeated() && party.activeParty.get(0) != null) {
                    monsterAttack(party.activeParty.get(0), monsters.monsterList.get(2));
                } else if (!party.activeParty.get(1).defeated() && party.activeParty.get(1) != null) {
                    monsterAttack(party.activeParty.get(1), monsters.monsterList.get(2));
                } else if (!party.activeParty.get(2).defeated() && party.activeParty.get(2) != null) {
                    monsterAttack(party.activeParty.get(2), monsters.monsterList.get(2));
                }

            } else {
                attacker();
            }
        } else {
            attacker();
        }

    }

    //EFFECTS: checks to see if the first hero is not defeated, performs heroAttack
    private void checkAttackFirstHero() {
        if (party.activeParty.get(0) != null && !party.activeParty.get(0).defeated()) {

            if (!monsters.monsterList.get(0).defeated() && monsters.monsterList.get(0) != null) {
                heroAttack(party.activeParty.get(0), monsters.monsterList.get(0));
            } else if (!monsters.monsterList.get(1).defeated() && monsters.monsterList.get(1) != null) {
                heroAttack(party.activeParty.get(0), monsters.monsterList.get(1));
            } else if (!monsters.monsterList.get(2).defeated() && monsters.monsterList.get(2) != null) {
                heroAttack(party.activeParty.get(0), monsters.monsterList.get(2));
            }

        } else {
            attacker();
        }
    }

    //EFFECTS: checks to see if the second hero is not defeated, performs heroAttack
    private void checkAttackSecondHero() {
        if (party.activeParty.size() >= 2) {
            if (party.activeParty.get(1) != null && !party.activeParty.get(1).defeated()) {

                if (!monsters.monsterList.get(0).defeated() && monsters.monsterList.get(0) != null) {
                    heroAttack(party.activeParty.get(1), monsters.monsterList.get(0));
                } else if (!monsters.monsterList.get(1).defeated() && monsters.monsterList.get(1) != null) {
                    heroAttack(party.activeParty.get(1), monsters.monsterList.get(1));
                } else if (!monsters.monsterList.get(2).defeated() && monsters.monsterList.get(2) != null) {
                    heroAttack(party.activeParty.get(1), monsters.monsterList.get(2));
                }
            } else {
                attacker();
            }
        } else {
            attacker();

        }
    }

    //EFFECTS: checks to see if the third hero is not defeated, performs heroAttack
    private void checkAttackThirdHero() {
        if (party.activeParty.size() == 3) {
            if (party.activeParty.get(2) != null && !party.activeParty.get(2).defeated()) {

                if (!monsters.monsterList.get(0).defeated() && monsters.monsterList.get(0) != null) {
                    heroAttack(party.activeParty.get(2), monsters.monsterList.get(0));
                } else if (!monsters.monsterList.get(1).defeated() && monsters.monsterList.get(1) != null) {
                    heroAttack(party.activeParty.get(2), monsters.monsterList.get(1));
                } else if (!monsters.monsterList.get(2).defeated() && monsters.monsterList.get(2) != null) {
                    heroAttack(party.activeParty.get(2), monsters.monsterList.get(2));
                }
            } else {
                attacker();
            }
        } else {
            attacker();
        }
    }

    //MODIFIES: this
    //EFFECTS: hero h attacks monster m, m loses health, if monster has no health, checks heroWin
    public void heroAttack(Hero h, Monster m) {
        m.setCurrentHP(m.getCurrentHP() - h.getAttack());
        System.out.println(h.getName() + " attacked " + m.getName() + "!");
        System.out.println(m.getName() + " lost " + h.getAttack() + " HP!");

        if (m.defeated()) {
            System.out.println(m.getName() + " cannot fight!");
            m.setCurrentHP(0);
            heroWin();
        }
        System.out.println();

    }

    //MODIFIES: this
    //EFFECTS: monster m attacks hero h, h loses health, if hero has no health, checks monsterWin
    public void monsterAttack(Hero h, Monster m) {

        h.setCurrentHP(h.getCurrentHP() - m.getAttack());
        System.out.println(m.getName() + " attacked " + h.getName() + "!");
        System.out.println(h.getName() + " lost " + m.getAttack() + " HP!");

        if (h.defeated()) {
            System.out.println(h.getName() + " cannot fight!");
            h.setCurrentHP(0);
            monsterWin();
        }
        System.out.println();

    }

    //MODIFIES: this
    //EFFECTS: checks if all monsters are defeated, hero gains experience and stage adds by 1,
    //         do nothing otherwise
    private void heroWin() {
        if (monsters.allDefeated()) {
            System.out.println();
            System.out.println("You have defeated the monsters!");
            System.out.println();
            if (stage == 1) {
                battleExperience(50);
            } else if (stage == 2) {
                battleExperience(70);
            } else if (stage == 3) {
                battleExperience(100);
            } else if (stage == 4) {
                battleExperience(120);
            }
            stage++;
        }
    }

    //EFFECTS: checks if heroes are all defeated, do nothing otherwise
    private void monsterWin() {
        if (party.allDefeated()) {
            System.out.println();
            System.out.println("All your heroes are unable to fight, you retreat in a hurry.");
            System.out.println();
        }
    }

    //MODIFIES: this
    //EFFECTS: gives hero an amount of experience, and prints if hero leveld up
    private void battleExperience(int experience) {
        for (Hero h : party.activeParty) {
            System.out.println(h.getName() + " got " + experience + " experience!");
            if (h.gainExperience(experience)) {
                System.out.println(h.getName() + " leveled up!");
                System.out.println();
            }

        }
    }

    //EFFECTS: adds a delay in printing out the text
    // Method taken from https://stackoverflow.com/questions/19882885/making-text-appear-delayed
    private void delay() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            System.out.println();
        }
    }

}