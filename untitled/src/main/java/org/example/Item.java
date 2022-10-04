package org.example;


abstract class Item{
    protected abstract String getName();
    protected abstract int getWeight();
}

abstract class Helmet extends Item{}
abstract class Armor extends Item{}
abstract class Trousers extends Item{}
abstract class Gloves extends Item{}
abstract class Boots extends Item{}

class SteelHelmet extends Helmet{
    protected String getName(){
        return "steel helmet";
    };
    protected int getWeight(){
        return 5;
    };
}

class SteelArmor extends Armor{
    protected String getName(){
        return "steel armor";
    };
    protected int getWeight(){
        return 15;
    };
}

class SteelTrousers extends Trousers{
    protected String getName(){
        return "steel trousers";
    };
    protected int getWeight(){
        return 10;
    };
}

class SteelGloves extends Gloves{
    protected String getName(){
        return "steel gloves";
    };
    protected int getWeight(){
        return 3;
    };
}

class SteelBoots extends Boots{
    protected String getName(){
        return "steel boots";
    };
    protected int getWeight(){
        return 4;
    };
}



class Gold extends Item{
    @Override
    protected String getName(){
        return "gold";
    }
    @Override
    protected int getWeight(){
        return 2;
    }
}

