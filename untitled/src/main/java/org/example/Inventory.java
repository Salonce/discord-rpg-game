package org.example;

import java.util.ArrayList;
import java.util.Iterator;



public class Inventory {
    public static int MAX_ITEM_NUMBER = 10;

    private ArrayList<Item> itemList;
    //private int maxWeight;

    private int money;
    public int getMoney(){return money;}
    public void setMoney(int money){
        this.money = money;
    }

    public Inventory(){
        itemList = new ArrayList<>();
        this.money = 0;
    }
    public int getSize(){
        return itemList.size();
    }

    public boolean isFreeSpace(){
        if (itemList.size() < MAX_ITEM_NUMBER)
            return true;
        else return false;
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

    public ArrayList<Item> getItemList(){
        return itemList;
    }
    public int getItemsWeight(){
        int weight = 0;
        for (Item item : itemList){
            weight += item.getWeight();
        }
        return weight;
    }
    public int getItemsValue(){
        int value = 0;
        for (Item item : itemList){
            value += item.getValue();
        }
        return value;
    }

    public Item getItemByName(String name){
        int i = 0;
        while(i < itemList.size()){
            if (itemList.get(i).getName().equalsIgnoreCase(name)){
                return itemList.get(i);
            }
            else{
                i++;
            }
        }
        return null;
    }

    public void removeItemByName(String name){
        int i = 0;
        while(i < itemList.size()){
            if (itemList.get(i).getName().equalsIgnoreCase(name)){
                itemList.remove(i);
                i = itemList.size();
                //itemList.trimToSize();
            }
            else{
                i++;
            }
        }
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


    /*
    public ArrayList<String> getItemNamesWeightValues(){
        ArrayList<String> itemNames = new ArrayList<>();
        for (Item item : itemList){
            itemNames.add(item.getName() + " (w: " + item.getWeight() + ", v: " + item.getValue() + ")");
        }
        return itemNames;
    }
 */