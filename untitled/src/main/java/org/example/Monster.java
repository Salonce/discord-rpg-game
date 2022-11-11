package org.example;

import java.util.ArrayList;
import java.util.Random;


class Monster implements Fighter{
    public Monster(MonsterBuilder monsterBuilder){
        this.name = monsterBuilder.name;
        this.combatPower = monsterBuilder.combatPower;
        this.description = monsterBuilder.description;
        this.maybeItemList = monsterBuilder.maybeItemList;
    }

    private ArrayList<MaybeItem> maybeItemList;

    public ArrayList<Item> getRandomLoot(){
        ArrayList<Item> loot = new ArrayList<>();
        for (MaybeItem maybeItem : maybeItemList) {
            Item item = maybeItem.maybeGet();
            if (item != null)
                loot.add(item);
        }
        return loot;
    }

    private String name;
    public String getName(){return this.name;}

    private String description;
    public String getDescription() {
        return description;
    }

    private int combatPower;
    public int getCombatPower(){return this.combatPower;}


    public Properties getProperties(){return null;}
    public Weaknesses getWeaknesses(){return null;}


    public static class MonsterBuilder{
        public MonsterBuilder(String name, int combatPower){
            this.name = name;
            this.combatPower = combatPower;
            this.description = "empty";
            this.maybeItemList = new ArrayList<>();
        }
        private String name;
        private int combatPower;

        private String description;
        public MonsterBuilder addDescription(String description){
            this.description = description;
            return this;
        }

        private ArrayList<MaybeItem> maybeItemList;
        public MonsterBuilder addLoot(Item item, int probability){
            maybeItemList.add(new MaybeItem(item, probability));
            return this;
        }

        public Monster build(){
            return new Monster(this);
        }
    }
    //public int vulnerabilityFire;
    //public int vulnerabilityIce;
    //public int vulnerabilitySlash;
    //public int vulnerabilityPierce;
    //public int vulnerabilityBash;
}

abstract class Maybe<T>{
    private static final Random random = new Random();

    public Maybe(T object, int probability){
        this.object = object;
        this.probability = probability;
    }
    private final T object;
    private final int probability;

    public T maybeGet(){
        if (Maybe.random.nextInt(100) < this.probability)
            return this.object;
        else
            return null;
    }
}

class MaybeItem extends Maybe<Item>{
    public MaybeItem(Item item, int probability) {
        super(item, probability);
    }
}

