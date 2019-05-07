package pl.aleksl.commandLineGame.model;

public class Monster extends GameCharacter {

    int strengthAttack;

    public Monster(String name, int healthPoints, int experience, int strengthAttack){
        this.name = name;
        this.healthPoints = healthPoints;
        this.experience = experience;
        this.strengthAttack = strengthAttack;
    }


    public int getHealthPoints() {
        return healthPoints;
    }

    public int getStrengthAttack() {
        return strengthAttack;
    }

    public void setStrengthAttack(int strengthAttack) {
        this.strengthAttack = strengthAttack;
    }
}
