package org.example;

public interface CharRace{
    String getName();
    int getHealthGrowth();
    int getManaGrowth();
}

class Human implements CharRace{
    public String getName(){ return "human"; }
    public int getHealthGrowth(){
        return 2;
    }
    public int getManaGrowth(){
        return 2;
    }
}

class Orc implements CharRace{
    public String getName(){ return "orc"; }
    public int getHealthGrowth(){
        return 3;
    }
    public int getManaGrowth(){
        return 1;
    }
}

class Elf implements CharRace{
    public String getName(){ return "elf"; }
    public int getHealthGrowth(){
        return 1;
    }
    public int getManaGrowth(){
        return 3;
    }
}

class CharRaceFactory{
    static CharRace createRace(String raceName) {
        switch (raceName) {
            case "human":
                return new Human();
            case "orc":
                return new Orc();
            case "elf":
                return new Elf();
            default:
                return null;
        }
    }
}