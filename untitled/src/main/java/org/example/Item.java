package org.example;


abstract class Item{
    protected abstract String getName();
    protected abstract int getWeight();
    protected abstract int getValue();
}

abstract class Helmet extends Item{}
abstract class Armor extends Item{}
abstract class Trousers extends Item{}
abstract class Gloves extends Item{}
abstract class Boots extends Item{}
abstract class Weapon extends Item{}
abstract class Shield extends Item{}

class SteelHelmet extends Helmet{
    protected String getName(){
        return "steel helmet";
    };
    protected int getWeight(){
        return 5;
    };
    protected int getValue(){
        return 25;
    };
}

class SteelArmor extends Armor{
    protected String getName(){
        return "steel armor";
    };
    protected int getWeight(){
        return 15;
    };
    protected int getValue(){
        return 55;
    };
}

class SteelTrousers extends Trousers{
    protected String getName(){
        return "steel trousers";
    };
    protected int getWeight(){
        return 10;
    };
    protected int getValue(){
        return 35;
    };
}

class SteelGloves extends Gloves{
    protected String getName(){
        return "steel gloves";
    };
    protected int getWeight(){
        return 3;
    };
    protected int getValue(){
        return 10;
    };
}

class SteelBoots extends Boots{
    protected String getName(){
        return "steel boots";
    };
    protected int getWeight(){
        return 4;
    };
    protected int getValue(){
        return 25;
    };
}

class SteelSword extends Weapon{
    protected String getName(){
        return "steel sword";
    };
    protected int getWeight(){
        return 3;
    };
    protected int getValue(){
        return 45;
    };
}

class SteelShield extends Shield{
    protected String getName(){
        return "steel shield";
    };
    protected int getWeight(){
        return 5;
    };
    protected int getValue(){
        return 45;
    };
}


class Gold extends Item{
    @Override
    protected String getName(){
        return "gold";
    }
    @Override
    protected int getWeight(){
        return 1;
    }
    protected int getValue(){
        return 1;
    };
}

class DolphinFin extends Item{
    @Override
    protected String getName(){
        return "dolphin's fin :dolphin:";
    }
    @Override
    protected int getWeight(){
        return 2;
    }
    protected int getValue(){
        return 100;
    };
}