package org.example;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;

class Character implements Battleable{
    public Character(CharClass charClass, CharRace charRace){
        this.charClass = charClass;
        this.charRace = charRace;
        this.body = new Body();
        this.attributes = new Attributes();
        this.inventory = new Inventory();
        this.actionPoints = new ActionPoints();
    }

    private CharClass charClass;
    private CharRace charRace;
    private Body body;
    private Attributes attributes;
    private Inventory inventory;
    private ActionPoints actionPoints;

    public CharClass getCharClass(){return charClass;}
    public CharRace getCharRace(){return charRace;}
    public Body getBody(){return body;}
    public Attributes getAttributes(){return attributes;}
    public Inventory getInventory(){return inventory;}
    public ActionPoints getActionPoints() {return actionPoints;}

    public BattleStats getBattleStats(){
        return new BattleStats(getAttributes().getHealth(), getAttributes().getStrength(), getAttributes().getEndurance(), getAttributes().getDexterity());
    }
    //Equipment equipment;
    //Inventory inventory;
}

class Attributes{
    public Attributes(){
        this.health = 100;
        this.mana = 100;
        this.capacity = 100;
        this.strength = 10;
        this.dexterity = 10;
        this.endurance = 10;
    }

    private int health;
    private int mana;
    private int strength;
    private int dexterity;
    private int endurance;
    private int capacity;


    public int getStrength() {
        return strength;
    }
    public int getDexterity() {
        return dexterity;
    }
    public int getEndurance() {
        return endurance;
    }
    public int getHealth(){return this.health;}
    public int getMana(){return this.mana;}
    public int getCapacity(){return this.capacity;}
}

class ActionPoints{
    // Duration/period operates on temporals -> duration operates on instants (instant belongs to Temporal)
    public static final int MAX_AP = 9;
    public static final int AP_RECOVERY_TIME = 30;

    public ActionPoints(){
        this.action_points = ActionPoints.MAX_AP;
    }
    private ArrayList<Instant> timeList = new ArrayList<>();
    private int action_points;

    public void addCooldown(int number) throws NotEnoughActionPointsException{
        if ((getCurrentAP() - number) >= 0) {
            for (int i = 0; i < number; i++)
                timeList.add(Instant.now().plusSeconds(AP_RECOVERY_TIME));
        }
        else{
            throw new NotEnoughActionPointsException(String.valueOf(number));
        }
    }
    public int getCurrentAP(){
        cleanNegativeCooldowns();
        return (MAX_AP - timeList.size());
    }

    public String getStringCooldowns(){
        cleanNegativeCooldowns();
        StringBuilder cooldownsBuilder = new StringBuilder("[");
        Iterator<Instant> cooldownIterator = timeList.iterator();
        while(cooldownIterator.hasNext()){
            Duration duration = Duration.between(Instant.now(), cooldownIterator.next());
            cooldownsBuilder.append(duration.getSeconds()+1);
            if (cooldownIterator.hasNext()){
                cooldownsBuilder.append(", ");
            }
        }
        cooldownsBuilder.append("]");
        return cooldownsBuilder.toString();
    }
    public void printCooldowns(){
        cleanNegativeCooldowns();
        Iterator<Instant> cooldownIterator = timeList.iterator();
        while(cooldownIterator.hasNext()){
            Duration duration = Duration.between(Instant.now(), cooldownIterator.next());
            if (duration.isPositive()){
                System.out.println("seconds of duration: " + duration.getSeconds());
            }
            else;
        }
    }
    private void cleanNegativeCooldowns(){
        Iterator<Instant> cooldownIterator = timeList.iterator();
        while(cooldownIterator.hasNext()){
            if (Instant.now().compareTo(cooldownIterator.next()) >= 0){
                cooldownIterator.remove();
            }
            else;
        }
    }
}

class NotEnoughActionPointsException extends Exception{
    public NotEnoughActionPointsException(String message){
        super(message);
    }
}



/*
interface Comparable<T> {
    int compareTo(T o);
}

class Something implements Comparable<Item>{
    public int compareTo(Item item){
        return 5;
    }
}
*/


/*
interface CharClass{
    int getPointsReqForStrength();
    int getPointsReqForDexterity();
    int getPointsReqForIntelligence();
    //int getMaxHealth(int level);
    //int getMaxMana(int level);
}

class Knight implements CharClass{
    public int getPointsReqForStrength(){
        return 1;
    };
    public int getPointsReqForDexterity(){
        return 2;
    };
    public int getPointsReqForIntelligence(){
        return 3;
    };
}

class Archer implements CharClass{
    public int getPointsReqForStrength(){
        return 2;
    };
    public int getPointsReqForDexterity(){
        return 1;
    };
    public int getPointsReqForIntelligence(){
        return 3;
    };
}
class Mage implements CharClass{
    public int getPointsReqForStrength(){
        return 3;
    };
    public int getPointsReqForDexterity(){
        return 2;
    };
    public int getPointsReqForIntelligence(){
        return 1;
    };
}
*/