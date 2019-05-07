package pl.aleksl.commandLineGame.service;

import org.junit.Before;
import org.junit.Test;
import pl.aleksl.commandLineGame.dao.GameDaoXmlImpl;

import static org.junit.Assert.assertTrue;

public class GameImplTest {

    private GameImpl game;

    @Before
    public void setUp(){
        game = new GameImpl(new GameDaoXmlImpl());
        game.initGame();
    }

    @Test
    public void shouldReturnIntLessOrEqualTen(){
        int randomNumber = game.getRandomNumber();
        assertTrue("Random number should less or equal 10", randomNumber <= 10);
    }

    @Test
    public void shouldReturnTrueForCorrectMath(){
        assertTrue(" Multiplication should return true for correct result", game.multiplicationTask(3,3,9));
    }

    @Test
    public void shouldHaveMapPlaces(){
        assertTrue("Map places loaded", game.allPlaces.size() > 0);
    }

    @Test
    public void shouldReturnZeroAfterKillMonster(){
        assertTrue("Monster hp are 0", game.attackMonster(15, 15) == 0);
    }

}
