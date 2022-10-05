package org.example;

import java.util.ArrayList;
import java.util.Random;




interface Lootable{
    ArrayList<Item> loot();
}

public abstract class Dungeon implements Lootable {
    public abstract void visit(Character character);

}

class Chest implements Lootable{
    public ArrayList<Item> loot(){
        ArrayList<Item> newItems = new ArrayList<>();

        Random rand = new Random();
        // Obtain a number between [0 - 49].
        int n;
        n = rand.nextInt(100);
        if (n < 30){
            Item item = new SteelBoots();
            newItems.add(item);
        }
        n = rand.nextInt(100);
        if (n < 30){
            Item item = new SteelArmor();
            newItems.add(item);
        }
        n = rand.nextInt(100);
        if (n < 30){
            Item item = new SteelShield();
            newItems.add(item);
        }
        n = rand.nextInt(100);
        if (n < 10){
            Item item = new DolphinFin();
            newItems.add(item);
        }
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

//class Cave extends Dungeon{
//
//}