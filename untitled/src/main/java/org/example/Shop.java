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
        itemsOnSale.add(ItemManager.STEEL_HELMET);
        itemsOnSale.add(ItemManager.STEEL_ARMOR);
        itemsOnSale.add(ItemManager.STEEL_BOOTS);
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