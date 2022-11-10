package org.example;

import java.util.ArrayList;
import java.util.Random;

interface Fighter{
    int getCombatPower();
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
    }

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



/*
class SmallMonster extends Monster{
    private int combatPower;

    public SmallMonster (String name, int combatPower){
        super(name);
        this.combatPower = combatPower;
    }

    @Override
    public int getCombatPower() {
        return combatPower;
    }

    @Override
    public Properties getProperties() {
        return null;
    }

    @Override
    public Weaknesses getWeaknesses() {
        return null;
    }
}
 */


class Dungeon{
    //public Dungeon(ArrayList<MonsterChancer> monsterChancerArrayList, int totalProbability){
    //    this.monsterChancerArrayList = monsterChancerArrayList;
    //    this.totalProbability = totalProbability;
    //}

    public Dungeon(DungeonBuilder dungeonBuilder){
        this.monsterChancerArrayList = dungeonBuilder.monsterChancerArrayList;
        this.totalProbability = dungeonBuilder.totalProbability;
    }
    //public Dungeon(MonsterChancer... monsterChancers){
    //    for (MonsterChancer monsterChancer : monsterChancers) {
    //        this.monsterChancerArrayList.add(monsterChancer);
    //    }
    //    totalProbability = 0;
    //}

    private int totalProbability;
    private ArrayList<MonsterChancer> monsterChancerArrayList;

    Monster getMonster(){
        Random random = new Random();
        int randomInt = random.nextInt(100); // return 0-99
        int addedProb = 0;
        for (int i = 0; i < monsterChancerArrayList.size(); i++){
            addedProb += monsterChancerArrayList.get(i).getProbability();
            if (randomInt < addedProb){
                return monsterChancerArrayList.get(i).getMonster();
            }
        }
        return null;
    }

    public static class DungeonBuilder{
        public DungeonBuilder(){
            this.monsterChancerArrayList = new ArrayList<>();
            this.totalProbability = 0;
        }
        // make a dungeon builder
        private int totalProbability;
        private ArrayList<MonsterChancer> monsterChancerArrayList;

        private int addToTotalProbability(int number){
            totalProbability += number;
            if (totalProbability > 100)
                throw new IllegalArgumentException("Probability for dungeon reached over 100");
            return number;
        }

        public DungeonBuilder addMonster(Monster monster, int probability){
            this.monsterChancerArrayList.add(new MonsterChancer(monster, addToTotalProbability(probability)));
            return this;
        }

        public Dungeon build(){
            //return new Dungeon(monsterChancerArrayList, totalProbability);
            return new Dungeon(this);
        }
    }
}

class MonsterChancer{
    public MonsterChancer(Monster monster, int probability){
        this.monster = monster;
        this.probability = probability;
    }
    private Monster monster;
    private int probability;

    public Monster getMonster() {
        return monster;
    }

    public int getProbability(){
        return probability;
    }
}

class Abc{
    private int b;

    public class Bcd{
        public Bcd(){
            super();
        }
        private int c;

        private class Adsf{
            public Adsf(Abc abc){
                d = abc.b;
            }

            private int d;
            private void doSomething(){
                System.out.println("asdasd");
            }
        }
    }
}

class Unicodeeee extends Abc{
    public Unicodeeee(){
        Abc abc = new Abc();
        Bcd asd = abc.new Bcd();
    }
}



/*
class Bcd{
    int c;
    public Bcd(Abc abc){
        c = abc.b;
    }
}

 */

class Monster implements Fighter, Lootable{
    public Monster(MonsterBuilder monsterBuilder){
        this.name = monsterBuilder.name;
        this.combatPower = monsterBuilder.combatPower;
        this.description = monsterBuilder.description;
        this.lootChanceList = monsterBuilder.lootChanceList;
    }

    @Override
    public ArrayList<Item> loot() {
        return null;
    }
    private ArrayList<ItemChance> lootChanceList;
    public ArrayList<ItemChance> getLootChanceList() {
        return lootChanceList;
    }

