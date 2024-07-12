# My Personal Project

## Introducing My Application
My application for this project will be a simulated combat rpg. In the game, there will be a main screen,
where the user will be given a variety of options to progress in the game, such as:
- training
- fighting monsters 
- recruiting new characters

The end goal of the game would be to destroy all the monsters and save the world. 

## Users and Interest

This application would be for those interested in playing the game and the type of game I am making.  I am interested in this project because I have always 
enjoyed playing games and wanted to try to create something that is interesting to me.

## Characters and Enemies
For the game, I am thinking for the characters to have a stats page and gain attributes based on their hero type.
Stats can include:
- Attack
- Health
- Level

When the hero levels up, it will gain additional stats.

The hero types were originally going to be classes such Warrior or Assasin
but I have had to simplify it into two hero types:
- Melee
- Ranged

There can a maximum of three (can change) characters in a party.

The monsters I have implemented into the game include:
- Goblins
- Orcs
- Dragon

which the player has to fight, for a total of five stages.

## User Stories
- As a user, I want to be able create a hero and add or remove it from my party
- As a user, I want to be view the stats of the heroes in my party as well as all my
heroes in my collection (hero list)
- As a user, I want to train the heroes in my party
- As a user, I want to see my heroes fighting monsters
- As a user, I want to save the heroes in my collection 
- As a user, I want to be able to load my heroes from file

##Phase 4: Task 2
I chose the test and design a robust class option. In my Party class,
I added three exceptions: HeroAbsentException in the removeHeroFromParty method
which throws an exception when the input hero is not in the party, 
HeroDuplicateException in the addHeroToParty method which throws an exception when
the hero added is already in the party, and PartyFullException, which throws an 
exception when the party already contains three heroes. 

##Phase 4: Task 3
If I had more time, there are a few things that I could refactor. First, since Party contains 
two lists of Hero that do different functions, for the idea of cohesion,I would separate the two 
lists into different classes, for example Party and AllHeroes, where Party contains one list of 
the zero to three heroes andallHeroes would be the arbitrary amount of heroes that contains all 
the heroes. In addition, I have all my console code and my GUI code each only in a single class 
which contains so many methods so it would be more organized for every different menu, 
such as the party, training, and battle menu,to be in a different class which would 
also make it a lot easier to navigate. Another thing I would change are the two similar methods
in the two classes, HeroRanged and HeroMelee, which are gainExperience and toJson, where they can
be refactored to be a single method in the super class. These are some possible ideas of 
improving the design of my project. 
