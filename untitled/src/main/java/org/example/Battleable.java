package org.example;

public interface Battleable{
    BattleStats getBattleStats();
    //void updateAfterBattle(BattleResult battleResult);
}

class BattleStats{
    public BattleStats(int health, int attack, int defense, int speed){
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }
    private int health;
    private int attack;
    private int defense;
    private int speed;

    public int getHealth(){return health;}
    public int getAttack(){return attack;}
    public int getDefense(){return defense;}
    public int getSpeed(){return speed;}
}

class BattleResult{
    private int winner;

    public void setWinner(int winner) {
        this.winner = winner;
    }


    private String battleString;
    private int hp;
    public int getHp(){
        return hp;
    };
}

class Battle{
    public Battle(Battleable fighterOne, Battleable fighterTwo){
        this.fighterOne = fighterOne;
        this.fighterTwo = fighterTwo;
    }
    Battleable fighterOne;
    Battleable fighterTwo;

    public String start(){
        BattleStats battleStatsOne = fighterOne.getBattleStats();
        BattleStats battleStatsTwo = fighterTwo.getBattleStats();

        int oneAttackStat = battleStatsOne.getAttack();
        int twoAttackStat = battleStatsTwo.getAttack();

        if (oneAttackStat >= twoAttackStat){ return "one wins against two";}
        else if (oneAttackStat < twoAttackStat){ return "two wins against one";}

        return "battle ended";
    }
}

