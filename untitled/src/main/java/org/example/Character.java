package org.example;


import discord4j.common.util.Snowflake;

import java.util.HashMap;

class Character implements Fighter{
    public Character(){
        this.inventory = new Inventory();
        this.actionPoints = new ActionPoints();
        this.equipment = new Equipment();
        this.stats = new Stats();
    }

    private Stats stats;
    public Stats getStats(){return stats;}

    private Equipment equipment;
    public Equipment getEquipment() {return equipment;}

    private Inventory inventory;
    public Inventory getInventory(){return inventory;}

    private ActionPoints actionPoints;
    public ActionPoints getActionPoints() {return actionPoints;}

    @Override
    public int getCombatPower() {
        System.out.println("HP: " + stats.getHealth());
        System.out.println("str: " + stats.getStrength());
        System.out.println("total att: " + equipment.getTotalAttack());
        System.out.println("total def: " + equipment.getTotalDefence());

        return stats.getHealth() * stats.getStrength() * equipment.getTotalAttack() * equipment.getTotalDefence();

    }

    @Override
    public Properties getProperties() {
        return null;
    }

    @Override
    public Weaknesses getWeaknesses() {
        return null;
    }
}


class Stats{
    public Stats(){
        this.health = 1;
        this.strength = 1;
    }

    //attributes
    private int health;
    private int strength;

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}

class CharacterManager {
    private final HashMap<Snowflake, Character> usersCharacters;
    public CharacterManager() {
        usersCharacters = new HashMap<>();
    }
    public void createNewCharacter(Snowflake id){
        usersCharacters.put(id, new Character());
    }
    public Character getCharacterById(Snowflake id) throws NoSuchCharacterException {
        if (usersCharacters.get(id) == null){
            throw new NoSuchCharacterException("No such character");
        }
        else return usersCharacters.get(id);
    }
}

/*
class Skills{
    public Skills(){
    }
    private int archery;
    private int block;
    private int swordFighting;
}

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


 */