package org.example;

public class Body{
    public Body(){
        head = new Head();
        torso = new Torso();
        legs = new Legs();
        feet = new Feet();
    }
    private BodyPart head;
    private BodyPart torso;
    private BodyPart legs;
    private BodyPart feet;
    private BodyPart leftHand;
    private BodyPart rightHand;

    public BodyPart getHead(){
        return this.head;
    }
    public BodyPart getTorso(){
        return this.torso;
    }
    public BodyPart getLegs(){
        return this.legs;
    }
    public BodyPart getFeet(){
        return this.feet;
    }
}

abstract class BodyPart <T extends Item>{
    public abstract String getBodyPartName();
    public abstract String getClothName();
    public abstract void putOn(T item);
    public abstract void takeOff(T item);
    public abstract boolean occupied();
}

class Head extends BodyPart <Helmet>{
    public Head(){
        this.helmet = null;
    }
    private Helmet helmet;

    public String getBodyPartName(){ return "head";}
    public String getClothName(){ return helmet.getName();}
    public void putOn(Helmet helmet){this.helmet = helmet;}
    public void takeOff(Helmet helmet){this.helmet = null;}
    public boolean occupied() { return helmet != null; }
}

class Torso extends BodyPart <Armor>{
    public Torso(){
        this.armor = null;
    }
    private Armor armor;

    public String getBodyPartName(){ return "torso";}
    public String getClothName(){ return armor.getName();}
    public void putOn(Armor armor){this.armor = armor;}
    public void takeOff(Armor armor){this.armor = null;}
    public boolean occupied() {
        if (armor == null)
            return false;
        else
            return true;
    }
}

class Legs extends BodyPart <Trousers>{
    public Legs(){
        this.trousers = null;
    }
    private Trousers trousers;

    public String getBodyPartName(){ return "legs";}
    public String getClothName(){ return trousers.getName();}
    public void putOn(Trousers trousers){this.trousers = trousers;}
    public void takeOff(Trousers trousers){this.trousers = null;}
    public boolean occupied() {
        if (trousers == null)
            return false;
        else
            return true;
    }
}

class Feet extends BodyPart <Boots>{
    public Feet(){
        this.boots = null;
    }
    private Boots boots;

    public String getBodyPartName(){ return "feet";}
    public String getClothName(){ return boots.getName();}
    public void putOn(Boots boots){this.boots = boots;}
    public void takeOff(Boots boots){this.boots = null;}
    public boolean occupied() {
        if (boots == null)
            return false;
        else
            return true;
    }
}



/*

interface Dressable <T extends Item>{
    //void dress(T item);
    String dress(T item);
    void takeoff(T item);
}

class Head extends BodyPart implements Dressable <Helmet>{
    @Override
    public String dress(Helmet helmet){
        return ("Wearing " + helmet.getName());
    };
    @Override
    public void takeoff(Helmet helmet){
        System.out.println("Taking off " + helmet.getName());
    };
}

class Torso extends BodyPart implements Dressable <Armor>{
    @Override
    public String dress(Armor armor){
        return ("Wearing " + armor.getName());
    };
    @Override
    public void takeoff(Armor armor){
        System.out.println("taking off armor");
    };
}

*/