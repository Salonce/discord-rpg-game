package org.example;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


class EmbedPair{
    private String name;
    private String embed;

    public EmbedPair(String name, String embed){
        this.name = name;
        this.embed = embed;
    }
    public String getName(){
        return name;
    }
    public String getEmbed(){
        return embed;
    }
}


public class Inventory {
    public static int MAX_ITEM_NUMBER = 7;
    //private int maxWeight;
    private ArrayList<Item> itemList;

    public Inventory(){
        itemList = new ArrayList<>();
    }
    public int getSize(){
        return itemList.size();
    }
    public void addItem(Item item) throws InventoryFullException {
        int itemsnumber = itemList.size();
        if (itemsnumber < MAX_ITEM_NUMBER) {
            itemList.add(item);
        }
        else{
            throw new InventoryFullException("Inventory is full. Can't pick up the item.");
        }
    }
    public void addItems(ArrayList<Item> itemsToAdd) throws InventoryFullException {
        Iterator<Item> newItemsIterator= itemsToAdd.iterator();
        try {
            while (newItemsIterator.hasNext()) {
                addItem(newItemsIterator.next());
            }
        }
        catch(InventoryFullException e){
            throw new InventoryFullException("Inventory is full. Can't pick up remaining items.");
        }
    }
    public void removeItem(Item item){
        itemList.remove(item);
    }

    public ArrayList<EmbedPair> getEmbedPairs(){
        ArrayList<EmbedPair> newEmbedPairs = new ArrayList<>();
        for (Item item : itemList){
            newEmbedPairs.add(new EmbedPair(item.getName(), item.getItemEmbedInfo()));
        }
        return newEmbedPairs;
    }
    public int getItemsWeight(){
        int weight = 0;
        for (Item item : itemList){
            weight = weight + item.getWeight();
        }
        return weight;
    }
    public int getItemsValue(){
        int value = 0;
        for (Item item : itemList){
            value = value + item.getValue();
        }
        return value;
    }
    public ArrayList<String> getItemNamesWeightValues(){
        ArrayList<String> itemNames = new ArrayList<>();
        for (Item item : itemList){
            itemNames.add(item.getName() + " (w: " + item.getWeight() + ", v: " + item.getValue() + ")");
        }
        return itemNames;
    }
}

class InventoryFullException extends Exception{
    public InventoryFullException(String message){
        super(message);
    };
}



/*
LEGACY CONTENT
    ////
    public String getItemNamesForEmbed(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Item item : itemList){
            stringBuilder.append(item.getName() + "\n");
        }
        //stringBuilder.append("\u200B\n");
        //stringBuilder.append("**TOTAL: **");
        if (!stringBuilder.isEmpty())
            return stringBuilder.toString();
        else
            return "\u200B";
    }
    public String getItemValuesForEmbed(){
        StringBuilder stringBuilder = new StringBuilder();
        int size = itemList.size();
        int indexNumber = 1;
        for (Item item : itemList){
            if (indexNumber < size)
                stringBuilder.append(item.getValue() + "\n");
            else
                stringBuilder.append("__" + item.getValue() + "__\n");
            indexNumber++;
        }
        stringBuilder.append("**" + getItemsValue() + "**");
        return stringBuilder.toString();
    }
    public String getItemWeightsForEmbed(){
        StringBuilder stringBuilder = new StringBuilder();
        int size = itemList.size();
        int indexNumber = 1;
        for (Item item : itemList){
            if (indexNumber < size)
                stringBuilder.append(item.getWeight() + "\n");
            else
                stringBuilder.append("__" + item.getWeight() + "__\n");
            indexNumber++;
        }
        stringBuilder.append("**" + getItemsWeight() + "**");
        return stringBuilder.toString();
    }

        public ArrayList<Item> getItemList(){ return itemList; }
    public ArrayList<String> getItemNames(){
        ArrayList<String> itemNames = new ArrayList<>();
        for (Item item : itemList){
            itemNames.add(item.getName());
        }
        return itemNames;
    }
 */