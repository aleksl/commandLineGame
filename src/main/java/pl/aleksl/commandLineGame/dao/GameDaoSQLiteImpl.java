package pl.aleksl.commandLineGame.dao;

import pl.aleksl.commandLineGame.model.Place;
import pl.aleksl.commandLineGame.model.Player;

import java.util.List;
import java.util.Map;

public class GameDaoSQLiteImpl implements GameDao {

    @Override
    public void prepareSave() {

    }

    @Override
    public void saveGame(Player player, int placeId, String saveId) {

    }

    @Override
    public Map<String, Object> loadGame(int saveSlot) {
        return null;
    }

    @Override
    public List<String> saves() {
        return null;
    }

    @Override
    public Map<Integer, Place> loadGameMap() {
        return null;
    }
/*
    private static String JDBC_URL = "jdbc:sqlite:game.db";
    private static String JDBC_CLASS = "org.sqlite.JDBC";


    @Override
    public void prepareSave() {
        try (Connection c = DriverManager.getConnection(JDBC_URL);
             Statement stmt = c.createStatement()) {
            Class.forName(JDBC_CLASS);
            // stmt.executeUpdate("DROP TABLE SAVES");
            String sql = "CREATE TABLE IF NOT EXISTS SAVES " +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " PLAYER_NAME TEXT NOT NULL, " +
                    " HP INT NOT NULL, " +
                    " EXP INT NOT NULL, " +
                    " WEAPON_ID INT NOT NULL," +
                    " LAST_PLACE_ID  INT NOT NULL)";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Table created successfully");
    }


    @Override
    public void saveGame(Player player, int placeId, String saveId) {
        try (Connection c = DriverManager.getConnection(JDBC_URL);
             Statement stmt = c.createStatement()) {
            Class.forName(JDBC_CLASS);
            StringBuffer sql = new StringBuffer("INSERT INTO SAVES (PLAYER_NAME,HP,EXP,WEAPON_ID, LAST_PLACE_ID) ");
            sql.append("VALUES ('");
            sql.append(player.getName()).append("',");
            sql.append(player.getHealthPoints()).append(",");
            sql.append(player.getExperience()).append(",");
            sql.append(player.getWeapon().weaponNumber).append(",");
            sql.append(placeId);
            sql.append(");");
            System.out.println(sql.toString());
            stmt.executeUpdate(sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> loadGame(int saveSlot) {
        Map<String, Object> result = new HashMap<String, Object>(1);
        Player loadedPlayer = new Player();
        try (Connection c = DriverManager.getConnection(JDBC_URL);
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM SAVES WHERE ID = " + saveSlot + ";")) {
            Class.forName(JDBC_CLASS);
            while (rs.next()) {
                loadedPlayer.setName(rs.getString("PLAYER_NAME"));
                loadedPlayer.setHealthPoints(rs.getInt("HP"));
                loadedPlayer.setExperience(rs.getInt("EXP"));
                loadedPlayer.setWeapon(Weapon.valueOfWeaponNumber(rs.getInt("WEAPON_ID")));
                result.put("player", loadedPlayer);
                result.put("lastPlace", rs.getInt("LAST_PLACE_ID"));
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<String> saves() {
        List<String> savesList = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(JDBC_URL);
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT ID,  PLAYER_NAME FROM SAVES;")) {
            Class.forName(JDBC_CLASS);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String playerName = rs.getString("PLAYER_NAME");
                savesList.add(id + " " + playerName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savesList;
    }
    */
}


