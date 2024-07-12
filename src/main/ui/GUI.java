package ui;

import exceptions.HeroAbsentException;
import exceptions.HeroDuplicateException;
import exceptions.PartyFullException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

//constructs a new GUI with panel, frame, buttons, etc.
public class GUI {

    protected JFrame frame;
    protected JPanel panel;
    private Party party;
    private Random random;
    private int stage = 1;
    int turn = 1;
    private MonsterList monsters = new MonsterList();
    private String heroClass;
    private JTextField userText;
    private JButton next;
    private JButton backToParty;
    private JButton backToMain;
    private static final String JSON_STORE = "./data/Party.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JLabel display;
    private ImageIcon meleeImage = new ImageIcon("./data/MeleeHero.png");
    private ImageIcon rangedImage = new ImageIcon("./data/RangedHero.png");

    //constructs a GUI with a frame, panel, party, random, jsonreader and jsonwriter,
    // set all text fonts, initialize buttons
    public GUI() throws FileNotFoundException {

        frame = new JFrame();
        panel = new JPanel();

        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        panel.setSize(800, 800);
        frame.add(panel);

        party = new Party();
        random = new Random();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        UIManager.put("Label.font", new FontUIResource(new Font("Serif", Font.PLAIN, 24)));
        UIManager.put("Button.font", new FontUIResource(new Font("Serif", Font.BOLD, 24)));
        UIManager.put("TextField.font", new FontUIResource(new Font("Serif", Font.PLAIN, 24)));
        UIManager.put("TextArea.font", new FontUIResource(new Font("Serif", Font.PLAIN, 24)));

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        next = new JButton("Next");
        backToMain = new JButton("Back");
        backToParty = new JButton("Back");
        display = new JLabel("", SwingConstants.CENTER);
        scaleImages();
        start();
    }

    //MODIFIES: this
    //EFFECTS: scales down meleeImage and rangedImage to be smaller
    public void scaleImages() {
        Image meleeImageScaled = meleeImage.getImage();
        Image newMeleeImage = meleeImageScaled.getScaledInstance(120, 200, java.awt.Image.SCALE_SMOOTH);
        meleeImage = new ImageIcon(newMeleeImage);

        Image rangedImageScaled = rangedImage.getImage();
        Image newRangedImage = rangedImageScaled.getScaledInstance(140, 180, java.awt.Image.SCALE_SMOOTH);
        rangedImage = new ImageIcon(newRangedImage);
    }

