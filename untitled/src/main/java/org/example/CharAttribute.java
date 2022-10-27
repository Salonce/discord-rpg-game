package org.example;

/*
public interface CharAttribute{
    int getStartingValue();
    int getBaseValue();
    int getAffectedValue();
}

class Health implements CharAttribute {
    public int getStartingValue(){ return 100;}
    public int getBaseValue(){ return 10; }
    public int getAffectedValue(){ return 5; }
}

class Mana implements CharAttribute {
    public int getStartingValue(){ return 100;}
    public int getBaseValue(){ return 10; }
    public int getAffectedValue(){ return 5; }
}

class Capacity implements CharAttribute {
    public int getStartingValue(){ return 100;}
    public int getBaseValue(){ return 10; }
    public int getAffectedValue(){ return 5; }
}
*/

public abstract class CharAttribute{
    //protected int baseValue;  // hp 100 // mana 100
    protected int improvedValue;
    protected int affectedValue;

    //public int getBaseValue(){return baseValue;}
    public int getImprovedValue(){return improvedValue;}
    public int getAffectedValue(){return affectedValue;}
    public abstract int getValue(CharRace charRace, CharClass charclass);
}

class Health extends CharAttribute {
    public int getBaseValue(){ return 100; }
    public int getValue(CharRace charRace, CharClass charclass){ return 100;}
}

class Capacity extends CharAttribute {
    public int getBaseValue(){ return 100; }
    public int getValue(CharRace charRace, CharClass charclass){ return 100;}
}




//

/*
class Mana extends CharAttribute {
    public int getBaseValue(){ return 100; }
    public int getValue(CharRace charRace, CharClass charclass){ return 100;}
}
*/