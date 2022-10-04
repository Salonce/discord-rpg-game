package org.example;

class Character{
    //public Character(CharClass charClass, CharRace charRace){
        //this.charClass = charClass;
        //this.charRace = charRace;
    public Character(CharClass charClass, CharRace charRace){
        //this.charClass = CharClassFactory.createClass(charClass);
        //this.charRace = CharRaceFactory.createRace(charRace);
        this.charClass = charClass;
        this.charRace = charRace;
        this.body = new Body();
        this.attributes = new Attributes();
    }

    public CharClass getCharClass(){return charClass;}
    public CharRace getCharRace(){return charRace;}
    public Body getBody(){return body;}
    public Attributes getAttributes(){return attributes;}

    CharClass charClass;
    CharRace charRace;
    Body body;
    Attributes attributes;

    //Equipment equipment;
    //Inventory inventory;
}

interface Attribute{
    int getBaseValue();
    int getAffectedValue();
}

class Health implements Attribute{
    public int getBaseValue(){ return 10; }
    public int getAffectedValue(){ return 5; }
}

class Mana implements Attribute{
    public int getBaseValue(){ return 10; }
    public int getAffectedValue(){ return 5; }
}

abstract class BodyPart{

}

class Head extends BodyPart implements Dressable <Helmet>{
    @Override
    public String dress(Helmet helmet){
        return ("Wearing " + helmet.getName());
    };
    @Override
    public void takeoff(Helmet helmet){
        System.out.println("Taking off " + helmet.getName());
    };
}

class Torso extends BodyPart implements Dressable <Armor>{
    @Override
    public String dress(Armor armor){
        return ("Wearing " + armor.getName());
    };
    @Override
    public void takeoff(Armor armor){
        System.out.println("taking off armor");
    };
}



class Body{
    Head head;
    Torso torso;
}



class Attributes{
    int health;
    int mana;
}


interface Dressable <T extends Item>{
    //void dress(T item);
    String dress(T item);
    void takeoff(T item);
}








//@Override
//protected String getType(){
//    return "GOLD";
//};


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