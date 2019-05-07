package pl.aleksl.commandLineGame.dao;

import pl.aleksl.commandLineGame.model.Place;
import pl.aleksl.commandLineGame.model.Player;

import java.util.List;
import java.util.Map;

public interface GameDao {

    void prepareSave();

    void saveGame(Player player, int placeId, String saveId);

    Map<String, Object> loadGame(int saveSlot);

    List<String> saves();

    Map<Integer, Place> loadGameMap();
}
