package org.example;

class ManagerItem {
    //EQUIPMENT

    //VARIOUS CLOTHES
    public final static Item BEANIE = new Helmet("Beanie", 1, 5, 1);
    public final static Item SANTA_HAT = new Helmet("Santa hat", 1, 50, 1);



    //STEEL SET (LIGHTER THAN IRON)
    public final static Item STEEL_HELMET = new Helmet("Steel helmet", 3, 25, 3);
    public final static Item STEEL_ARMOR = new Armor("Steel armor", 15, 55, 10);
    public final static Item STEEL_TROUSERS = new Trousers("Steel trousers", 10, 35, 7);
    public final static Item STEEL_GLOVES = new Gloves("Steel gloves", 3, 10, 3);
    public final static Item STEEL_BOOTS = new Boots("Steel boots", 4, 25, 3);
    public final static Item STEEL_SHIELD = new Shield("Steel shield", 5, 45, 6);
    public final static Item STEEL_SWORD = new Weapon("Steel sword", 3, 45, 10);

    //IRON SET
    public final static Item IRON_HELMET = new Helmet("Iron helmet", 2, 15, 2);
    public final static Item IRON_ARMOR = new Armor("Iron armor", 5, 35, 3);
    public final static Item IRON_TROUSERS = new Trousers("Iron trousers", 3, 25, 2);
    public final static Item IRON_GLOVES = new Gloves("Iron gloves", 1, 5, 1);
    public final static Item IRON_BOOTS = new Boots("Iron boots", 1, 15, 1);
    public final static Item IRON_SHIELD = new Shield("Iron shield", 2, 25, 4);

    //LEATHER SET
    public final static Item LEATHER_HELMET = new Helmet("Leather helmet", 2, 15, 2);
    public final static Item LEATHER_ARMOR = new Armor("Leather armor", 5, 35, 3);
    public final static Item LEATHER_TROUSERS = new Trousers("Leather trousers", 3, 25, 2);
    public final static Item LEATHER_GLOVES = new Gloves("Leather gloves", 1, 5, 1);
    public final static Item LEATHER_BOOTS = new Boots("Leather boots", 1, 15, 1);
    public final static Item LEATHER_SHIELD = new Shield("Leather shield", 2, 25, 4);

    //WEAPONS
    public final static Item KNIFE = new Weapon("Knife", 1, 5, 3);


    //DOLPHIN SET
    public final static Item DOLPHIN_SHIELD = new Shield("Dolphin shield", 3, 155, 16);

    //NON-EQUIPMENT
    public final static Item NO_HELMET = new NoHelmet("-", 0, 0, 0);
    public final static Item NO_ARMOR = new NoArmor("-", 0, 0, 0);
    public final static Item NO_TROUSERS = new NoTrousers("-", 0, 0, 0);
    public final static Item NO_GLOVES = new NoGloves("-", 0, 0, 0);
    public final static Item NO_BOOTS = new NoBoots("-", 0, 0, 0);
    public final static Item NO_SHIELD = new NoShield("-", 0, 0, 1);
    public final static Item NO_WEAPON = new NoWeapon("-", 0, 0, 1);


    //MONEY
    public final static Item GOLD = new Money("Gold", 0, 0);

    //CRAFTING ITEMS
    public final static Item DOLPHIN_FIN = new CraftingItem("Dolphin's fin", 2, 100);
    public final static Item SHEEP_WOOL = new CraftingItem("Sheep wool", 2, 100);

    public final static Item RAT_TAIL = new CraftingItem("Rat's tail", 1, 5);
    public final static Item RAT_MEAT = new CraftingItem("Rat's meat", 3, 3);

    public final static Item BEAR_MEAT = new CraftingItem("Bear's meat", 5, 15);
    public final static Item BEAR_SKIN = new CraftingItem("Bear's skin", 10, 50);
    public final static Item BUTTERFLY_WING = new CraftingItem("Butterfly's wing", 0, 10);

    public final static Item GABRIELAS_TONGUE = new CraftingItem("Gabriela's tongue", 1, 50);
    public final static Item FINGERHOODS_STORY = new CraftingItem("Fingerhood's story", 100, 0);

    public final static Item FEATHER = new CraftingItem("Feather", 0, 1);

}
