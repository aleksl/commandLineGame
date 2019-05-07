package pl.aleksl.commandLineGame.service;

import pl.aleksl.commandLineGame.dao.GameDao;
import pl.aleksl.commandLineGame.menu.CommandLineMessage;
import pl.aleksl.commandLineGame.model.Monster;
import pl.aleksl.commandLineGame.model.Place;
import pl.aleksl.commandLineGame.model.Player;
import pl.aleksl.commandLineGame.model.Weapon;

import java.util.*;
import java.util.stream.Collectors;

public class GameImpl implements Game {

    Scanner scanner;
    GameDao gameDao;

    Map<Integer, Place> allPlaces = new HashMap<>();

    public GameImpl(GameDao gameDao) {
        this.scanner = new Scanner(System.in);
        this.gameDao = gameDao;
    }

    public void initGame() {
        allPlaces = gameDao.loadGameMap();
        gameDao.prepareSave();
    }

    private Integer getInputInt() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            wrongOption();
        }
        return null;
    }

    private String getInputString() {
        return scanner.next();
    }

    private void wrongOption() {
        CommandLineMessage.wrongOptionMessage();
        scanner.nextLine();
    }

    @Override
    public boolean fightWithMonster(Player player, Monster monster) {
        boolean winFight = true;
        int hp = monster.getHealthPoints();
        do {
            CommandLineMessage.displayPlayerAndMonsterMessage(player, monster);
            boolean canAttack = attackOrMiss();
            if (canAttack) {
                hp = attackMonster(hp, player.getWeapon().strengthAttack);
                if (hp <= 0) {
                    player.setExperience(player.getExperience() + monster.getExperience());
                }
                monster.setHealthPoints(hp);
            } else {
                player.setHealthPoints(player.getHealthPoints() - monster.getStrengthAttack());
                if (player.getHealthPoints() <= 0) {
                    winFight = false;
                    break;
                }
            }
        } while (hp > 0);

        return winFight;
    }

    @Override
    public int attackMonster(int hp, int strengthAttack) {
        int monsterHp = hp - strengthAttack;
        return monsterHp < 0 ? 0 : monsterHp;
    }

    @Override
    public boolean exploreToNextPlace(Player player, Place destinationPlace) {
        boolean[] isAlive = new boolean[1];
        isAlive[0] = true;
        destinationPlace.getMonsterToKill().forEach(monster -> {
            if (player.getHealthPoints() <= 0) {
                isAlive[0] = false;
                return;
            }
            isAlive[0] = fightWithMonster(player, monster);
        });
        return isAlive[0];
    }


    @Override
    public void startGame() {
        initGame();
        CommandLineMessage.helloInGameMessage();
        mainMenuController();
    }

    @Override
    public Player createNewCharacter() {
        String username = null;
        Integer weaponId = null;
        while (true) {
            if (username == null || username.isEmpty()) {
                CommandLineMessage.setUserNameMessage();
                username = getInputString();
                if (username.isEmpty())
                    continue;
            }
            if (weaponId == null) {
                CommandLineMessage.chooseWeaponForNewPlayerMessage();
                weaponId = getInputInt();
                if (weaponId == null) {
                    weaponId = null;
                    continue;
                }
                if (weaponId < 1 || weaponId > 2) {
                    weaponId = null;
                    wrongOption();
                    continue;
                }

            }
            if (username != null && !username.isEmpty() && weaponId != null) {
                break;
            }
        }
        Player player = new Player();
        player.setName(username);
        player.setWeapon(Weapon.valueOfWeaponNumber(weaponId.intValue()));
        player.setHealthPoints(100);
        return player;
    }

    @Override
    public boolean attackOrMiss() {
        int numberA = getRandomNumber();
        int numberB = getRandomNumber();
        Integer result = displayMathTaskAndGetInput(numberA, numberB);
        if (result == null) {
            return false;
        }
        return multiplicationTask(numberA, numberB, result.intValue());

    }

    public int getRandomNumber(){
        return new Random().nextInt(10) + 1;
    }

    public boolean multiplicationTask(int numberA, int numberB, int result) {
        return numberA * numberB == result;
    }

    private Integer displayMathTaskAndGetInput(int numberA, int numberB) {
        CommandLineMessage.displayMathTask(numberA, numberB);
        Integer result = getInputInt();
        return result;
    }

    private void mainMenuController() {
        boolean runGame = true;
        do {
            CommandLineMessage.mainMenuMessage();
            Integer typedOption = getInputInt();
            if (typedOption == null) {
                continue;
            }
            switch (typedOption) {
                case 1: { //Start new game
                    Place startPlace = allPlaces.get(1);
                    Player player = createNewCharacter();
                    gameOptionMenuController(player, startPlace);
                    break;
                }
                case 2: { //Load game
                    List<String> saves = gameDao.saves();
                    CommandLineMessage.displayGameSaves(saves);
                    Integer typedSaves = getInputInt();
                    switch (typedSaves) {
                        case 0: //Exit to option
                            break;
                        default:
                            if ((typedSaves) > saves.size()) {
                                wrongOption();
                                continue;
                            }
                            Map<String, Object> loadedGame = gameDao.loadGame(typedSaves);
                            Place lastSavedPlace = allPlaces.get(loadedGame.get("lastPlace"));
                            gameOptionMenuController((Player) loadedGame.get("player"), lastSavedPlace);
                    }
                    break;
                }
                case 0: { //Exit game
                    CommandLineMessage.endGameMessage();
                    runGame = false;
                    break;
                }
                default:
                    wrongOption();
            }

        } while (runGame);
    }

    private void gameOptionMenuController(Player player, Place startPlace) {
        while (true) {
            CommandLineMessage.gameOptionMenuMessage();
            Integer typedOption = getInputInt();
            if (typedOption == null) {
                continue;
            }
            switch (typedOption) {
                case 1: { //Explore
                    boolean gameOver = false;
                    exploreLoop:
                    do {
                        Place currentPlace = gameController(player, startPlace);
                        if (currentPlace == null) {
                            gameOver = true;
                            break exploreLoop;
                        }
                        startPlace = currentPlace;
                        gameOver = exploreToNextPlace(player, currentPlace);
                    } while (gameOver);
                    if (!gameOver) {
                        CommandLineMessage.gameOver();
                        return;
                    }
                    break;
                }
                case 2: { //Save game
                    //TODO update saveGame or create new
                    CommandLineMessage.displayGameSaves(gameDao.saves());
                    Integer typedSaves = getInputInt();
                    switch (typedSaves) {
                        case 0: //Exit to option
                            break;
                        default:
                            if ((typedSaves) > gameDao.saves().size()) {
                                wrongOption();
                                continue;
                            }
                    }
                    gameDao.saveGame(player, startPlace.getPlaceId(), String.valueOf(typedSaves.intValue()));
                    break;
                }
                case 0: //Exit to main menu
                    return;
                default:
                    wrongOption();
            }
        }
    }

    private Place gameController(Player player, Place currentPlace) {
        List<Place> linkedPlaces = allPlaces.entrySet().stream()
                .filter(place -> currentPlace.getLinkedPlaces().contains(place.getKey()))
                .map(place -> place.getValue())
                .collect(Collectors.toList());

        while (true) {
            CommandLineMessage.displayPlayerAndGameMenuMessage(player, currentPlace, linkedPlaces);
            Integer typedOption = getInputInt();
            if (typedOption == null) {
                continue;
            }
            switch (typedOption) {
                case 0: //Exit to option
                    return null;
                default:
                    if ((typedOption - 1) >= currentPlace.getLinkedPlaces().size()) {
                        wrongOption();
                        continue;
                    }
                    return allPlaces.get(currentPlace.getLinkedPlaces().get((typedOption - 1)));
            }

        }
    }
}

