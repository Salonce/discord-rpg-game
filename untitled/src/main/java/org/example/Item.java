package org.example;

import java.util.Comparator;

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
    protected int defence;
    protected int attack;
    //private int price;

    protected String getName(){return name;}
    protected int getWeight(){return weight;}
    protected int getValue(){return value;}
    protected int getPrice(){ return getValue()*PRICE_MULTIPLICATOR; }

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

    public abstract boolean isMoney();
    public abstract boolean hasAttack();
    public abstract boolean hasDefense();
    public abstract boolean isEmptyEquipment();

    public abstract Wearable getWearable();

    public boolean isEquipment(){
        return !isEmptyEquipment();
    };
    //public boolean checkWearable() throws NotWearableItemException {
    //    if (!getWearable().equals(Wearable.NOTHING))
    //        return true;
    //    else
    //        throw new NotWearableItemException();
    //}
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
    public Wearable getWearable() {
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
    public Wearable getWearable() {
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
    public Wearable getWearable() {
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
    public Wearable getWearable() {
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
    public Wearable getWearable() {
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
    public Wearable getWearable() {
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
    public Wearable getWearable() {return Wearable.FIRSTHAND;}
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
    public Wearable getWearable() {
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
    public Wearable getWearable() {
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
    public Wearable getWearable() {
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
    public Wearable getWearable() {
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
    public Wearable getWearable() {
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
    public Wearable getWearable() {
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
    public Wearable getWearable() {
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
    public Wearable getWearable() {
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
    public Wearable getWearable() {
        return Wearable.NOTHING;
    }
}

//
enum WeaponType{
    CUTTING,
    BASHING,
    PIERCING
    //FIRE
    //WATER
    //POISON
}

/*
///Item builder gives more optional values to pick and make the coupling with ENUM system looser, i think

enum Itemtype{
    HELMET (false, false, true, false, Wearable.HEAD),
    ARMOR (false, false, true, false, Wearable.TORSO),
    TROUSERS (false, false, true, false, Wearable.LEGS),
    BOOTS (false, false, true, false, Wearable.FEET),
    GLOVES (false, false, true, false, Wearable.HANDS),
    ONEHWEAPON (false, true, false, false, Wearable.FIRSTHAND),
    SHIELD (false, false, true, false, Wearable.SECONDHAND),

    NOHELMET (false, false, true, true, Wearable.HEAD),
    NOARMOR (false, false, true, true, Wearable.TORSO),
    NOTROUSERS (false, false, true, true, Wearable.LEGS),
    NOBOOTS (false, false, true, true, Wearable.FEET),
    NOGLOVES (false, false, true, true, Wearable.HANDS),
    NOONEHWEAPON (false, true, false, true, Wearable.FIRSTHAND),
    NOSHIELD (false, false, true, true, Wearable.SECONDHAND),

    CRAFTINGITEM (false, false, false, false, Wearable.NOTHING),
    MONEY (true, false, false, false, Wearable.NOTHING);

    private final boolean isMoney;
    private final boolean hasattack;
    private final boolean hasdefence;
    private final boolean isEmptyEquipment;
    private final Wearable wearable;
    Itemtype(boolean isMoney, boolean hasAttack, boolean hasDefense, boolean isEmptyEquipment, Wearable wearable) {
        this.isMoney = isMoney;
        this.hasattack = hasAttack;
        this.hasdefence = hasDefense;
        this.isEmptyEquipment = isEmptyEquipment;
        this.wearable = wearable;
    }
}
 */




/*

    //TESTING
    static Comparator<Item> getComparatorByName(){
        return Comparator.comparing(Item::getName);
    }
    static Comparator<Item> getComparatorByValue(){
        return Comparator.<Item, Integer>comparing(item -> item.getValue());
    }
    //END OF TESTING
 */