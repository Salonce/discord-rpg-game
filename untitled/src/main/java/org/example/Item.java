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

    //NON-EQUIPMENT
    public final static Item NO_HELMET = new NoHelmet("-", 0, 0, 0);
    public final static Item NO_ARMOR = new NoArmor("-", 0, 0, 0);
    public final static Item NO_TROUSERS = new NoTrousers("-", 0, 0, 0);
    public final static Item NO_GLOVES = new NoGloves("-", 0, 0, 0);
    public final static Item NO_BOOTS = new NoBoots("-", 0, 0, 0);
    public final static Item NO_SHIELD = new NoShield("-", 0, 0, 1);
    public final static Item NO_WEAPON = new NoWeapon("-", 0, 0, 1);


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

    public abstract boolean isMoney();
    public abstract boolean hasAttack();
    public abstract boolean hasDefense();

    public abstract boolean isEmptyEquipment();
    public abstract Wearable getWearablePart();

    protected int defence;
    protected int attack;

    public int getDefence(){
        if (hasDefense())
            return defence;
        else return 0;
    }
    public int getAttack(){
        if (hasAttack())
            return attack;
        else return 0;
    }

    public String getItemEmbedInfo(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(":coin:: " + this.value).
                append("\n:scales:: " + this.weight);

        return stringBuilder.toString();
    }
}


abstract class DefensiveItem extends Item{
    public DefensiveItem(String name, int weight, int value, int defence){
        super(name, weight, value);
        this.defence = defence;
    }
    @Override
    public boolean isMoney() {
        return false;
    }
    @Override
    public boolean hasAttack() {
        return false;
    }

    @Override
    public boolean hasDefense() {
        return true;
    }
}

abstract class OffensiveItem extends Item{
    public OffensiveItem(String name, int weight, int value, int attack){
        super(name, weight, value);
        this.attack = attack;
    }
    @Override
    public boolean isMoney() {
        return false;
    }
    @Override
    public boolean hasAttack() {
        return true;
    }

    @Override
    public boolean hasDefense() {
        return false;
    }
}

abstract class HeadEquipment extends DefensiveItem{
    public HeadEquipment(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }
}

abstract class TorsoEquipment extends DefensiveItem{
    public TorsoEquipment(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }
}

abstract class LegsEquipment extends DefensiveItem{
    public LegsEquipment(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }
}

abstract class BootsEquipment extends DefensiveItem{
    public BootsEquipment(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }
}

abstract class HandsEquipment extends DefensiveItem{
    public HandsEquipment(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }
}

abstract class SecondHandEquipment extends DefensiveItem{
    public SecondHandEquipment(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }
}

abstract class FirstHandEquipment extends OffensiveItem{
    public FirstHandEquipment(String name, int weight, int value, int attack){
        super(name, weight, value, attack);
    }
}


class NoHelmet extends HeadEquipment{
    public NoHelmet(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }

    @Override
    public boolean isEmptyEquipment() {
        return true;
    }

    @Override
    public Wearable getWearablePart() {
        return Wearable.HEAD;
    }
}
final class NoArmor extends TorsoEquipment{
    public NoArmor(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }

    @Override
    public boolean isEmptyEquipment() {
        return true;
    }

    @Override
    public Wearable getWearablePart() {
        return Wearable.TORSO;
    }
}
final class NoTrousers extends LegsEquipment{
    public NoTrousers(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }

    @Override
    public boolean isEmptyEquipment() {
        return true;
    }

    @Override
    public Wearable getWearablePart() {
        return Wearable.LEGS;
    }
}
final class NoGloves extends HandsEquipment{
    public NoGloves(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }

    @Override
    public boolean isEmptyEquipment() {
        return true;
    }

    @Override
    public Wearable getWearablePart() {
        return Wearable.HANDS;
    }
}
final class NoBoots extends BootsEquipment{
    public NoBoots(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }

    @Override
    public boolean isEmptyEquipment() {
        return true;
    }

    @Override
    public Wearable getWearablePart() {
        return Wearable.FEET;
    }
}
final class NoShield extends SecondHandEquipment{
    public NoShield(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }

    @Override
    public boolean isEmptyEquipment() {
        return true;
    }

    @Override
    public Wearable getWearablePart() {
        return Wearable.SECONDHAND;
    }
}
final class NoWeapon extends FirstHandEquipment{
    public NoWeapon(String name, int weight, int value, int attack){
        super(name, weight, value, attack);
    }

    @Override
    public boolean isEmptyEquipment() {
        return true;
    }

    @Override
    public Wearable getWearablePart() {
        return Wearable.FIRSTHAND;
    }
}


class Helmet extends HeadEquipment{
    public Helmet(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }

    @Override
    public boolean isEmptyEquipment() {
        return false;
    }

    @Override
    public Wearable getWearablePart() {
        return Wearable.HEAD;
    }
}
final class Armor extends TorsoEquipment{
    public Armor(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }

    @Override
    public boolean isEmptyEquipment() {
        return false;
    }

    @Override
    public Wearable getWearablePart() {
        return Wearable.TORSO;
    }
}
final class Trousers extends LegsEquipment{
    public Trousers(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }
    @Override
    public boolean isEmptyEquipment() {
        return false;
    }

    @Override
    public Wearable getWearablePart() {
        return Wearable.LEGS;
    }
}
final class Gloves extends HandsEquipment{
    public Gloves(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }
    @Override
    public boolean isEmptyEquipment() {
        return false;
    }

    @Override
    public Wearable getWearablePart() {
        return Wearable.HANDS;
    }
}
final class Boots extends BootsEquipment{
    public Boots(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }
    @Override
    public boolean isEmptyEquipment() {
        return false;
    }

    @Override
    public Wearable getWearablePart() {
        return Wearable.FEET;
    }
}
final class Shield extends SecondHandEquipment{
    public Shield(String name, int weight, int value, int defence){
        super(name, weight, value, defence);
    }
    @Override
    public boolean isEmptyEquipment() {
        return false;
    }

    @Override
    public Wearable getWearablePart() {
        return Wearable.SECONDHAND;
    }
}
final class Weapon extends FirstHandEquipment{
    public Weapon(String name, int weight, int value, int attack){
        super(name, weight, value, attack);
    }
    @Override
    public boolean isEmptyEquipment() {
        return false;
    }

    @Override
    public Wearable getWearablePart() {
        return Wearable.FIRSTHAND;
    }
}

final class CraftingItem extends Item{
    public CraftingItem(String name, int weight, int value){
        super(name, weight, value);
    }

    @Override
    public boolean isMoney() {
        return false;
    }

    @Override
    public boolean hasAttack() {
        return false;
    }

    @Override
    public boolean hasDefense() {
        return false;
    }

    @Override
    public boolean isEmptyEquipment() {
        return false;
    }

    @Override
    public Wearable getWearablePart() {
        return Wearable.NOTHING;
    }
}

final class Money extends Item{
    public Money(String name, int weight, int value){
        super(name, weight, value);
    }

    @Override
    public boolean isMoney() {
        return true;
    }

    @Override
    public boolean hasAttack() {
        return false;
    }

    @Override
    public boolean hasDefense() {
        return false;
    }

    @Override
    public boolean isEmptyEquipment() {
        return false;
    }

    @Override
    public Wearable getWearablePart() {
        return Wearable.NOTHING;
    }
}

enum Wearable{
    HEAD,
    TORSO,
    LEGS,
    FEET,
    HANDS,
    FIRSTHAND,
    SECONDHAND,
    NOTHING
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