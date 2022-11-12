package org.example;

class DungeonManager {
    public static final Dungeon CAVE = new Dungeon.DungeonBuilder().addMonster(MonsterManager.RAT, 50).build();

    public static final Dungeon CHICKEN_CAVE = new Dungeon.DungeonBuilder()
            .addMonster(MonsterManager.CHICK, 60)
            .addMonster(MonsterManager.SMALL_ROOSTER, 25)
            .addMonster(MonsterManager.EGG_MONSTER, 10)
            .addMonster(MonsterManager.CHICK_LORD, 5)
            .build();

    /*
    public static final Dungeon LONELY_SWAMP = new Dungeon.DungeonBuilder()
            .addMonster(MonsterManager.FROG_WIFE, 60)
            .addMonster(MonsterManager.SNAKE, 25)
            .build();
     */

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
