package pl.aleksl.commandLineGame;

import pl.aleksl.commandLineGame.dao.GameDaoXmlImpl;
import pl.aleksl.commandLineGame.service.Game;
import pl.aleksl.commandLineGame.service.GameImpl;

public class Main {

    public static void main(String[] args) {
        Game newGame = new GameImpl(new GameDaoXmlImpl());
        newGame.startGame();
    }
}
