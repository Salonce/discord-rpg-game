package org.example;

class Character implements Battleable{
    public Character(CharClass charClass, CharRace charRace){
        this.charClass = charClass;
        this.charRace = charRace;
        this.body = new Body();
        this.attributes = new Attributes();
        this.inventory = new Inventory();
    }

    private CharClass charClass;
    private CharRace charRace;
    private Body body;
    private Attributes attributes;
    private Inventory inventory;

    public CharClass getCharClass(){return charClass;}
    public CharRace getCharRace(){return charRace;}
    public Body getBody(){return body;}
    public Attributes getAttributes(){return attributes;}
    public Inventory getInventory(){return inventory;}

    public BattleStats getBattleStats(){
        return new BattleStats(getAttributes().getHealth(), getAttributes().getStrength(), getAttributes().getEndurance(), getAttributes().getDexterity());
    }
    //Equipment equipment;
    //Inventory inventory;
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


/*
interface Comparable<T> {
    int compareTo(T o);
}

class Something implements Comparable<Item>{
    public int compareTo(Item item){
        return 5;
    }
}
*/


/*
interface CharClass{
    int getPointsReqForStrength();
    int getPointsReqForDexterity();
    int getPointsReqForIntelligence();
    //int getMaxHealth(int level);
    //int getMaxMana(int level);
}

class Knight implements CharClass{
    public int getPointsReqForStrength(){
        return 1;
    };
    public int getPointsReqForDexterity(){
        return 2;
    };
    public int getPointsReqForIntelligence(){
        return 3;
    };
}

class Archer implements CharClass{
    public int getPointsReqForStrength(){
        return 2;
    };
    public int getPointsReqForDexterity(){
        return 1;
    };
    public int getPointsReqForIntelligence(){
        return 3;
    };
}
class Mage implements CharClass{
    public int getPointsReqForStrength(){
        return 3;
    };
    public int getPointsReqForDexterity(){
        return 2;
    };
    public int getPointsReqForIntelligence(){
        return 1;
    };
}
*/