package com.dao;

import com.exception.NameNotFoundException;
import com.exception.TypeNotFoundException;
import com.model.artifacts.Armor;
import com.model.artifacts.Helm;
import com.model.artifacts.Weapon;
import com.model.characters.Hero;
import com.util.CharacterFactory;
import com.util.ItemFactory;
import com.util.Position;

import java.sql.*;
import java.util.ArrayList;


/**
 * Created by alazarev on 8/2/18.
 */

public class HeroDB {
    private static Connection connection;
    private static boolean hasData = false;
    String querySelectDB    = "SELECT name FROM sqlite_master WHERE type='table' AND name='user'";
    String queryCreateTable = "CREATE TABLE hero(" +
            "id integer, " +
            "name varchar(60), " +
            "type varchar(10), " +
            "weapon varchar(15), " +
            "armor varchar(15), " +
            "helm varchar(15), " +
            "exp varchar(15), " +
            "lvl integer, " +
            "dmg integer, " +
            "arm integer, " +
            "hp integer, " +
            "x integer, " +
            "y integer, " +
            "primary key(id))";
    String querySelectHeroes= "SELECT id, name, type, weapon, armor, helm, exp, lvl, dmg, arm, hp, x, y from hero";
    String querySelectHero  = "SELECT id, name, type, weapon, armor, helm, exp, lvl, dmg, arm, hp, x, y from hero where id=";

    String queryInsert      = "INSERT INTO hero values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    String queryUpdate      = "UPDATE hero SET weapon=?, armor=?, helm=?, exp=?, lvl=?, dmg=?, arm=?, hp=?, x=?, y=? where id=?";
    String queryUpdateName  = "UPDATE hero SET name=? where id=?";

    String queryDelete      = "DELETE FROM hero WHERE id=";
    public void updateHero(Hero hero) {
        try {
            if (!isConnected())
                getConnection();
                PreparedStatement prep = connection.prepareStatement(queryUpdate);
                if (hero.getWeapon() != null)
                    prep.setString(1, hero.getWeapon().getName());
                if (hero.getArmor() != null)
                    prep.setString(2, hero.getArmor().getName());
                if (hero.getHelm() != null)
                    prep.setString(3, hero.getHelm().getName());
                prep.setInt(4, hero.getExp());
                prep.setInt(5, hero.getLvl());
                prep.setInt(6, hero.getDmg());
                prep.setInt(7, hero.getArm());
                prep.setInt(8, hero.getHp());
                prep.setInt(9, hero.getPos().getX());
                prep.setInt(10, hero.getPos().getY());
                prep.setInt(11, hero.getId());
                prep.executeUpdate();
            }
        catch (Exception e){
            System.out.println(e.toString() + " updateHero");
        }
        closeConnection();
    }

    public void updateHeroName(Hero hero) {
        try {
            if (!isConnected())
                getConnection();
            PreparedStatement prep = connection.prepareStatement(queryUpdateName);
            prep.setString(1, hero.getName());
            prep.setInt(2, hero.getId());
            prep.executeUpdate();
        }
        catch (Exception e){
            System.out.println(e.toString() + " updateHeroName");
        }
        closeConnection();
    }

    public void deleteHero(int id){
        try {
            if (!isConnected()){
                getConnection();
            }
            Statement statement = connection.createStatement();
            statement.executeUpdate(queryDelete + id);
        }
        catch (Exception e){
            System.out.println(e.toString() + "delete HERO");
        }
        closeConnection();
    }

    public ResultSet getHeroes() throws ClassNotFoundException, SQLException {
        try {
            if (!isConnected()) {
                getConnection();
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySelectHeroes);
            return resultSet;
        }
        catch (Exception e){
            System.out.println("List of hero is empty!");
        }
        return null;
    }

