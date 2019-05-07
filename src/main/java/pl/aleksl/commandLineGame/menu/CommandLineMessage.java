package pl.aleksl.commandLineGame.menu;

import pl.aleksl.commandLineGame.model.GameCharacter;
import pl.aleksl.commandLineGame.model.Monster;
import pl.aleksl.commandLineGame.model.Place;
import pl.aleksl.commandLineGame.model.Player;

import java.util.List;

public class CommandLineMessage {

    public static void mainMenuMessage() {
        StringBuffer option = new StringBuffer();
        option.append("Select options number").append("\n");
        option.append("1) Start new game").append("\n");
        option.append("2) Load game").append("\n");
        option.append("0) Exit");
        System.out.println(option.toString());
    }

    public static void gameMenuMessage(Place currentPlace, List<Place> places) {
        System.out.println("Welcome in place " + currentPlace.getPlaceName());
        StringBuffer option = new StringBuffer();
        option.append("Choose place:").append("\n");
        for (int i = 0; i < places.size(); i++) {
            option.append(i + 1).append(") ").append(places.get(i).getPlaceName()).append("\n");
        }
        option.append("0) Back to option").append("\n");
        System.out.println(option.toString());
    }

    public static void gameOptionMenuMessage() {
        StringBuffer option = new StringBuffer();
        option.append("Choose the option:").append("\n");
        option.append("1) Explore").append("\n");
        option.append("2) Save game").append("\n");
        option.append("0) Back to menu").append("\n");
        System.out.println(option.toString());
    }


    public static void chooseWeaponForNewPlayerMessage() {
        StringBuffer option = new StringBuffer();
        option.append("Choose weapon:").append("\n");
        option.append("1) Sword").append("\n");
        option.append("2) Axe").append("\n");
        System.out.println(option.toString());
    }

    public static void wrongOptionMessage() {
        System.out.println("Wrong input - try again");
    }

    public static void setUserNameMessage() {
        System.out.println("Enter your name: ");
    }

    public static void helloInGameMessage() {
        System.out.println("Hello in CommandLine game");
    }

    public static void endGameMessage() {
        System.out.println("End");
    }

    public static void gameCharacterMessage(GameCharacter character) {
        System.out.println(character.getName() + " hp:" + character.getHealthPoints() + " exp:" + character.getExperience());
    }

    public static void gameOver() {
        StringBuffer option = new StringBuffer();
        option.append("--------- Game over ---------").append("\n");
        System.out.println(option.toString());
    }

    public static void displayPlayerAndMonsterMessage(Player player, Monster monster) {
        CommandLineMessage.gameCharacterMessage(player);
        CommandLineMessage.gameCharacterMessage(monster);
    }

    public static void displayPlayerAndGameMenuMessage(Player player, Place currentPlace, List<Place> places) {
        CommandLineMessage.gameCharacterMessage(player);
        CommandLineMessage.gameMenuMessage(currentPlace, places);
    }

    public static void displayMathTask(int numberA, int numberB) {
        System.out.print(numberA + "*" + numberB + "=");
    }


    public static void displayGameSaves(List<String> savesList) {
        StringBuffer option = new StringBuffer();
        option.append("Choose saves:").append("\n");
        int index = 0;
        for (int i = 0; i < savesList.size(); i++) {
            option.append(savesList.get(i)).append("\n");
        }
        option.append("0) Back").append("\n");
        System.out.println(option.toString());
    }
}
