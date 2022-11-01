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

class ActionPoints{
    // Duration/period operates on temporals -> duration operates on instants (instant belongs to Temporal)
    public static final int MAX_AP = 19;
    public static final int AP_RECOVERY_TIME = 30;

    public ActionPoints(){
        //this.action_points = ActionPoints.MAX_AP;
    }
    private ArrayList<Instant> timeList = new ArrayList<>();
    //private int action_points;

    private Instant getLastInstant(){
        Instant lastInstant;
        if (!timeList.isEmpty())
            lastInstant = timeList.get(timeList.size() - 1);
        else{
            lastInstant = Instant.now();
        }
        return lastInstant;
    }

    public void addCooldown(int number) throws NotEnoughActionPointsException{
        if ((getCurrentAP() - number) >= 0) {
            for (int i = 0; i < number; i++)
                timeList.add(getLastInstant().plusSeconds(AP_RECOVERY_TIME));
        }
        else{
            throw new NotEnoughActionPointsException(String.valueOf(number));
        }
    }
    public int getCurrentAP(){
        cleanNegativeCooldowns();
        return (MAX_AP - timeList.size());
    }

    public long getFirstCD(){
        cleanNegativeCooldowns();
        try {
            Duration duration = Duration.between(Instant.now(), timeList.get(0));
            if (duration.isPositive()) {
                return duration.getSeconds();
            }
        } catch (IndexOutOfBoundsException e){
            //do nothing
        }
        return 0;
    }
    public long getLastCD(){
        cleanNegativeCooldowns();
        Duration duration = Duration.between(Instant.now(), getLastInstant());
        if (duration.isPositive()){
            return duration.getSeconds();
        }
        return 0;
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


    /////TESTING / LEGACY METHODS
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