    public Hero getHero(int id) throws ClassNotFoundException, SQLException, TypeNotFoundException, NameNotFoundException {
        if (!isConnected()){
            getConnection();
        }
        Hero hero = null;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(querySelectHero + id);
        if (!rs.isClosed()) {
            hero = CharacterFactory.newHero(
                    rs.getInt("id"),
                    rs.getString("type"),
                    rs.getString("name"),
                    rs.getInt("lvl"),
                    rs.getInt("exp"),
                    rs.getInt("dmg"),
                    rs.getInt("arm"),
                    rs.getInt("hp"),
                    (Weapon) ItemFactory.newItem("Weapon", rs.getString("weapon")),
                    (Armor) ItemFactory.newItem("Armor", rs.getString("armor")),
                    (Helm) ItemFactory.newItem("Helm", rs.getString("helm")),
                    new Position(rs.getInt("x"), rs.getInt("y")));
        }
        closeConnection();
        return hero;
    }

    private void getConnection() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        }
        catch (Exception e){
            throw new ClassNotFoundException(e.toString() + " getConnection");
        }
        connection = DriverManager.getConnection("jdbc:sqlite:sqlhero.db");
        initialise();
    }

    private void initialise() throws SQLException {
        if (!hasData){
            hasData = true;
            Statement statement = connection.createStatement();
            try {
                statement.executeQuery(queryCreateTable);
            }
            catch (Exception e){
                //System.out.println("BD already exist");
            }
        }
    }

    public void addHero(Hero hero) {
        try {
            if (!isConnected())
                getConnection();
            PreparedStatement prep = connection.prepareStatement(queryInsert, Statement.RETURN_GENERATED_KEYS);
            if (hero.getName() != null)
                prep.setString(2, hero.getName());
            prep.setString(3, hero.getType());
            if (hero.getWeapon() != null)
                prep.setString(4, hero.getWeapon().getName());
            if (hero.getArmor() != null)
                prep.setString(5, hero.getArmor().getName());
            if (hero.getHelm() != null)
                prep.setString(6, hero.getHelm().getName());
            prep.setInt(7, hero.getExp());
            prep.setInt(8, hero.getLvl());
            prep.setInt(9, hero.getDmg());
            prep.setInt(10, hero.getArm());
            prep.setInt(11, hero.getHp());
            if (hero.getPos() != null) {
                prep.setInt(12, hero.getPos().getX());
                prep.setInt(13, hero.getPos().getY());
            }
            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            if (rs.next()){
                hero.setId(rs.getInt(1));
            }
        }
        catch (Exception e){
            System.out.println(e.toString() + " addHero");
            try {
                Thread.sleep(2000);
            }
            catch (Exception er) {
                System.out.println(er.toString());
            }
        }
        closeConnection();
    }

    public String getListOfHeroes() throws SQLException, ClassNotFoundException {
        ResultSet   rs  = getHeroes();
        StringBuilder stringBuilder = new StringBuilder();
        if (rs != null) {
            while (rs.next()) {
                stringBuilder.append("[" +
                        rs.getInt("id") + "] " +
                        rs.getString("type") + " " +
                        rs.getString("name") + " " +
                        rs.getString("lvl"));
                stringBuilder.append(System.getProperty("line.separator"));
            }

            closeConnection();
        }
        return stringBuilder.toString();
    }

    public String[][] getObjectsOfHeroes() throws SQLException, ClassNotFoundException {
        ResultSet   rs  = getHeroes();
        ArrayList<String[]> list = new ArrayList<>();
        if (rs != null) {
            while (rs.next()) {
                String[] str = new String[4];
                str[0] = rs.getString("id");
                str[1] = rs.getString("type");
                str[2] = rs.getString("name");
                str[3] = rs.getString("lvl");
                list.add(str);
            }
            closeConnection();
        }
        String[][] str = new String[list.size()][];
            for (int i = 0; i < list.size(); i++) {
                str[i] = list.get(i);
            }

        return str;
    }

    private boolean isConnected() {
        try {
            return (connection != null && !connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void closeConnection() {
        try {
            connection.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
