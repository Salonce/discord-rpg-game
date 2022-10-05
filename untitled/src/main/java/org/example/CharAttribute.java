package org.example;

public interface CharAttribute{
    int getBaseValue();
    int getAffectedValue();
}

class Health implements CharAttribute {
    public int getBaseValue(){ return 10; }
    public int getAffectedValue(){ return 5; }
}

class Mana implements CharAttribute {
    public int getBaseValue(){ return 10; }
    public int getAffectedValue(){ return 5; }
}

