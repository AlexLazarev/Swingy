package com;

import com.exception.TypeNotFoundException;
import com.model.artifacts.Armor;
import com.model.artifacts.Helm;
import com.model.artifacts.Item;
import com.model.artifacts.Weapon;
import com.model.characters.Enemy;
import com.model.characters.Hero;
import com.util.*;
import com.view.GUI;
import com.view.Shell;
import com.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by alazarev on 8/1/18.
 */
public class GameManager {
    static GameManager  game = new GameManager();
    private GUI                 gui;
    private Shell               shell;
    private Hero                hero;
    private char[][]            map;
    private int                 size;
    private boolean             isGUI;
    private Random              random;
    private Enemy               enemy;
    private int                 dx;
    private int                 dy;
    private Item                item;
    private Scene               scene;
    private ArrayList<Enemy>    enemies;

    private GameManager(){
        gui     = new GUI();
        shell   = new Shell();
        isGUI   = false;
        scene   = Scene.Menu;
        enemies = new ArrayList<Enemy>();
        random  = new Random();
    }

    static public GameManager getGame(){
        return game;
    }

    public void generateMap() {
        size = (hero.getLvl() - 1) * 5 + 10 - (hero.getLvl() % 2);
        map = new char[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                switch (random.nextInt(4)) {
                    case 0:
                        map[i][j] = '.';
                        break;
                    case 1:
                        if (random.nextBoolean())
                            map[i][j] = 'R';
                        else
                            map[i][j] = '.';
                        break;
                    case 2:
                    case 3:
                        try {
                            enemies.add(CharacterFactory.newEnemy("Spider", new Position(j, i)));
                            map[i][j] = 'E';
                        } catch (TypeNotFoundException e) {
                            System.out.println(e.toString());
                        }
                        break;
                    default:
                        break;
                }
                setHeroOnMap();
    }

    public State moveCheck(int dx, int dy){
        this.dx = dx;
        this.dy = dy;
        if (isBoundOfMap(dx, dy) && !isRock(dx, dy))
        {
            enemy = getEnemy();
            if (enemy != null){
                return State.ProposalFight;
            }
            return State.Success;
        }
        return State.Continue;
    }

    public void moveHero() {
        map[hero.getPos().getY()][hero.getPos().getX()] = '.';
        hero.move(dx, dy);
        map[hero.getPos().getY()][hero.getPos().getX()] = 'H';
        if (hero.getPos().getY() == 0 || hero.getPos().getY() == size - 1 ||
                hero.getPos().getX() == 0 || hero.getPos().getX() == size - 1)
            scene = Scene.GameOver;

    }

    public State fight() {
        enemy.setHp(enemy.getHp() - hero.getFullDmg() / enemy.getArm());
        hero.setHp(hero.getHp() - enemy.getDmg() / hero.getFullArm());
        if (enemy.getHp() == 0 && hero.getHp() != 0) {
            hero.setExp(hero.getExp() + enemy.getLvl() * 100);
            map[enemy.getPos().getX()][enemy.getPos().getY()] = '.';
            randomItem();
            return State.ProposalItem;
        }
        else if (hero.getHp() == 0) {
            scene = Scene.GameOver;
            return State.Back;
        }
        return State.Continue;
    }

    public void randomItem(){
        try {
            switch (random.nextInt(3)) {
                case 0:
                    item = ItemFactory.newRandomItem("Weapon");
                    break;
                case 1:
                    item = ItemFactory.newRandomItem("Armor");
                    break;
                case 2:
                    item = ItemFactory.newRandomItem("Helm");
                    break;
            }
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void setItem(){
        switch (item.getType()){
            case "Weapon":
                hero.setWeapon((Weapon)item);
                break;
            case "Armor":
                hero.setArmor((Armor) item);
                break;
            case "Helm":
                hero.setHelm((Helm)item);
                break;
        }
    }

    public void setHero(Hero hero){
        this.hero = hero;
    }

    public void init(){
        try {
            generateMap();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public Hero getHero() {
        return hero;
    }

    public char[][] getMap() {
        return map;
    }

    private boolean isBoundOfMap(int dx, int dy){
        if (hero.getPos().getX() + dx >= 0
                && hero.getPos().getX() + dx < size
                && hero.getPos().getY() + dy >= 0
                && hero.getPos().getY() + dy < size)
            return true;
        return false;
    }

    private boolean isRock(int dx, int dy) {
        if (map[hero.getPos().getY() + dy][hero.getPos().getX() + dx] == 'R')
            return true;
        return false;
    }

    private Enemy getEnemy() {
        int x = hero.getPos().getX() + dx;
        int y = hero.getPos().getY() + dy;
        if (map[y][x] == 'E'){
            for (Enemy e : enemies){
                if (e.getPos().equal(x, y))
                    return e;
            }
        }
        return null;
    }

    private void setHeroOnMap(){
        map[size / 2][size / 2] = 'H';
        hero.setPos(new Position(size / 2, size / 2));
    }

    public String getStrMap(){
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++) {
                stringBuilder.append(GameManager.getGame().getMap()[i][j]);
                stringBuilder.append(" ");
            }
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    public String getItemInfo(){
        return item.getInfo();
    }


    public String getStrInfo(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Type   :  ");
        stringBuilder.append(hero.getType());
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("Name   :  ");
        stringBuilder.append(hero.getName());
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("LVL    :  ");
        stringBuilder.append(hero.getLvl());
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("ExP    :  ");
        stringBuilder.append(hero.getExp());
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("DMG    :  ");
        stringBuilder.append(hero.getDmg());
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("ARMOR  :  ");
        stringBuilder.append(hero.getArm());
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("HP     :  ");
        stringBuilder.append(hero.getHp());
        stringBuilder.append(System.lineSeparator());
        if (hero.getWeapon() != null) {
            stringBuilder.append("Weapon :  ");
            stringBuilder.append(hero.getWeapon().getDmg());
            stringBuilder.append(System.lineSeparator());
        }
        if (hero.getArmor() != null) {
            stringBuilder.append("Armor  :  ");
            stringBuilder.append(hero.getArmor().getArm());
            stringBuilder.append(System.lineSeparator());
        }
        if (hero.getHelm() != null) {
            stringBuilder.append("Helm   :  ");
            stringBuilder.append(hero.getHelm().getHp());
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    public WindowManager    getUI(){
        if (isGUI) {
            gui.setVisible(true);
            return gui;
        }
        gui.setVisible(false);
        return shell;
    }


    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setGUI(boolean GUI) {
        isGUI = GUI;
    }
}
