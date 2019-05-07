package pl.aleksl.commandLineGame.service;

import pl.aleksl.commandLineGame.model.Monster;
import pl.aleksl.commandLineGame.model.Place;
import pl.aleksl.commandLineGame.model.Player;

public interface Game {

    void startGame();

    boolean fightWithMonster(Player player, Monster monster);

    int attackMonster(int monsterHp, int strengthAttack);

    Player createNewCharacter();

    boolean attackOrMiss();

    boolean exploreToNextPlace(Player player, Place destinationPlace);
}
