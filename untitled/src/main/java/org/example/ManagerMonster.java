package org.example;

class ManagerMonster {
    public static final Monster ENORMOUS_RAT = new Monster
            .MonsterBuilder("Enormous rat", 15, 30, 2, 3, 1, 1)
            .addLoot(ManagerItem.RAT_MEAT, 70)
            .addLoot(ManagerItem.RAT_TAIL, 30)
            .addDescription("This rat is really huge. And smart. Likes to live near people and steal their food.")
            .build();

    public static final Monster GABRIELA = new Monster
            .MonsterBuilder("Gabriela", 1, 100, 2, 4,4, 3)
            .addLoot(ManagerItem.GABRIELAS_TONGUE, 40)
            .addLoot(ManagerItem.GABRIELAS_NAIL, 50)
            .addDescription("Wild brazilian woman. Her sharp nails can cut right through your skin.")
            .build();

    public static final Monster FINGERHOOD = new Monster
            .MonsterBuilder("Fingerhood", 50, 100, 1, 2,1, 1)
            .addLoot(ManagerItem.FINGERHOODS_STORY, 100)
            .addDescription("Talking admin. Always has a story to tell.")
            .build();

    public static final Monster SQUIRREL = new Monster
            .MonsterBuilder("Squirrel", 5, 20, 1, 4,2, 13)
            .addDescription("Jumps on the trees. Eats (it's) nuts.")
            .build();

    public static final Monster BUTTERFLY = new Monster
            .MonsterBuilder("Butterfly", 1, 3, 1, 1,1, 100)
            .addLoot(ManagerItem.BUTTERFLY_WING, 60)
            .addDescription("Flying little thing. Pretty and comes in various colours.")
            .build();

    public static final Monster WOLF = new Monster
            .MonsterBuilder("Wolf", 50, 70, 5, 7,6, 10)
            .addDescription("An angry, dog-like beast from the forest. Likes to move in packs.")
            .build();

    public static final Monster BEAR = new Monster
            .MonsterBuilder("Bear", 500, 200, 8, 13, 25, 5)
            .addLoot(ManagerItem.BEAR_MEAT, 70)
            .addLoot(ManagerItem.BEAR_SKIN, 30)
            .addDescription("A wild mammal with a large body and deadly claws.")
            .build();

    public static final Monster DRAGON = new Monster.MonsterBuilder("Dragon", 5000, 960, 47, 64,70, 53).addDescription("Immense monster covered in thick leather and scales that breathes fire.").build();

    public static final Monster DOLPHIN = new Monster.MonsterBuilder("Dolphin", 200, 10, 1, 2,1, 1).addDescription("Swimmer.").build();

    public static final Monster CHEST = new Monster.MonsterBuilder("Chest", 0, 1, 1, 1,1, 1).addDescription("Receptacle with treasuries.")
            .addLoot(ManagerItem.STEEL_SWORD, 60)
            .addLoot(ManagerItem.STEEL_HELMET, 20)
            .addLoot(ManagerItem.DOLPHIN_SHIELD, 60)
            .addLoot(ManagerItem.STEEL_SHIELD, 60)
            .addLoot(ManagerItem.DOLPHIN_FIN, 25)
            .addLoot(ManagerItem.SHEEP_WOOL, 25)
            .build();

    public static final Monster CHICK = new Monster.MonsterBuilder("Chick", 1, 20, 1, 3,1, 4).addDescription("A small chicken that came right out of an egg.")
            .addLoot(ManagerItem.STEEL_SWORD, 10)
            .addLoot(ManagerItem.FEATHER, 80)
            .build();

    public static final Monster SMALL_ROOSTER = new Monster.MonsterBuilder("Small rooster", 2, 30, 1, 5,1, 6).addDescription("Small cock, a chicken defender.")
            .addLoot(ManagerItem.STEEL_SWORD, 10)
            .addLoot(ManagerItem.FEATHER, 80)
            .build();

    public static final Monster EGG_MONSTER = new Monster.MonsterBuilder("Eggster", 3, 40, 2, 5, 2,7).addDescription("Strange creature made of eggs. It smells badly.")
            .addLoot(ManagerItem.STEEL_SWORD, 10)
            .build();

    public static final Monster CHICK_LORD = new Monster.MonsterBuilder("Chick lord", 5, 50, 3, 5,3, 7).addDescription("Lord among chicks. Has a sharp yellow beak.")
            .addLoot(ManagerItem.STEEL_SWORD, 10)
            .addLoot(ManagerItem.FEATHER, 80)
            .build();
    /*
          Chancer.chanceItem(newItems, ItemManager.STEEL_SWORD, 60);
        Chancer.chanceItem(newItems, ItemManager.STEEL_HELMET, 20);
        Chancer.chanceItem(newItems, ItemManager.STEEL_ARMOR, 20);
        Chancer.chanceItem(newItems, ItemManager.STEEL_SHIELD, 60);
        Chancer.chanceItem(newItems, ItemManager.DOLPHIN_SHIELD, 60);
        Chancer.chanceItem(newItems, ItemManager.DOLPHIN_FIN, 25);
        Chancer.chanceItem(newItems, ItemManager.SHEEP_WOOL, 25);
     */
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
