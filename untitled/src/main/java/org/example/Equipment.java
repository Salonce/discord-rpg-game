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
        this.headEquipment = ItemManager.NO_HELMET;
        this.torsoEquipment = ItemManager.NO_ARMOR;
        this.legsEquipment = ItemManager.NO_TROUSERS;
        this.handsEquipment = ItemManager.NO_GLOVES;
        this.feetEquipment = ItemManager.NO_BOOTS;
        this.firstHandEquipment = ItemManager.NO_WEAPON;
        this.secondHandEquipment = ItemManager.NO_SHIELD;


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
                +feetEquipment.getDefence();
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

    public static String getEmbedStats(Item item){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n" + item.getName());
        stringBuilder.append("\n:coin: " + item.getValue());
        stringBuilder.append("\n:scales: " + item.getWeight());
        //stringBuilder.append("\nPrice: " + item.getPrice());
        if (item.hasAttack()){
            stringBuilder.append("\n:axe: " + item.getAttack());
        }
        if (item.hasDefense()){
            stringBuilder.append("\n:shield: " + item.getDefence());
        }
        return stringBuilder.toString();
    }


    public void equipItem(Item item){
        if (item.getWearablePart() == Wearable.HEAD){
            headEquipment = item;
        }
        else if (item.getWearablePart() == Wearable.TORSO){
            torsoEquipment = item;
        }
        else if (item.getWearablePart() == Wearable.LEGS){
            legsEquipment = item;
        }
        else if (item.getWearablePart() == Wearable.FEET){
            feetEquipment = item;
        }
        else if (item.getWearablePart() == Wearable.HANDS){
            handsEquipment = item;
        }
        else if (item.getWearablePart() == Wearable.FIRSTHAND){
            firstHandEquipment = item;
        }
        else if (item.getWearablePart() == Wearable.SECONDHAND){
            secondHandEquipment = item;
        }
    }

    public Item getItemByName(String name){
        if (headEquipment.getName().equals(name)){
            return headEquipment;
        }
        else if (torsoEquipment.getName().equals(name)){
            return torsoEquipment;
        }
        else if (legsEquipment.getName().equals(name)){
            return legsEquipment;
        }
        else if (handsEquipment.getName().equals(name)){
            return handsEquipment;
        }
        else if (feetEquipment.getName().equals(name)){
            return feetEquipment;
        }
        else if (firstHandEquipment.getName().equals(name)){
            return firstHandEquipment;
        }
        else if (secondHandEquipment.getName().equals(name)){
            return secondHandEquipment;
        }
        else return null;
    }

    public Item getItemByWearable(Wearable wearable){
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


    public boolean removeItemByName(String name){
        if (headEquipment.getName().equals(name)){
            this.headEquipment = ItemManager.NO_HELMET;
            return true;
        }
        else if (torsoEquipment.getName().equals(name)){
            this.torsoEquipment = ItemManager.NO_ARMOR;
            return true;
        }
        else if (legsEquipment.getName().equals(name)){
            this.legsEquipment = ItemManager.NO_TROUSERS;
            return true;
        }
        else if (handsEquipment.getName().equals(name)){
            this.handsEquipment = ItemManager.NO_GLOVES;
            return true;
        }
        else if (feetEquipment.getName().equals(name)){
            this.feetEquipment = ItemManager.NO_BOOTS;
            return true;
        }
        else if (firstHandEquipment.getName().equals(name)){
            this.firstHandEquipment = ItemManager.NO_WEAPON;
            return true;
        }
        else if (secondHandEquipment.getName().equals(name)){
            this.secondHandEquipment = ItemManager.NO_SHIELD;
            return true;
        }
        return false;
    }

    public Item getHeadEquipment() {
        return headEquipment;
    }

    public void setHeadEquipment(Item headEquipment) {
        this.headEquipment = headEquipment;
    }

    public Item getTorsoEquipment() {
        return torsoEquipment;
    }

    public void setTorsoEquipment(Item torsoEquipment) {
        this.torsoEquipment = torsoEquipment;
    }

    public Item getLegsEquipment() {
        return legsEquipment;
    }

    public void setLegsEquipment(Item legsEquipment) {
        this.legsEquipment = legsEquipment;
    }

    public Item getHandsEquipment() {
        return handsEquipment;
    }

    public void setHandsEquipment(Item handsEquipment) {
        this.handsEquipment = handsEquipment;
    }

    public Item getFeetEquipment() {
        return feetEquipment;
    }

    public void setFeetEquipment(Item bootsEquipment) {
        this.feetEquipment = bootsEquipment;
    }

    public Item getFirstHandEquipment() {
        return firstHandEquipment;
    }

    public void setFirstHandEquipment(Item firstHandEquipment) {
        this.firstHandEquipment = firstHandEquipment;
    }

    public Item getSecondHandEquipment() {
        return secondHandEquipment;
    }

    public void setSecondHandEquipment(Item secondHandEquipment) {
        this.secondHandEquipment = secondHandEquipment;
    }
}




