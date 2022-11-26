package org.example;

import java.util.*;
import java.util.stream.Collectors;


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
    public void addMoney(int money){
        this.money =+ money;
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

    //return true if an item was added succesfully
    public boolean add(Item item) throws InventoryFullException {
        if (item.isEquipment() == true) {
            int itemsnumber = itemList.size();
            if (itemsnumber < MAX_ITEM_NUMBER) {
                itemList.add(item);
                return true;
            } else {
                throw new InventoryFullException("Inventory is full. Can't pick up the item.");
            }
        }
        else{
            return false;
        }
    }
    public int addItems(ArrayList<Item> itemsToAdd) throws InventoryFullException {
        //int numberToAdd = MAX_ITEM_NUMBER - itemList.size();
        //if (itemsToAdd.size() < numberToAdd){
        //    numberToAdd = itemsToAdd.size();
        //}

        int number = 0;

        Iterator<Item> newItemsIterator= itemsToAdd.iterator();
        try {
            while (newItemsIterator.hasNext()) {
                add(newItemsIterator.next());
                number++;
            }
        }
        catch(InventoryFullException e){
            return number;
        }
        return number;
    }

    public Item get(String name) throws NoSuchItemException {
        try {
            return itemList.stream().filter(item -> item.getName().equalsIgnoreCase(name)).findAny().get();
        } catch (NoSuchElementException e){
            throw new NoSuchItemException();
        }
    }
    public int getItemsWeight(){return itemList.stream().map(Item::getWeight).collect(Collectors.summingInt(Integer::intValue));}
    public int getItemsValue(){return itemList.stream().map(Item::getValue).collect(Collectors.summingInt(Integer::intValue));}
    public void remove(String name) throws NoSuchItemException {itemList.remove(get(name));}
    public void removeItem(Item item){
        itemList.remove(item);
    }
    public void sortByName(){
        itemList.sort(Comparator.comparing(Item::getName));
    }
    public void sortByValue(){
        itemList.sort(Comparator.comparing(Item::getValue));
    }
    public void sortByValueReversed(){
        itemList.sort(Comparator.comparing(Item::getValue).reversed());
    }
    public void swap(int a, int b) throws IndexOutOfBoundsException { Collections.swap(itemList, a, b);}
}




        /*for(int i = 0; i < itemList.size(); i++){
            if (itemList.get(i).getName().equalsIgnoreCase(name)){
                itemList.remove(i);
                return;
            }
        }
        throw new NoSuchItemException();
         */
        /*
        int weight = 0;
        for (Item item : itemList){
            weight += item.getWeight();
        }
        return weight;
         */