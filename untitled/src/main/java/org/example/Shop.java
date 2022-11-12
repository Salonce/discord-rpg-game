package org.example;

import java.util.ArrayList;
import java.util.Iterator;


class ShopManager{

}


public abstract class Shop {
    protected ArrayList<Item> itemsOnSale;
    abstract public String itemsAvailable();
}

class BasicShop extends Shop{
    public BasicShop(){
        itemsOnSale = new ArrayList<>();
        itemsOnSale.add(ManagerItem.STEEL_HELMET);
        itemsOnSale.add(ManagerItem.STEEL_ARMOR);
        itemsOnSale.add(ManagerItem.STEEL_BOOTS);
    }

    public String itemsAvailable(){
        Iterator<Item> itemsIterator = itemsOnSale.iterator();
        StringBuilder stringBuilder = new StringBuilder("Items on sale:");
        while (itemsIterator.hasNext()){
            Item item = itemsIterator.next();
            stringBuilder.append("\n" + item.getName() + ", price: " + item.getPrice());
        }
        return stringBuilder.toString();
    }
}