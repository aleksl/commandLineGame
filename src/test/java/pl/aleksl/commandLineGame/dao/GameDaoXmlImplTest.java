package pl.aleksl.commandLineGame.dao;

import org.junit.Before;
import org.junit.Test;
import pl.aleksl.commandLineGame.model.Place;
import pl.aleksl.commandLineGame.model.Player;
import pl.aleksl.commandLineGame.model.Weapon;

import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GameDaoXmlImplTest {

    public GameDao gameDao;
    private Map<String, Object> loadedGame;
    private Map<Integer, Place> mapPlaces;

    @Before
    public void setUp(){
        gameDao = new GameDaoXmlImpl();
        mapPlaces = gameDao.loadGameMap();
        loadedGame = gameDao.loadGame(1);
        createSaveFile();
    }

    public void createSaveFile(){
        Player player = new Player();
        player.setName("Test");
        player.setHealthPoints(100);
        player.setExperience(0);
        player.setWeapon(Weapon.SWORD);
        gameDao.saveGame(player, 1, "1");
    }

    @Test
    public void shouldReturnMapPlacesOnInitGame(){
        assertTrue("Init game have any places on map", !mapPlaces.isEmpty());
    }

    @Test
    public void shouldReturnMapWithPlayerAndPlaceIdOnLoadGame(){
        assertTrue("Loaded slot have info about player", loadedGame.containsKey("player"));
        assertTrue("Loaded slot have info about last place", loadedGame.containsKey("lastPlace"));
    }

    @Test
    public void shouldReturnAllPlayerInfoOnLoadGame(){
        assertTrue("Loaded slot have info about player", loadedGame.containsKey("player"));
        Player player = (Player) loadedGame.get("player");
        assertTrue("Loaded player have name", !player.getName().isEmpty());
        assertTrue("Loaded player have more than 0 hp", player.getHealthPoints() > 0);
        assertTrue("Loaded player have any experience or 0", player.getExperience() >= 0);
        assertNotNull("Loaded player have weapon", player.getWeapon());
        assertNotNull("Loaded player have weapon currently exits in game", Weapon.valueOfWeaponNumber(player.getWeapon().weaponNumber));
    }

    @Test
    public void shouldReturnExistedPlaceIdOnLoadGame(){
        assertTrue("Loaded slot have info about last place", loadedGame.containsKey("lastPlace"));
        int placeId = (Integer) loadedGame.get("lastPlace");
        assertTrue("Loaded placeId exist on Map places", mapPlaces.containsKey(placeId));
    }
}
