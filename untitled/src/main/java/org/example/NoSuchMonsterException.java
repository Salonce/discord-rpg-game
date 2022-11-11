package org.example;

class NoSuchMonsterException extends Exception{
    public NoSuchMonsterException(){
        super();
    }
}

class InvalidUserException extends Exception{
    public InvalidUserException(String message){
        super(message);
    }
}

class InventoryFullException extends Exception{
    public InventoryFullException(String message){
        super(message);
    };
}

class NoSuchItemException extends Exception{
    public NoSuchItemException(){
        super();
    }
}

class NoSuchCharacterException extends Exception{
    public NoSuchCharacterException(String message){
        super(message);
    }
}

class IllegalCharacterRaceException extends Exception{
    public IllegalCharacterRaceException(String message){
        super(message);
    }
}

class IllegalCharacterClassException extends Exception{
    public IllegalCharacterClassException(String message){
        super(message);
    }
}