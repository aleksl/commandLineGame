package pl.aleksl.commandLineGame.model;

public class Player extends GameCharacter {

    Weapon weapon;

    public Player() {
    }

    public Player(String name, int healthPoints, int experience, Weapon weapon) {
        this.weapon = weapon;
        this.name = name;
        this.healthPoints = healthPoints;
        this.experience = experience;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}
