package org.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

abstract class Chancer{
    private static Random random = new Random();
    public static void chanceItem(Collection<Item> newItems, Item item, int probability){
        if (random.nextInt(100) < probability){
            newItems.add(item);
        }
    }
}

interface Lootable{
    ArrayList<Item> loot();
}

class Chest implements Lootable{
    public ArrayList<Item> loot(){
        ArrayList<Item> newItems = new ArrayList<>();

        Chancer.chanceItem(newItems, ItemManager.STEEL_HELMET, 30);
        Chancer.chanceItem(newItems, ItemManager.STEEL_ARMOR, 30);
        Chancer.chanceItem(newItems, ItemManager.STEEL_SHIELD, 30);
        Chancer.chanceItem(newItems, ItemManager.DOLPHIN_FIN, 2);
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

/*
class DungeonManager{}
public class Dungeon implements Lootable {
    ArrayList<Item> loot(){
    };
}
*/