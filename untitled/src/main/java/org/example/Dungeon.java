package org.example;

import java.util.ArrayList;
import java.util.Random;


class Dungeon{
    private record MaybeMonster(Monster monster, int probability) {}

    public Dungeon(DungeonBuilder dungeonBuilder){
        this.maybeMonsterList = dungeonBuilder.maybeMonsterList;
    }
    private final ArrayList<MaybeMonster> maybeMonsterList;

    Monster getMonster() throws NoSuchMonsterException {
        int randomDraw = new Random().nextInt(100); // return 0-99
        int additiveProb = 0;
        for (MaybeMonster maybeMonster : maybeMonsterList) {
            additiveProb += maybeMonster.probability();
            if (randomDraw < additiveProb)
                return maybeMonster.monster();
        }
        throw new NoSuchMonsterException();
    }

    /////////// BUILDER
    public static class DungeonBuilder{
        public DungeonBuilder(){
            this.maybeMonsterList = new ArrayList<>();
            this.probability = 0;
        }
        private final ArrayList<MaybeMonster> maybeMonsterList;

        public DungeonBuilder addMonster(Monster monster, int probability){
            this.maybeMonsterList.add(new Dungeon.MaybeMonster(monster, sumProbability(probability)));
            return this;
        }

        private int probability;
        private int sumProbability(int number){
            this.probability += number;
            if (probability > 100)
                throw new IllegalArgumentException("Probability for dungeon reached over 100");
            return number;
        }

        public Dungeon build(){
            return new Dungeon(this);
        }
    }
}

class DungeonManager{
    public static final Dungeon CAVE = new Dungeon.DungeonBuilder().addMonster(MonsterManager.RAT, 50).build();

    public static final Dungeon FOREST = new Dungeon.DungeonBuilder()
            .addMonster(MonsterManager.RAT, 30)
            .addMonster(MonsterManager.BUTTERFLY, 30)
            .addMonster(MonsterManager.SQUIRREL, 10)
            .addMonster(MonsterManager.WOLF, 10)
            .addMonster(MonsterManager.BEAR, 14)
            .addMonster(MonsterManager.DRAGON, 5)
            .build();

    public static final Dungeon SERVER = new Dungeon.DungeonBuilder()
            .addMonster(MonsterManager.FINGERHOOD, 50)
            .addMonster(MonsterManager.GABRIELA, 50)
            .build();

    public static final Dungeon CHESTPLACE = new Dungeon.DungeonBuilder().addMonster(MonsterManager.CHEST, 100).build();

    public static final Dungeon SHIP = new Dungeon.DungeonBuilder().addMonster(MonsterManager.SQUIRREL, 100).build();

    public static final Dungeon LAKE = new Dungeon.DungeonBuilder().addMonster(MonsterManager.DOLPHIN, 1).build();


}




//one class for different Dungeons (to read names by it)
//Dungeon Manager list function to get the Dungeon based off the command
//Dungeon getDungeon(String command){
//
//}