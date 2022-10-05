package org.example;

class Character{
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

    //Equipment equipment;
    //Inventory inventory;
}




class Attributes{
    int health;
    int mana;
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