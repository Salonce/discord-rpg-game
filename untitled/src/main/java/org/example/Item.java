package org.example;

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