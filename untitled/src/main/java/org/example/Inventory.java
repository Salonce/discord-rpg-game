package org.example;

import java.util.ArrayList;
import java.util.Iterator;



public class Inventory {
    public static int MAX_ITEM_NUMBER = 12;
    public static int getMaxItemNumber(){ return MAX_ITEM_NUMBER;}

    public Inventory(){
        itemList = new ArrayList<>();
        this.money = 0;
    }

    private ArrayList<Item> itemList;
    public ArrayList<Item> getItemList(){
        return itemList;
    }

    private int money;
    public int getMoney(){return money;}
    public void setMoney(int money){
        this.money = money;
    }

    //private int maxWeight;


    public int getSize(){
        return itemList.size();
    }

    public boolean isFreeSpace(){
        if (itemList.size() < MAX_ITEM_NUMBER)
            return true;
        else return false;
    }

    public void add(Item item) throws InventoryFullException {
        if (item.isEquipment() == true) {
            int itemsnumber = itemList.size();
            if (itemsnumber < MAX_ITEM_NUMBER) {
                itemList.add(item);
            } else {
                throw new InventoryFullException("Inventory is full. Can't pick up the item.");
            }
        }
    }
    public void addItems(ArrayList<Item> itemsToAdd) throws InventoryFullException {
        Iterator<Item> newItemsIterator= itemsToAdd.iterator();
        try {
            while (newItemsIterator.hasNext()) {
                add(newItemsIterator.next());
            }
        }
        catch(InventoryFullException e){
            throw new InventoryFullException("Inventory is full. Can't pick up remaining items.");
        }
    }
    public void removeItem(Item item){
        itemList.remove(item);
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

    public Item get(String name) throws NoSuchItemException {
        int i = 0;
        while(i < itemList.size()){
            if (itemList.get(i).getName().equalsIgnoreCase(name)){
                return itemList.get(i);
            }
            else{
                i++;
            }
        }
        throw new NoSuchItemException();
    }

    public void remove(String name){
        int i = 0;
        while(i < itemList.size()){
            if (itemList.get(i).getName().equalsIgnoreCase(name)){
                itemList.remove(i);
                i = itemList.size();
            }
            else{
                i++;
            }
        }
    }

}

