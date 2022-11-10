package org.example;


class Character implements Fighter{
    public Character(CharClass charClass, CharRace charRace){
        this.charClass = charClass;
        this.charRace = charRace;
        this.inventory = new Inventory();
        this.actionPoints = new ActionPoints();
        this.equipment = new Equipment();
        this.stats = new Stats();
    }

    private Stats stats;
    public Stats getStats(){return stats;}

    private CharClass charClass;
    public CharClass getCharClass(){return charClass;}

    private CharRace charRace;
    public CharRace getCharRace(){return charRace;}

    private Equipment equipment;
    public Equipment getEquipment() {return equipment;}

    private Inventory inventory;
    public Inventory getInventory(){return inventory;}

    private ActionPoints actionPoints;
    public ActionPoints getActionPoints() {return actionPoints;}

    @Override
    public int getCombatPower() {
        System.out.println("HP: " + stats.getHealth());
        System.out.println("str: " + stats.getStrength());
        System.out.println("total att: " + equipment.getTotalAttack());
        System.out.println("total def: " + equipment.getTotalDefence());

        return stats.getHealth() * stats.getStrength() * equipment.getTotalAttack() * equipment.getTotalDefence();

    }

    @Override
    public Properties getProperties() {
        return null;
    }

    @Override
    public Weaknesses getWeaknesses() {
        return null;
    }
}


class Stats{
    public Stats(){
        this.health = 1;
        this.strength = 1;
    }

    //attributes
    private int health;
    private int strength;

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}


/*


//I like the idea of having specific needs to unlock next part of the game as - to kill this monster - you need these stats, this item and this

//hitpoints
//hits for 10
//hits for 25
//hits for 50

//max 10?
//what if hits for 0
//hit for 1 is a minimum?

//2
//4
//6
//8
//11
//15
//each *2 *2 *2 or 100-totallost = 80 then 100/totallost = x  x * each value = totallost = 100   something until dies

//picture of a ghost, description
//but what about he fight?


//fight
//You get into a fight with a big yeti which you win.
//
//
// It attack you suddenly, makes you feel pain, breaks your leg, destroys your brain.


//hurt leg - movement speed down
//





package org.example;


class Skills{
    public Skills(){
    }
    private int archery;
    private int block;
    private int swordFighting;
}

class Attributes{
    public Attributes(){
        this.health = 100;
        this.mana = 100;
        this.capacity = 100;
        this.strength = 10;
        this.dexterity = 10;
        this.endurance = 10;
    }

    private int health;
    private int mana;
    private int strength;
    private int dexterity;
    private int endurance;
    private int capacity;


    public int getStrength() {
        return strength;
    }
    public int getDexterity() {
        return dexterity;
    }
    public int getEndurance() {
        return endurance;
    }
    public int getHealth(){return this.health;}
    public int getMana(){return this.mana;}
    public int getCapacity(){return this.capacity;}
}


 */