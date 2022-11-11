package org.example;

import java.util.ArrayList;
import java.util.Random;

interface Fighter{
    int getCombatPower();
    Properties getProperties();
    Weaknesses getWeaknesses();
}

class Properties{
    public Properties(WeaponType weaponType){
        this.weaponType = weaponType;
    }

    private WeaponType weaponType;
    public WeaponType getWeaponType(){ return this.weaponType; }
}

class Weaknesses{
    public Weaknesses(WeaponType weaponType){
        this.weaponType = weaponType;
    }
    private WeaponType weaponType;
    public WeaponType getWeaponType(){ return this.weaponType; }
}

//weaknesses...

public class Fight {
    public Fight(Fighter fighterA, Fighter fighterB){
        this.fighterA = fighterA;
        this.fighterB = fighterB;
        this.powerA = fighterA.getCombatPower();
        this.powerB = fighterB.getCombatPower();
    }

    private Fighter fighterA;
    private Fighter fighterB;

    private int powerA;
    private int powerB;

    String getResult(){
        if (powerA > powerB)
            return "A wins";
        else if (powerA < powerB)
            return "B wins";
        return "tie";
    }
}


//weather_type //environment conditions

/*

class Chest implements Lootable{
    public ArrayList<Item> loot(){
        ArrayList<Item> newItems = new ArrayList<>();

        Chancer.chanceItem(newItems, ItemManager.STEEL_SWORD, 60);
        Chancer.chanceItem(newItems, ItemManager.STEEL_HELMET, 20);
        Chancer.chanceItem(newItems, ItemManager.STEEL_ARMOR, 20);
        Chancer.chanceItem(newItems, ItemManager.STEEL_SHIELD, 60);
        Chancer.chanceItem(newItems, ItemManager.DOLPHIN_SHIELD, 60);
        Chancer.chanceItem(newItems, ItemManager.DOLPHIN_FIN, 25);
        Chancer.chanceItem(newItems, ItemManager.SHEEP_WOOL, 25);
        return newItems;
    }
}

class LootingHelper{
    public static ArrayList<String> getItemNamesInArrayList(ArrayList<Item> itemList){
        ArrayList<String> itemNames = new ArrayList<>();
        for (Item item : itemList){
            itemNames.add(item.getName());
        }
        return itemNames;
    }
    public static String getItemNamesInString(ArrayList<Item> itemList){
        ArrayList<String> itemNames = new ArrayList<>();
        for (Item item : itemList){
            itemNames.add(item.getName());
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String name : itemNames) {
            stringBuilder.append(name + ",\n");
        }
        String fullList = stringBuilder.toString();
        return fullList;
    }
}
*/
