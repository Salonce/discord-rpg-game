package org.example;

import java.util.ArrayList;
import java.util.stream.Collectors;

class Equipment{
    private Item headEquipment = ManagerItem.NO_HELMET;;
    private Item torsoEquipment = ManagerItem.NO_ARMOR;;
    private Item legsEquipment = ManagerItem.NO_TROUSERS;;
    private Item handsEquipment = ManagerItem.NO_GLOVES;;
    private Item feetEquipment = ManagerItem.NO_BOOTS;;
    private Item firstHandEquipment = ManagerItem.NO_WEAPON;;
    private Item secondHandEquipment = ManagerItem.NO_SHIELD;;

    private ArrayList<Item> readAsList(){
        ArrayList<Item> arrayList = new ArrayList<>();
        arrayList.add(headEquipment);
        arrayList.add(torsoEquipment);
        arrayList.add(legsEquipment);
        arrayList.add(handsEquipment);
        arrayList.add(feetEquipment);
        arrayList.add(firstHandEquipment);
        arrayList.add(secondHandEquipment);
        return arrayList;
    }

    public int getTotalDefence(){ return this.readAsList().stream().collect(Collectors.summingInt(Item::getDefence)); }
    public int getTotalMinAttack(){ return this.readAsList().stream().collect(Collectors.summingInt(Item::getMinAttack)); }
    public int getTotalMaxAttack(){ return this.readAsList().stream().collect(Collectors.summingInt(Item::getMaxAttack)); }
    public int getTotalWeight(){
        return this.readAsList().stream().collect(Collectors.summingInt(Item::getWeight));
    }


    public void equip(Item item) throws NotWearableItemException {
        if (item.getWearable() == Wearable.HEAD){
            headEquipment = item;
        }
        else if (item.getWearable() == Wearable.TORSO){
            torsoEquipment = item;
        }
        else if (item.getWearable() == Wearable.LEGS){
            legsEquipment = item;
        }
        else if (item.getWearable() == Wearable.FEET){
            feetEquipment = item;
        }
        else if (item.getWearable() == Wearable.HANDS){
            handsEquipment = item;
        }
        else if (item.getWearable() == Wearable.FIRSTHAND){
            firstHandEquipment = item;
        }
        else if (item.getWearable() == Wearable.SECONDHAND){
            secondHandEquipment = item;
        }
        else
            throw new NotWearableItemException();
    }

    public Item get(String name){
        if (headEquipment.getName().equalsIgnoreCase(name)){
            return headEquipment;
        }
        else if (torsoEquipment.getName().equalsIgnoreCase(name)){
            return torsoEquipment;
        }
        else if (legsEquipment.getName().equalsIgnoreCase(name)){
            return legsEquipment;
        }
        else if (handsEquipment.getName().equalsIgnoreCase(name)){
            return handsEquipment;
        }
        else if (feetEquipment.getName().equalsIgnoreCase(name)){
            return feetEquipment;
        }
        else if (firstHandEquipment.getName().equalsIgnoreCase(name)){
            return firstHandEquipment;
        }
        else if (secondHandEquipment.getName().equalsIgnoreCase(name)){
            return secondHandEquipment;
        }
        else return null;
    }

    public Item get(Wearable wearable){
        if (wearable == Wearable.HEAD){
            return headEquipment;
        }
        else if (wearable == Wearable.TORSO){
            return torsoEquipment;
        }
        else if (wearable == Wearable.LEGS){
            return legsEquipment;
        }
        else if (wearable == Wearable.HANDS){
            return handsEquipment;
        }
        else if (wearable == Wearable.FEET){
            return feetEquipment;
        }
        else if (wearable == Wearable.FIRSTHAND){
            return firstHandEquipment;
        }
        else if (wearable == Wearable.SECONDHAND){
            return secondHandEquipment;
        }
        else return null;
    }

    public Item remove(String name) {
        Item item = null;
        if (headEquipment.getName().equalsIgnoreCase(name)){
            item = headEquipment;
                this.headEquipment = ManagerItem.NO_HELMET;
        }
        else if (torsoEquipment.getName().equalsIgnoreCase(name)){
            item = torsoEquipment;
                this.torsoEquipment = ManagerItem.NO_ARMOR;
        }
        else if (legsEquipment.getName().equalsIgnoreCase(name)){
            item = legsEquipment;
                this.legsEquipment = ManagerItem.NO_TROUSERS;
        }
        else if (handsEquipment.getName().equalsIgnoreCase(name)){
            item = handsEquipment;
                this.handsEquipment = ManagerItem.NO_GLOVES;
        }
        else if (feetEquipment.getName().equalsIgnoreCase(name)){
            item = feetEquipment;
                this.feetEquipment = ManagerItem.NO_BOOTS;
        }
        else if (firstHandEquipment.getName().equalsIgnoreCase(name)){
            item = firstHandEquipment;
                this.firstHandEquipment = ManagerItem.NO_WEAPON;
        }
        else if (secondHandEquipment.getName().equalsIgnoreCase(name)){
            item = secondHandEquipment;
                this.secondHandEquipment = ManagerItem.NO_SHIELD;
        }
        if (!item.isEquipment())
            return null;
        return item;
    }



    public Item getHeadEquipment() {
        return headEquipment;
    }
    public Item getTorsoEquipment() {
        return torsoEquipment;
    }
    public Item getLegsEquipment() {
        return legsEquipment;
    }
    public Item getHandsEquipment() {
        return handsEquipment;
    }
    public Item getFeetEquipment() {
        return feetEquipment;
    }
    public Item getFirstHandEquipment() {
        return firstHandEquipment;
    }
    public Item getSecondHandEquipment() {
        return secondHandEquipment;
    }
}


/*

        return readAsList().stream().collect(Collectors.summingInt(Item::getWeight));

 */


/*
    private ArrayList<Item> getEquipmentList(){
        ArrayList<Item> arrayList = new ArrayList<>();
        arrayList.add(headEquipment);
        arrayList.add(torsoEquipment);
        arrayList.add(legsEquipment);
        arrayList.add(handsEquipment);
        arrayList.add(feetEquipment);
        arrayList.add(firstHandEquipment);
        arrayList.add(secondHandEquipment);
        return arrayList;
    }
*/


