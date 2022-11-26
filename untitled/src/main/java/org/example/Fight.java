package org.example;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;

import java.util.ArrayList;
import java.util.Random;

class CombatStrength{
    private int health;
    private int minAttack;
    private int maxAttack;
    private int defense;
    private int speed;
    public CombatStrength(int health, int minAttack, int maxAttack, int defense, int speed){
        this.health = health;
        this.minAttack = minAttack;
        this.maxAttack = maxAttack;
        this.defense = defense;
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }
    public int getMinAttack() {
        return minAttack;
    }
    public int getMaxAttack() {
        return maxAttack;
    }
    public int getDefense() {
        return defense;
    }
    public int getSpeed() {
        return speed;
    }
}

interface Fighter{
    int getCombatPower();
    CombatStrength getCombatStrength();
    Properties getProperties();
    Weaknesses getWeaknesses();
}



class Properties{
    public Properties(WeaponType weaponType){
        this.weaponType = weaponType;
    }

    private WeaponType weaponType;
    public WeaponType getWeaponType(){ return this.weaponType; }
}

class Weaknesses{
    public Weaknesses(WeaponType weaponType){
        this.weaponType = weaponType;
    }
    private WeaponType weaponType;
    public WeaponType getWeaponType(){ return this.weaponType; }
}

//weaknesses...

public class Fight {
    public Fight(Fighter fighterA, Fighter fighterB){
        this.fighterA = fighterA;
        this.fighterB = fighterB;
        this.powerA = fighterA.getCombatPower();
        this.powerB = fighterB.getCombatPower();
        this.results = new Results(fighterA.getCombatStrength(), fighterB.getCombatStrength());
    }

    public Results getResults() {
        return results;
    }

    private Results results;
    private Fighter fighterA;
    private Fighter fighterB;

    private int powerA;
    private int powerB;

    String getResult(){
        if (powerA > powerB)
            return "A wins";
        else if (powerA < powerB)
            return "B wins";
        return "tie";
    }


}

class Results{
    public Results(CombatStrength combatStrengthA, CombatStrength combatStrengthB){
        this.combatStrengthA = combatStrengthA;
        this.combatStrengthB = combatStrengthB;
        this.totalPowerA = calculateRandomTotalPower(combatStrengthA);
        this.totalPowerB = calculateRandomTotalPower(combatStrengthB);
        this.resultA = new Result();
        this.resultB = new Result();

        this.setResults(totalPowerA, totalPowerB);
    }

    public Result getResultA(){
        return resultA;
    }
    public Result getResultB(){
        return resultB;
    }

    private CombatStrength combatStrengthA;
    private CombatStrength combatStrengthB;
    private int totalPowerA;
    private int totalPowerB;
    private Result resultA;
    private Result resultB;



    private int calculateRandomTotalPower(CombatStrength combatStrength){
        int minTotalPower = combatStrength.getHealth() * combatStrength.getMinAttack() * (combatStrength.getDefense() + combatStrength.getSpeed());
        int maxTotalPower = combatStrength.getHealth() * combatStrength.getMaxAttack() * (combatStrength.getDefense() + combatStrength.getSpeed());
        Random random = new Random();
        int randomTotalPower = minTotalPower;
        if (maxTotalPower > minTotalPower)
            randomTotalPower += random.nextInt(maxTotalPower-minTotalPower);
        return randomTotalPower;
    }

    private void setResults (int totalPowerA, int totalPowerB){
        if (totalPowerA > totalPowerB){
            double newHealth = (double) combatStrengthA.getHealth() - ((( (double) totalPowerB) / (double) totalPowerA) * (double) combatStrengthA.getHealth());
            System.out.println("totalPowerB: " + totalPowerB);
            System.out.println("totalPowerA: " + totalPowerA);
            System.out.println("A health: " + combatStrengthA.getHealth());
            System.out.println("A new health: " + newHealth);
            if (newHealth <= 0)
                newHealth = 1;
            resultA.setHealth((int) Math.round(newHealth));
            resultA.setVictory(true);
            resultB.setHealth(0);
            resultB.setVictory(false);
        }
        else if (totalPowerB > totalPowerA){
            double newHealth = (double) combatStrengthB.getHealth() - ((((double) totalPowerA)/totalPowerB) * combatStrengthB.getHealth());
            System.out.println("totalPowerA: " + totalPowerA);
            System.out.println("totalPowerB: " + totalPowerB);
            System.out.println("B health: " + combatStrengthB.getHealth());
            System.out.println("B new health: " + newHealth);
            if (newHealth <= 0)
                newHealth = 1;
            resultB.setHealth((int) Math.round(newHealth));
            resultB.setVictory(true);
            resultA.setHealth(0);
            resultA.setVictory(false);
        }
        else if (totalPowerA == totalPowerB){
            Random random = new Random();
            int OneOrTwo = random.nextInt(2);
            if (OneOrTwo == 0) {
                resultA.setHealth(1);
                resultA.setVictory(true);
                resultB.setHealth(0);
                resultB.setVictory(false);
            }
            else{
                resultA.setHealth(0);
                resultA.setVictory(false);
                resultB.setHealth(1);
                resultB.setVictory(true);
            }
        }
    }

}

class Result{
    private int health;
    private boolean victory;

    public int getHealth() {
        return health;
    }
    public boolean isVictory() {
        return victory;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }

}


//weather_type //environment conditions

/*

class Chest implements Lootable{
    public ArrayList<Item> loot(){
        ArrayList<Item> newItems = new ArrayList<>();

        Chancer.chanceItem(newItems, ItemManager.STEEL_SWORD, 60);
        Chancer.chanceItem(newItems, ItemManager.STEEL_HELMET, 20);
        Chancer.chanceItem(newItems, ItemManager.STEEL_ARMOR, 20);
        Chancer.chanceItem(newItems, ItemManager.STEEL_SHIELD, 60);
        Chancer.chanceItem(newItems, ItemManager.DOLPHIN_SHIELD, 60);
        Chancer.chanceItem(newItems, ItemManager.DOLPHIN_FIN, 25);
        Chancer.chanceItem(newItems, ItemManager.SHEEP_WOOL, 25);
        return newItems;
    }
}

class LootingHelper{
    public static ArrayList<String> getItemNamesInArrayList(ArrayList<Item> itemList){
        ArrayList<String> itemNames = new ArrayList<>();
        for (Item item : itemList){
            itemNames.add(item.getName());
        }
        return itemNames;
    }
    public static String getItemNamesInString(ArrayList<Item> itemList){
        ArrayList<String> itemNames = new ArrayList<>();
        for (Item item : itemList){
            itemNames.add(item.getName());
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String name : itemNames) {
            stringBuilder.append(name + ",\n");
        }
        String fullList = stringBuilder.toString();
        return fullList;
    }
}
*/
