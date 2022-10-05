package org.example;

import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class Inventory {
    public Inventory(){
        itemList = new ArrayList<>();
    }
    //private int maxWeight;
    private ArrayList<Item> itemList;

    public void addItem(Item item){
        itemList.add(item);
    }
    public void addItems(ArrayList<Item> items){
        itemList.addAll(items);
    }
    public void removeItem(Item item){
        itemList.remove(item);
    }

    public ArrayList<Item> getItemList(){ return itemList; }
    public ArrayList<String> getItemNames(){
        ArrayList<String> itemNames = new ArrayList<>();
        for (Item item : itemList){
            itemNames.add(item.getName());
        }
        return itemNames;
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
            itemNames.add(item.getName() + " (weight: " + item.getWeight() + ", value: " + item.getValue() + ")");
        }
        return itemNames;
    }
}
