package pl.aleksl.commandLineGame.dao;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pl.aleksl.commandLineGame.model.Monster;
import pl.aleksl.commandLineGame.model.Place;
import pl.aleksl.commandLineGame.model.Player;
import pl.aleksl.commandLineGame.model.Weapon;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class GameDaoXmlImpl implements GameDao {

    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

    private static Node createPlayerSaveNode(Document doc, Player player, int placeId, String saveId) {
        Element playerSave = doc.createElement("playerSave");
        playerSave.setAttribute("id", saveId);
        playerSave.appendChild(createTextElement(doc, "name", player.getName()));
        playerSave.appendChild(createTextElement(doc, "hp", String.valueOf(player.getHealthPoints())));
        playerSave.appendChild(createTextElement(doc, "exp", String.valueOf(player.getExperience())));
        playerSave.appendChild(createTextElement(doc, "weapon", player.getWeapon().label));
        playerSave.appendChild(createTextElement(doc, "lastPlaceId", String.valueOf(placeId)));
        return playerSave;
    }

    private static Node createTextElement(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    private static Player getPlayerSaved(Node node) {
        Player player = new Player();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            player.setName(getTagValue("name", element));
            player.setHealthPoints(Integer.parseInt(getTagValue("hp", element)));
            player.setExperience(Integer.parseInt(getTagValue("exp", element)));
            player.setWeapon(Weapon.valueOfLabel(getTagValue("weapon", element)));
        }
        return player;
    }

    private static Integer getPlayerPlaceIdSaved(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            return Integer.parseInt(element.getAttribute("id"));
        }
        return null;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }


    private void saveXML(Document doc, boolean override) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transf = null;
        try {
            transf = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transf.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        File saves = new File("commandLineSaves.xml");
        if (saves.exists() && !override) {
            return;
        }
        StreamResult file = new StreamResult(saves);
        try {
            transf.transform(source, file);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void prepareSave() {
        try {
            DocumentBuilder builder = dbFactory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element root = doc.createElementNS("pl.aleksl.commandLineGame", "saves");
            doc.appendChild(root);
            root.appendChild(createPlayerSaveNode(doc, new Player("Player1", 100, 0, Weapon.SWORD), 1, "1"));
            root.appendChild(createPlayerSaveNode(doc, new Player("Player2", 100, 0, Weapon.SWORD), 1, "2"));
            root.appendChild(createPlayerSaveNode(doc, new Player("Player3", 100, 0, Weapon.SWORD), 1, "3"));
            saveXML(doc, false);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveGame(Player player, int placeId, String saveId) {
        try {
            DocumentBuilder builder = dbFactory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element root = doc.createElementNS("pl.aleksl.commandLineGame", "saves");
            doc.appendChild(root);
            root.appendChild(createPlayerSaveNode(doc, player, placeId, saveId));
            saveXML(doc, true);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> loadGame(int saveSlot) {
        Map<String, Object> result = new HashMap<>(1);
        File saves = new File("commandLineSaves.xml");
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(saves);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("playerSave");
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (getPlayerPlaceIdSaved(nodeList.item(i)).intValue() == saveSlot) {
                    result.put("player", getPlayerSaved(nodeList.item(i)));
                    result.put("lastPlace", getPlayerPlaceIdSaved(nodeList.item(i)));
                    break;
                }
            }

        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
        return result;
    }

    @Override
    public List<String> saves() {
        List<String> savesList = new ArrayList<>();
        File saves = new File("commandLineSaves.xml");
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(saves);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("playerSave");
            for (int i = 0; i < nodeList.getLength(); i++) {
                savesList.add(getPlayerPlaceIdSaved(nodeList.item(i)).intValue() + ") " + ((Player) getPlayerSaved(nodeList.item(i))).getName());
            }

        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
        return savesList;
    }

    @Override
    public Map<Integer, Place> loadGameMap() {
        //TODO load from xml
        Map<Integer, Place> allPlaces = new HashMap<>();
        Place carlin = new Place();
        carlin.setPlaceId(1);
        carlin.setPlaceName("Carlin");

        Place thais = new Place();
        thais.setPlaceId(3);
        thais.setPlaceName("Thais");

        Place venore = new Place();
        venore.setPlaceId(4);
        venore.setPlaceName("Venore");

        Place kazor = new Place();
        kazor.setPlaceId(2);
        kazor.setPlaceName("Kazor");

        carlin.getLinkedPlaces().addAll(Arrays.asList(2, 3));
        thais.getLinkedPlaces().addAll(Arrays.asList(2, 1, 4));
        kazor.getLinkedPlaces().addAll(Arrays.asList(3, 1, 4));
        venore.getLinkedPlaces().addAll(Arrays.asList(2, 3));

        List<Monster> carlinMonster = new ArrayList<>();
        carlinMonster.add(new Monster("Rat", 5, 10, 5));
        carlin.setMonsterToKill(carlinMonster);

        List<Monster> thaisMonster = new ArrayList<>();
        thaisMonster.add(new Monster("BiggerRat", 5, 15, 10));
        // thaisMonster.add(new Monster("BiggerRat", 20, 15, 10));
        thais.setMonsterToKill(thaisMonster);

        List<Monster> kazorMonster = new ArrayList<>();
        kazorMonster.add(new Monster("BiggestRat", 5, 20, 10));
        // kazorMonster.add(new Monster("BiggestRat", 25, 20, 10));
        // kazorMonster.add(new Monster("BiggestRat", 25, 20, 10));
        kazor.setMonsterToKill(kazorMonster);

        List<Monster> venoreMonster = new ArrayList<>();
        venoreMonster.add(new Monster("DemonRat", 5, 100, 10));
        venore.setMonsterToKill(venoreMonster);
        allPlaces.put(1, carlin);
        allPlaces.put(2, kazor);
        allPlaces.put(3, thais);
        allPlaces.put(4, venore);
        return allPlaces;
    }

}