    public ArrayList<Item> getRandomLoot(){
        ArrayList<Item> loot = new ArrayList<>();
        for (ItemChance itemChance : lootChanceList) {
            Item newPossibleItem = Chancer.drawItem(itemChance.getItem(), itemChance.getProbability());
            if (newPossibleItem != null)
                loot.add(newPossibleItem);
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


    //public int vulnerabilityFire;
    //public int vulnerabilityIce;
    //public int vulnerabilitySlash;
    //public int vulnerabilityPierce;
    //public int vulnerabilityBash;

    public static class MonsterBuilder{
        public MonsterBuilder(String name, int combatPower){
            this.name = name;
            this.combatPower = combatPower;
            this.description = "empty";
            this.lootChanceList = new ArrayList<>();
        }
        private String name;
        private int combatPower;

        private String description;
        public MonsterBuilder addDescription(String description){
            this.description = description;
            return this;
        }

        private ArrayList<ItemChance> lootChanceList;
        public MonsterBuilder addLoot(Item item, int probability){
            lootChanceList.add(new ItemChance(item, probability));
            return this;
        }


        public Monster build(){
            return new Monster(this);
        }
    }
}

class ItemChance{
    public ItemChance(Item item, int probability){
        this.item = item;
        this.probability = probability;
    }

    private Item item;
    public Item getItem() {
        return item;
    }

    private int probability;
    public int getProbability() {
        return probability;
    }
}

class MonsterManager{
    public static final Monster RAT = new Monster
            .MonsterBuilder("Rat",15)
            .addLoot(ItemManager.RAT_MEAT, 70)
            .addLoot(ItemManager.RAT_TAIL, 30)
            .addDescription("Little, annoying mammal. Likes to live near people and steal their food.")
            .build();
    public static final Monster GABRIELA = new Monster
            .MonsterBuilder("Gabriela",1)
            .addLoot(ItemManager.GABRIELAS_TONGUE, 40)
            .addDescription("Proud resident of Brazil")
            .build();
    public static final Monster FINGERHOOD = new Monster
            .MonsterBuilder("Fingerhood",50)
            .addLoot(ItemManager.FINGERHOODS_STORY, 100)
            .addDescription("Talking admin")
            .build();

    public static final Monster SQUIRREL = new Monster.MonsterBuilder("Squirrel",5).addDescription("Jumps on the trees. Eats (it's) nuts.").build();
    public static final Monster BUTTERFLY = new Monster.MonsterBuilder("Butterfly",1)
            .addLoot(ItemManager.BUTTERFLY_WING, 60)
            .addDescription("Flying little thing. Pretty and comes in various colours.").build();
    public static final Monster WOLF = new Monster.MonsterBuilder("Wolf", 50).addDescription("An angry, dog-like beast from the forest. Likes to move in packs.").build();
    public static final Monster BEAR = new Monster
            .MonsterBuilder("Bear", 500)
            .addLoot(ItemManager.BEAR_MEAT, 70)
            .addLoot(ItemManager.BEAR_SKIN, 30).addDescription("A wild mammal with a large body and deadly claws.").build();
    public static final Monster DRAGON = new Monster.MonsterBuilder("Dragon", 5000).addDescription("Immense monster covered in thick leather and scales that breathes fire.").build();
    public static final Monster DOLPHIN = new Monster.MonsterBuilder("Dolphin", 200).addDescription("Swimmer.").build();
    /*
    public static final Monster HAWK = new Monster("hawk", 50);
    public static final Monster BEAVER = new Monster("beaver", 15);
    public static final Monster HAMSTER = new Monster("hamster", 5);
    public static final Monster TROLL = new Monster("troll", 150);
    public static final Monster RED_WOLF = new Monster("red wolf", 30);
    public static final Monster SNAKE = new Monster("snake", 30);
    public static final Monster DOMESTIC_PIG = new Monster("domestic pig", 30);
    public static final Monster WILD_BOAR = new Monster("wild boar", 30);
    public static final Monster TIGER = new Monster("tiger", 30);
    public static final Monster LION = new Monster("lion", 30);
    public static final Monster PALLAS_CAT = new Monster("pallas cat", 30);
    public static final Monster IMP = new Monster("imp", 200);
    public static final Monster CATFISH = new Monster("catfish", 400);
    public static final Monster CRAZY_FRIDGE = new Monster("crazy fridge", 1000);
    public static final Monster STARFISH = new Monster("starfish", 10);
    public static final Monster SHARK = new Monster("shark", 50);
    public static final Monster COSSACK = new Monster("cossack", 50);
    public static final Monster PIRATE = new Monster("pirate", 50);
    public static final Monster PIGEON = new Monster("pigeon", 50);
    public static final Monster BISON = new Monster("bison", 50);
    public static final Monster BANDIT = new Monster("bandit", 50);
    public static final Monster THIEF = new Monster("thief", 50);
*/
}

class DungeonManager{
    public static final Dungeon CAVE = new Dungeon.DungeonBuilder().addMonster(MonsterManager.RAT, 50).build();
    /*
    public static final Dungeon FOREST = new Dungeon.DungeonBuilder()
            .addMonster(MonsterManager.RAT, 30)
            .addMonster(MonsterManager.BUTTERFLY, 30)
            .addMonster(MonsterManager.SQUIRREL, 10)
            .addMonster(MonsterManager.WOLF, 10)
            .addMonster(MonsterManager.BEAR, 14)
            .addMonster(MonsterManager.DRAGON, 5)
            .build();

     */
    public static final Dungeon FOREST = new Dungeon.DungeonBuilder()
            .addMonster(MonsterManager.FINGERHOOD, 50)
            .addMonster(MonsterManager.GABRIELA, 50)
            .build();
    public static final Dungeon SHIP = new Dungeon.DungeonBuilder().addMonster(MonsterManager.SQUIRREL, 100).build();
    public static final Dungeon LAKE = new Dungeon.DungeonBuilder().addMonster(MonsterManager.DOLPHIN, 1).build();

    public static final Dungeon BRAZIL = new Dungeon.DungeonBuilder().addMonster(MonsterManager.GABRIELA, 1).build();
}


//weather_type //environment conditions