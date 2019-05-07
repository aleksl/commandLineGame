package pl.aleksl.commandLineGame.model;

import java.util.ArrayList;
import java.util.List;

public class Place {

    int placeId;
    String placeName;
    List<Integer> linkedPlaces = new ArrayList<>();
    List<Monster> monsterToKill = new ArrayList<>();

    public List<Monster> getMonsterToKill() {
        return monsterToKill;
    }

    public void setMonsterToKill(List<Monster> monsterToKill) {
        this.monsterToKill = monsterToKill;
    }

    public List<Integer> getLinkedPlaces() {
        return linkedPlaces;
    }

    public void setLinkedPlaces(List<Integer> linkedPlaces) {
        this.linkedPlaces = linkedPlaces;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
}
