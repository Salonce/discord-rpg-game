package org.example;

import java.util.ArrayList;

class Equipment{
    private Item headEquipment;
    private Item torsoEquipment;
    private Item legsEquipment;
    private Item handsEquipment;
    private Item feetEquipment;
    private Item firstHandEquipment;
    private Item secondHandEquipment;

    public Equipment(){
        this.headEquipment = ManagerItem.NO_HELMET;
        this.torsoEquipment = ManagerItem.NO_ARMOR;
        this.legsEquipment = ManagerItem.NO_TROUSERS;
        this.handsEquipment = ManagerItem.NO_GLOVES;
        this.feetEquipment = ManagerItem.NO_BOOTS;
        this.firstHandEquipment = ManagerItem.NO_WEAPON;
        this.secondHandEquipment = ManagerItem.NO_SHIELD;
    }

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

    public int getTotalDefence(){
        return headEquipment.getDefence()
                +torsoEquipment.getDefence()
                +legsEquipment.getDefence()
                +handsEquipment.getDefence()
                +feetEquipment.getDefence()
                +firstHandEquipment.getDefence()
                +secondHandEquipment.getDefence();
    }
    public int getTotalAttack(){
        return headEquipment.getAttack()
                +torsoEquipment.getAttack()
                +legsEquipment.getAttack()
                +handsEquipment.getAttack()
                +feetEquipment.getAttack()
                +firstHandEquipment.getAttack()
                +secondHandEquipment.getAttack();
    }
    public int getTotalWeight(){
        return headEquipment.getWeight()
                +torsoEquipment.getWeight()
                +legsEquipment.getWeight()
                +handsEquipment.getWeight()
                +feetEquipment.getWeight()
                +firstHandEquipment.getWeight()
                +secondHandEquipment.getWeight();
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

    public Item get(String name) throws NoSuchItemException {
        if (headEquipment.getName().equalsIgnoreCase(name)){
            if (headEquipment == null)
                throw new NoSuchItemException();
            return headEquipment;
        }
        else if (torsoEquipment.getName().equalsIgnoreCase(name)){
            if (torsoEquipment == null)
                throw new NoSuchItemException();
            return torsoEquipment;
        }
        else if (legsEquipment.getName().equalsIgnoreCase(name)){
            if (legsEquipment == null)
                throw new NoSuchItemException();
            return legsEquipment;
        }
        else if (handsEquipment.getName().equalsIgnoreCase(name)){
            if (handsEquipment == null)
                throw new NoSuchItemException();
            return handsEquipment;
        }
        else if (feetEquipment.getName().equalsIgnoreCase(name)){
            if (feetEquipment == null)
                throw new NoSuchItemException();
            return feetEquipment;
        }
        else if (firstHandEquipment.getName().equalsIgnoreCase(name)){
            if (firstHandEquipment == null)
                throw new NoSuchItemException();
            return firstHandEquipment;
        }
        else if (secondHandEquipment.getName().equalsIgnoreCase(name)){
            if (secondHandEquipment == null)
                throw new NoSuchItemException();
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




