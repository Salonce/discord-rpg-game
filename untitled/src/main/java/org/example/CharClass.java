package org.example;

interface CharClass{
    String getName();
    int getHealthGrowth();
    int getManaGrowth();
}

class Knight implements CharClass{
    public String getName(){ return "knight"; }
    public int getHealthGrowth(){
        return 3;
    }
    public int getManaGrowth(){
        return 1;
    }
}

class Mage implements CharClass{
    public String getName(){ return "mage"; }
    public int getHealthGrowth(){
        return 1;
    }
    public int getManaGrowth(){
        return 3;
    }
}

class Archer implements CharClass{
    public String getName(){ return "archer"; }
    public int getHealthGrowth(){
        return 2;
    }
    public int getManaGrowth(){
        return 2;
    }
}

class CharClassFactory{
    static CharClass createClass(String className) throws IllegalCharacterClassException {
        switch (className) {
            case "knight":
                return new Knight();
            case "mage":
                return new Mage();
            case "archer":
                return new Archer();
            default:
                throw new IllegalCharacterClassException("Class name is wrong.");
        }
    }
}

class IllegalCharacterClassException extends Exception{
   public IllegalCharacterClassException(String message){
       super(message);
   }
}