package org.example;



class Character implements Battleable{
    public Character(CharClass charClass, CharRace charRace){
        this.charClass = charClass;
        this.charRace = charRace;
        this.body = new Body();
        this.attributes = new Attributes();
        this.inventory = new Inventory();
        this.actionPoints = new ActionPoints();
        this.equipment = new Equipment();
    }

    private CharClass charClass;
    private CharRace charRace;
    private Body body;
    private Attributes attributes;
    private Equipment equipment;
    private Inventory inventory;
    private ActionPoints actionPoints;

    public CharClass getCharClass(){return charClass;}
    public CharRace getCharRace(){return charRace;}
    public Body getBody(){return body;}
    public Attributes getAttributes(){return attributes;}
    public Inventory getInventory(){return inventory;}
    public ActionPoints getActionPoints() {return actionPoints;}
    public Equipment getEquipment() {return equipment;}

    public BattleStats getBattleStats(){
        return new BattleStats(getAttributes().getHealth(), getAttributes().getStrength(), getAttributes().getEndurance(), getAttributes().getDexterity());
    }

    public void itemToEquipment(String itemName){
     //   String
    }
    //public void itemToEquipment(String itemName){
//
    //}
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