package org.example;

public interface CharRace{
    String getName();
    int getHealthIncreasePerLevel();
    int getManaIncreasePerLevel();
}

class Human implements CharRace{
    public String getName(){ return "human"; }
    public int getHealthIncreasePerLevel(){
        return 2;
    }
    public int getManaIncreasePerLevel(){
        return 2;
    }
}

class Orc implements CharRace{
    public String getName(){ return "orc"; }
    public int getHealthIncreasePerLevel(){
        return 3;
    }
    public int getManaIncreasePerLevel(){
        return 1;
    }
}

class Elf implements CharRace{
    public String getName(){ return "elf"; }
    public int getHealthIncreasePerLevel(){
        return 1;
    }
    public int getManaIncreasePerLevel(){
        return 3;
    }
}

class CharRaceFactory{
    static CharRace createRace(String raceName) throws IllegalCharacterRaceException {
        switch (raceName) {
            case "human":
                return new Human();
            case "orc":
                return new Orc();
            case "elf":
                return new Elf();
            default:
                throw new IllegalCharacterRaceException("Race name is wrong.");
        }
    }
}