    //MODIFIES: this
    //EFFECTS: adds start text and start game button to panel
    public void start() {

        panel.setLayout(new GridLayout(0, 1));
        JLabel startText1 = new JLabel("War has been erupting for ages between humans and monsters.");
        startText1.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel startText2 = new JLabel("After many years, humanity has been forced to the brink of decimation.");
        startText2.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel startText3 = new JLabel("Pushed to the edge, humanity will fight back with its strongest forces.");
        startText3.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel startText4 = new JLabel("All hope is on these new heroes to save the world.");
        startText4.setHorizontalAlignment(SwingConstants.CENTER);

        JButton startGame = new JButton("Start Game");

        addStartToPanel(startText1, startText2, startText3, startText4, startGame);

        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel();
                mainMenu();
                playMusic();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: helper method to add start text to panel
    private void addStartToPanel(JLabel text1, JLabel text2, JLabel text3, JLabel text4, JButton button) {
        panel.add(text1);
        panel.add(text2);
        panel.add(text3);
        panel.add(text4);
        panel.add(button);
    }

    //MODIFIES: this
    //EFFECTS: adds main menu buttons to panel
    public void mainMenu() {

        panel.setLayout(new GridLayout(6, 1));
        JButton partyOptions = new JButton("Party Options");
        JButton trainingOptions = new JButton("Training Options");
        JButton battleOptions = new JButton("Battle Options");
        JButton saveHeroes = new JButton("Save Heroes");
        JButton loadHeroes = new JButton("Load Heroes");

        panel.add(partyOptions);
        panel.add(trainingOptions);
        panel.add(battleOptions);
        panel.add(saveHeroes);
        panel.add(loadHeroes);
        panel.add(display);


        mainToNext(partyOptions, trainingOptions, battleOptions);
        saveHeroes(saveHeroes);
        loadHeroes(loadHeroes);

    }

    //EFFECTS: when button is clicked sends user to next screen
    private void mainToNext(JButton partyOptions, JButton trainingOptions, JButton battleOptions) {
        partyOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel();
                partyMenu();
            }
        });

        trainingOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel();
                trainingMenu();
            }
        });

        battleOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel();
                battleMenu();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: saves party to file
    public void saveHeroes(JButton saveHeroes) {
        saveHeroes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jsonWriter.open();
                    jsonWriter.write(party);
                    jsonWriter.close();
                    display.setText("Heroes saved");
                } catch (FileNotFoundException exception) {
                    display.setText("Error, heroes not saved");
                }
                panel.validate();
            }
        });

    }

    //MODIFIES: this
    //EFFECTS: loads party from file
    public void loadHeroes(JButton loadHeroes) {
        loadHeroes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    party = jsonReader.read();
                    display.setText("Heroes loaded");
                } catch (IOException exception) {
                    display.setText("Error, heroes not loaded");
                }
                panel.validate();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: party screen and buttons are displayed
    public void partyMenu() {
        panel.setLayout(new GridLayout(0, 1));
        JButton viewPartyStats = new JButton("View Party Stats");
        JButton addToParty = new JButton("Add Hero to Party");
        JButton removeFromParty = new JButton("Remove Hero From Party");
        JButton createNewHero = new JButton("Create New Hero");
        JButton viewAllHeroes = new JButton("View All Heroes");
        JButton backToMain = new JButton("Back To Main Menu");

        panel.add(viewPartyStats);
        panel.add(addToParty);
        panel.add(removeFromParty);
        panel.add(createNewHero);
        panel.add(viewAllHeroes);
        panel.add(backToMain);

        partyToPartyOptions(viewPartyStats, addToParty, removeFromParty);
        partyToHeroOptions(createNewHero, viewAllHeroes);
        backToMain(backToMain);
    }

    //EFFECTS: sends to party screen depending on which button is pressed
    private void partyToPartyOptions(JButton viewPartyStats, JButton addToParty, JButton removeFromParty) {
        viewPartyStats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel();
                viewPartyStats();
            }
        });

        addToParty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel();
                addToParty();
            }
        });

        removeFromParty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel();
                removeFromParty();
            }
        });
    }

    //EFFECTS: sends user to party screen depending on which button is pressed
    private void partyToHeroOptions(JButton createNewHero, JButton viewAllHeroes) {
        createNewHero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel();
                createNewHero();
            }
        });

        viewAllHeroes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel();
                viewAllHeroes();
            }
        });
    }

    //EFFECTS: returns user back to main menu
    private void backToMain(JButton backToMain) {
        backToMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel();
                mainMenu();
                party.resetHP();
                monsters.resetMonsters();
                turn = 1;
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: shows all heroes in party and their stats
    public void viewPartyStats() {

        panel.setLayout(new GridLayout(0, 2));
        if (party.isPartyEmpty()) {
            JLabel empty = new JLabel("Error, party is empty");
            panel.add(empty);
        } else {
            for (Hero h : party.activeParty) {
                if (h.getHeroType().equals("Melee")) {
                    JLabel newMeleeHero = new JLabel(meleeImage);
                    panel.add(newMeleeHero);
                } else {
                    JLabel newRangedHero = new JLabel(rangedImage);
                    panel.add(newRangedHero);
                }
                displayHeroStats(h);
            }
        }
        panel.add(backToParty);
        backToParty(backToParty);
        panel.validate();
    }

    //MODIFIES: this
    //EFFECTS: helper method to display user stats
    public void displayHeroStats(Hero h) {
        JTextArea hero = new JTextArea("Name: " + h.getName() + "\nType: " + h.getHeroType() + "\n" + "Level: "
                + h.getLevel() + "\n" + "HP: " + h.getTotalHP() + "\n" + "Attack: " + h.getAttack() + "\n"
                + "Experience at Current Level: " + h.getExperience() + "\n"
                + "Experience Required for Next Level: " + h.getExperienceRequired());
        panel.add(hero);
    }

    //MODIFIES: this
    //EFFECTS: adds hero to party if name is found
    public void addToParty() {

        panel.setLayout(new GridLayout(0, 1));
        if (party.isPartyFull() || party.heroList.isEmpty()) {
            display.setText("Sorry, party is full or there are no heroes to add");
        } else {
            JLabel heroes = new JLabel("Which hero would you like to add?");
            panel.add(heroes);
            JLabel hero;
            for (Hero h : party.heroList) {
                if (!party.activeParty.contains(h)) {
                    hero = new JLabel(h.getName() + " Level: " + h.getLevel());
                    panel.add(hero);
                    panel.validate();
                }
            }
            userText.setText("");
            panel.add(userText);
            JButton addHero = new JButton("Add Hero");
            panel.add(addHero);
            addHero(addHero);

        }
        panel.add(display);
        panel.add(backToParty);
        backToParty(backToParty);

    }

    //MODIFIES: this
    //EFFECTS: checks if party is not full and name is contained and adds hero
    private void addHero(JButton addHero) {
        addHero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Hero h : party.heroList) {
                    if (userText.getText().equals(h.getName())) {
                        if (!party.isPartyFull() && !party.activeParty.contains(h)) {
                            try {
                                party.addHeroToParty(h);
                                display.setText("Hero Added");
                            } catch (PartyFullException partyFullException) {
                                display.setText("Error, party is full");
                            } catch (HeroDuplicateException heroDuplicateException) {
                                display.setText("Error, hero is already in party");
                            }
                        } else {
                            display.setText("Error, hero was not added");
                        }
                        panel.validate();
                    }
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: checks if name is found in party and removes hero
    public void removeFromParty() {

        panel.setLayout(new GridLayout(0, 1));
        if (party.activeParty.isEmpty() || party.heroList.isEmpty()) {
            display.setText("Sorry, party is empty or there are no heroes to add");
        } else {
            JLabel chooseRemoveHero = new JLabel("Which heroes do you want to remove?");
            panel.add(chooseRemoveHero);
            JLabel hero;
            for (Hero h : party.activeParty) {
                hero = new JLabel(h.getName() + " Level: " + h.getLevel());
                panel.add(hero);
                panel.validate();
            }
            userText.setText("");
            panel.add(userText);
            JButton removeHero = new JButton("Remove Hero");
            panel.add(removeHero);
            removeHero(removeHero);

        }
        panel.add(display);
        panel.add(backToParty);
        backToParty(backToParty);

    }

    //MODIFIES: this
    //MODIFIES: helper method to remove hero
    private void removeHero(JButton removeHero) {
        removeHero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Hero h : party.activeParty) {
                    if (userText.getText().equals(h.getName())) {
                        try {
                            party.removeHeroFromParty(h);
                            display.setText("Hero removed");
                        } catch (HeroAbsentException heroAbsentException) {
                            display.setText("Error, hero not found");
                        }
                    }
                    panel.validate();
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: creates new hero
    public void createNewHero() {

        panel.setLayout(new GridLayout(0, 2));
        JLabel heroName = new JLabel("Hero Name");
        heroName.setBounds(10, 20, 80, 25);
        panel.add(heroName);

        userText.setText("");
        panel.add(userText);

        JButton melee = new JButton("Melee");
        JButton ranged = new JButton("Ranged");
        panel.add(melee);
        panel.add(ranged);
        JButton createHero = new JButton("Create Hero");
        panel.add(createHero);
        panel.add(display);
        panel.add(backToParty);
        meleeRangedPressed(melee, ranged);
        createHeroPressed(createHero);
        backToParty(backToParty);

    }

    //MODIFIES: this
    //EFFECTS: sets user text to melee/ranged and display to melee/ranged selected
    private void meleeRangedPressed(JButton melee, JButton ranged) {
        melee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText("Melee Selected");
                panel.validate();
                heroClass = "Melee";
            }
        });

        ranged.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText("Ranged Selected");
                panel.validate();
                heroClass = "Ranged";
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: checks if hero name and type is ranged and creates hero
    private void createHeroPressed(JButton createHero) {
        createHero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!userText.getText().isEmpty() && !userText.equals("") && heroClass.equals("Melee")) {
                    Hero hero = new HeroMelee(userText.getText(), "Melee");
                    party.addHeroToList(hero);
                    display.setText("Hero created ");
                    panel.validate();
                } else if (!userText.getText().isEmpty() && !userText.equals("") && heroClass.equals("Ranged")) {
                    Hero hero = new HeroRanged(userText.getText(), "Ranged");
                    party.addHeroToList(hero);
                    display.setText("Hero created ");
                    panel.validate();
                } else {
                    display.setText("Error, please type in a name and select a class");
                    panel.validate();
                }
            }
        });
    }

    //EFFECTS: sends user back to main party screen
    private void backToParty(JButton back) {
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel();
                partyMenu();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: displays all heroes and their levels
    public void viewAllHeroes() {

        panel.setLayout(new GridLayout(0, 1));
        JTextField allHeroes = new JTextField("All Heroes:", SwingConstants.CENTER);
        panel.add(allHeroes);

        JTextField heroes;
        if (!party.heroList.isEmpty()) {
            for (Hero h : party.heroList) {
                heroes = new JTextField(h.getName() + " Level: " + h.getLevel(), SwingConstants.CENTER);
                panel.add(heroes);
            }
        }
        panel.add(backToParty);
        backToParty(backToParty);
    }

    //MODIFIES: this
    //EFFECTS: shows training menu
    public void trainingMenu() {

        panel.setLayout(new GridLayout(0, 1));
        JButton trainHeroes = new JButton("Train Heroes");
        JButton backToMainMenu = new JButton("Back to Main Menu");

        panel.add(trainHeroes);
        panel.add(backToMainMenu);

        trainHeroes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel();
                trainHeroes();
            }
        });

        backToMain(backToMainMenu);
    }

    //MODIFIES: this
    //EFFECTS: adds experience to the hero selected
    public void trainHeroes() {
        playMusic();
        if (party.activeParty.isEmpty()) {
            display.setText("Sorry, party is empty, there is no one to train");
        } else {
            JLabel chooseTrainHero = new JLabel("Which heroes do you want to train?");
            panel.add(chooseTrainHero);
            JLabel hero;
            for (Hero h : party.activeParty) {
                hero = new JLabel(h.getName() + " Level: " + h.getLevel());
                panel.add(hero);
                panel.validate();
            }
            userText.setText("");
            panel.add(userText);
            JButton trainHero = new JButton("Train Hero");
            panel.add(trainHero);
            trainHero(trainHero);
        }
        panel.add(display);
        panel.add(backToMain);
        backToMain(backToMain);

    }

    //MODIFIES: this
    //EFFECTS: helper method to add experience
    private void trainHero(JButton trainHero) {
        trainHero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int experience = random.nextInt(10) + 5;
                for (Hero h : party.activeParty) {
                    if (userText.getText().equals(h.getName())) {
                        if (h.gainExperience(experience)) {
                            display.setText(h.getName() + " gained " + experience + " experience! "
                                    + h.getName() + " leveled up!");
                        } else {
                            display.setText(h.getName() + " gained " + experience + " experience!");
                        }
                    }
                    panel.validate();
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: shows battle screen
    public void battleMenu() {

        JButton battleMonsters = new JButton("Battle Monsters");
        JButton backToMainMenu = new JButton("Back to Main Menu");

        panel.add(battleMonsters);
        panel.add(backToMainMenu);

        battleMonsters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel();
                playMusic();
                selectStage();
                battle();

            }
        });

        backToMain(backToMainMenu);
    }

    //MODIFIES: this
    //EFFECTS: checks stage and sets the monsters
    private void selectStage() {
        if (stage == 1) {
            display.setText("Stage 1: Goblin Attack");
            monsters.setStageOne();
        } else if (stage == 2) {
            display.setText("Stage 2: Stronger Goblins");
            monsters.setStageTwo();
        } else if (stage == 3) {
            display.setText("Stage 3: Orc Discovery");
            monsters.setStageThree();
        } else if (stage == 4) {
            display.setText("Stage 4: Orc Base");
            monsters.setStageFour();
        } else {
            display.setText("Final Battle: DRAGON!");
            monsters.setStageFive();
        }
    }

    //MODIFIES: this
    //EFFECTS: checks if able to battle, performs attack
    private void battle() {

        if (party.isPartyEmpty()) {
            display.setText("Sorry, your party is empty and cannot go into battle.");
            panel.add(backToMain);
            backToMain(backToMain);
        } else if (stage > 5) {
            display.setText("You've Won! That seems to have been the last of the monsters... "
                    + "for now. Thanks for playing!");
            panel.add(backToMain);
            backToMain(backToMain);
        } else {
            printBattleScreen();
            attack();
        }

        panel.add(display);
        panel.validate();

    }

    //MODIFIES: this
    //EFFECTS: prints heroes and monsters
    public void printBattleScreen() {
        panel.setLayout(new GridLayout(0, 2));
        for (Hero h : party.activeParty) {
            JTextArea hero = new JTextArea(h.getName() + "\n Level: " + h.getLevel()
                    + "\n HP: " + h.getCurrentHP() + "\n Attack: " + h.getAttack());
            panel.add(hero);
        }
        for (Monster m : monsters.monsterList) {
            JTextArea monster = new JTextArea(m.getName() + "\n HP: " + m.getCurrentHP()
                    + "\n Attack: " + m.getAttack());
            panel.add(monster);
        }
        panel.add(display);
        panel.add(next);
        nextPanel(next);
        panel.validate();
    }

    //EFFECTS: checks whose turn it is to attack
    public void attack() {
        if (turn <= 3) {
            heroChooseAttack();
        } else {
            monsterChooseAttack();
        }
        monsterWin();
    }

    //EFFECTS: checks if hero is able to attack, goes to hero attack
    public void heroChooseAttack() {
        if (turn == 1) {
            if (!party.activeParty.get(0).defeated()) {
                firstHeroAttack();
            }
            turn++;
        } else if (turn == 2) {
            if (party.activeParty.size() >= 2) {
                if (!party.activeParty.get(1).defeated()) {
                    secondHeroAttack();
                }
            }
            turn++;
        } else if (turn == 3) {
            if (party.activeParty.size() == 3) {
                if (!party.activeParty.get(2).defeated()) {
                    thirdHeroAttack();
                }
            }
            turn++;
        }
    }

    //EFFECTS: checks if monster is able to attack, goes to monster attack
    public void monsterChooseAttack() {
        if (turn == 4) {
            if (!monsters.monsterList.get(0).defeated()) {
                firstMonsterAttack();
            }
            turn++;
        } else if (turn == 5) {
            if (monsters.monsterList.size() >= 2) {
                if (!monsters.monsterList.get(1).defeated()) {
                    secondMonsterAttack();
                }
            }
            turn++;
        } else {
            if (monsters.monsterList.size() == 3) {
                if (!monsters.monsterList.get(2).defeated()) {
                    thirdMonsterAttack();
                }
            }
            turn = 1;
        }
    }

    //EFFECTS: checks if monster is able to be attacked, goes to hero attack
    public void firstHeroAttack() {
        if (!monsters.monsterList.get(0).defeated()) {
            heroAttack(party.activeParty.get(0), monsters.monsterList.get(0));
        } else if (monsters.monsterList.get(1) != null && !monsters.monsterList.get(1).defeated()) {
            heroAttack(party.activeParty.get(0), monsters.monsterList.get(1));
        } else {
            heroAttack(party.activeParty.get(0), monsters.monsterList.get(2));
        }
    }

    //EFFECTS: checks if monster is able to be attacked, goes to hero attack
    public void secondHeroAttack() {
        if (!monsters.monsterList.get(0).defeated()) {
            heroAttack(party.activeParty.get(1), monsters.monsterList.get(0));
        } else if (monsters.monsterList.get(1) != null && !monsters.monsterList.get(1).defeated()) {
            heroAttack(party.activeParty.get(1), monsters.monsterList.get(1));
        } else {
            heroAttack(party.activeParty.get(1), monsters.monsterList.get(2));
        }
    }

    //EFFECTS: checks if monster is able to be attacked, goes to hero attack
    public void thirdHeroAttack() {
        if (!monsters.monsterList.get(0).defeated()) {
            heroAttack(party.activeParty.get(2), monsters.monsterList.get(0));
        } else if (monsters.monsterList.get(1) != null && !monsters.monsterList.get(1).defeated()) {
            heroAttack(party.activeParty.get(2), monsters.monsterList.get(1));
        } else {
            heroAttack(party.activeParty.get(2), monsters.monsterList.get(2));
        }
    }

    //EFFECTS: checks if hero is able to be attacked, goes to hero attack
    public void firstMonsterAttack() {
        if (!party.activeParty.get(0).defeated()) {
            monsterAttack(party.activeParty.get(0), monsters.monsterList.get(0));
        } else if (party.activeParty.size() >= 2 && !party.activeParty.get(1).defeated()) {
            monsterAttack(party.activeParty.get(1), monsters.monsterList.get(0));
        } else if (party.activeParty.size() == 3 && !party.activeParty.get(2).defeated())  {
            monsterAttack(party.activeParty.get(2), monsters.monsterList.get(0));
        }
    }

    //EFFECTS: checks if hero is able to be attacked, goes to hero attack
    public void secondMonsterAttack() {
        if (!party.activeParty.get(0).defeated()) {
            monsterAttack(party.activeParty.get(0), monsters.monsterList.get(1));
        } else if (party.activeParty.size() >= 2 && !party.activeParty.get(1).defeated()) {
            monsterAttack(party.activeParty.get(1), monsters.monsterList.get(1));
        } else if (party.activeParty.size() == 3 && !party.activeParty.get(2).defeated()) {
            monsterAttack(party.activeParty.get(2), monsters.monsterList.get(1));
        }
    }

    //EFFECTS: checks if hero is able to be attacked, goes to hero attack
    public void thirdMonsterAttack() {
        if (!party.activeParty.get(0).defeated()) {
            monsterAttack(party.activeParty.get(0), monsters.monsterList.get(2));
        } else if (party.activeParty.size() >= 2 && !party.activeParty.get(1).defeated()) {
            monsterAttack(party.activeParty.get(1), monsters.monsterList.get(2));
        } else if (party.activeParty.size() == 3 && !party.activeParty.get(2).defeated())  {
            monsterAttack(party.activeParty.get(2), monsters.monsterList.get(2));
        }
    }

    //MODIFIES: this
    //EFFECTS: performs hero attack
    public void heroAttack(Hero h, Monster m) {
        m.setCurrentHP(m.getCurrentHP() - h.getAttack());

        if (m.defeated()) {
            display.setText(h.getName() + " attacked " + m.getName() + "! "
                    + "\n" + m.getName() + " lost " + h.getAttack() + " HP! "
                    + m.getName() + " cannot fight!");
            m.setCurrentHP(0);
        } else {
            display.setText(h.getName() + " attacked " + m.getName() + "! "
                    + "\n" + m.getName() + " lost " + h.getAttack() + " HP!");
        }
        heroWin();
        panel.validate();

    }

    //MODIFIES: this
    //EFFECTS: performs monster attack
    public void monsterAttack(Hero h, Monster m) {

        h.setCurrentHP(h.getCurrentHP() - m.getAttack());

        if (h.defeated()) {
            display.setText(m.getName() + " attacked " + h.getName() + "! "
                    + h.getName() + " lost " + m.getAttack() + " HP! "
                    + h.getName() + " cannot fight!");
            h.setCurrentHP(0);
            monsterWin();
        } else {
            display.setText(m.getName() + " attacked " + h.getName() + "! "
                    + h.getName() + " lost " + m.getAttack() + " HP!");
        }
        panel.validate();

    }

    //MODIFIES: this
    //EFFECTS: checks if monsters are defeated, adds back to main menu button
    private void heroWin() {
        if (monsters.allDefeated()) {
            if (stage == 1) {
                display.setText("You have defeated the monsters! "
                        + "Each hero has obtained 50 experience!");
                stage++;
            } else if (stage == 2) {
                display.setText("You have defeated the monsters! "
                        + "Each hero has obtained 70 experience!");
                stage++;
            } else if (stage == 3) {
                display.setText("You have defeated the monsters! "
                        + "Each hero has obtained 100 experience!");
                stage++;
            } else if (stage == 4) {
                display.setText("You have defeated the monsters! "
                        + "Each hero has obtained 120 experience!");
                stage++;
            }

            panel.remove(next);
            panel.add(backToMain);
            backToMain(backToMain);
            panel.validate();
        }
    }

    //MODIFIES: this
    //EFFECTS: checks if party is defeated, adds to back to menu button
    private void monsterWin() {
        if (party.allDefeated()) {
            display.setText("All your heroes are unable to fight, you retreat in a hurry.");
            panel.remove(next);
            panel.add(backToMain);
            backToMain(backToMain);
            panel.validate();
        }
    }

    //MODIFIES: this
    //EFFECTS: clears panel and returns to battle()
    private void nextPanel(JButton next) {
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel();
                battle();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: removes all elements from panel
    private void clearPanel() {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
        display.setText("");
    }

    //EFFECTS: plays the music found in data folder
    public void playMusic() {
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(new File("./data/HeroMusicShort.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

}
