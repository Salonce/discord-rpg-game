package org.example;


class ItemManager{
    //EQUIPMENT
    public final static Item STEEL_HELMET = new Helmet("Steel helmet", 3, 25, 3);
    public final static Item STEEL_ARMOR = new Armor("Steel armor", 15, 55, 10);
    public final static Item STEEL_TROUSERS = new Trousers("Steel trousers", 10, 35, 7);
    public final static Item STEEL_GLOVES = new Gloves("Steel gloves", 3, 10, 3);
    public final static Item STEEL_BOOTS = new Boots("Steel boots", 4, 25, 3);
    public final static Item STEEL_SHIELD = new Shield("Steel shield", 5, 45, 6);
    public final static Item STEEL_SWORD = new Weapon("Steel sword", 3, 45, 10);

    //MONEY
    public final static Item GOLD = new Money("Gold", 0, 0);

    //CRAFTING ITEMS
    public final static Item DOLPHIN_FIN = new CraftingItem("Dolphin's fin :dolphin:", 2, 100);
}



abstract class Item{
    //public static int MAX_ITEM_NAME_LENGTH = 15;
    public Item(String name, int weight, int value){

        this.name = name;
        this.weight = weight;
        this.value = value;
    }
    private final int PRICE_MULTIPLICATOR = 2;

    protected String name;
    protected int weight;
    protected int value;
    //private int price;

    protected String getName(){return name;}
    protected int getWeight(){return weight;}
    protected int getValue(){return value;}
    protected int getPrice(){ return getValue()*PRICE_MULTIPLICATOR; }
}

final class Money extends Item{
    public Money(String name, int weight, int value){
        super(name, weight, value);
    }
}

final class CraftingItem extends Item{
    public CraftingItem(String name, int weight, int value){
        super(name, weight, value);
    }
}

abstract class DefensiveItem extends Item{
    protected int defence;
    public DefensiveItem(String name, int weight, int value, int defence){
        super(name, weight, value);
        this.defence = defence;
    }
    public int getDefence() {return defence;}
}

abstract class OffensiveItem extends Item{
    protected int attack;
    public OffensiveItem(String name, int weight, int value, int attack){
        super(name, weight, value);
        this.attack = attack;
    }
    public int getAttack() {return attack;}
}


class Helmet extends DefensiveItem{
    public Helmet(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }
}

final class Armor extends DefensiveItem{
    public Armor(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }
}
final class Trousers extends DefensiveItem{
    public Trousers(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }
}
final class Gloves extends DefensiveItem{
    public Gloves(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }
}
final class Boots extends DefensiveItem{
    public Boots(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }
}
final class Shield extends DefensiveItem{
    public Shield(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }
}
final class Weapon extends OffensiveItem{
    public Weapon(String name, int weight, int value, int attack){
        super(name, weight, value, attack);
    }
}












/*
public class DefensiveItemBuilder<T extends DefensiveItem>{
    public DefensiveItemBuilder(){}
    private String name;
    private int weight;
    private int value;
    private int price;

    public DefensiveItemBuilder setName(String name){
        this.name = name;
        return this;
    }
    public DefensiveItemBuilder setWeight(int weight){
        this.weight = weight;
        return this;
    }
    public DefensiveItemBuilder setValue(int value){
        this.value = value;
        return this;
    }
    public DefensiveItemBuilder setPrice(int price){
        this.price = price;
        return this;
    }
    public T build(){
        return new T(this);
    }
}
*/

/*

    ////BUILDER
    private Helmet(HelmetBuilder helmetBuilder){
        super(helmetBuilder.name, helmetBuilder.weight, helmetBuilder.value);
    }

    public static class HelmetBuilder{
        public HelmetBuilder(){}
        private String name;
        private int weight;
        private int value;

        public HelmetBuilder setName(String name){
            this.name = name;
            return this;
        }
        public HelmetBuilder setWeight(int weight){
            this.weight = weight;
            return this;
        }
        public HelmetBuilder setValue(int value){
            this.value = value;
            return this;
        }
        public Helmet buildHelmet(){
            return new Helmet(this);
        }
    }
 */