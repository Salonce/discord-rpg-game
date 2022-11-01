package org.example;

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
