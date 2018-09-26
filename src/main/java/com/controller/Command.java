package com.controller;

import com.GameManager;
import com.dao.HeroDB;
import com.model.characters.Hero;
import com.util.CharacterFactory;
import com.util.Scene;
import com.util.State;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.exit;

/**
 * Created by alazarev on 8/5/18.
 */
public class Command {

    private Scanner in;
    private int     input;
    private String  next;
    private State   state;
    private HeroDB  heroDB;

    public Command(){
        in = new Scanner(System.in);
        heroDB = new HeroDB();
    }

    public void take(){
        next = in.next();
        if (next.equals("exit")){
            System.exit(0);
        }
        else if (next.equals("back")){
            state = State.Back;
            GameManager.getGame().setHero(null);
            GameManager.getGame().setScene(Scene.Menu);
        }
        else if (next.equals("switch")){
            state = State.Switch;
            GameManager.getGame().setGUI(true);
            System.out.print("\033[H\033[2J");
        }
        else {
            try {
                input = Integer.parseInt(next);
                state = State.Success;
            } catch (Exception e) {
                System.out.println("[Error] Bad input");
                try {
                    Thread.sleep(1000);
                }
                catch (Exception er) {
                    System.out.println(er.toString());
                }
                state = State.Continue;
            }
        }
    }

    public void take(boolean isString){
        if (isString) {
            next = in.next();
            if (next.equals("exit")) {
                exit(0);
            } else if (next.equals("back")) {
                state = State.Back;
            } else
                state = State.Success;
        }
        else
            take();
    }

    public void selectHero(){
        Hero    hero;
        state = State.Continue;
        try {
            if (input < 0) {
                heroDB.deleteHero(-input);
            } else {
                hero = heroDB.getHero(input);
                if (hero != null) {
                    GameManager.getGame().setHero(hero);
                    GameManager.getGame().init();
                    GameManager.getGame().setScene(Scene.Game);
                    state = State.Success;
                }
            }
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void menu(){
        state = State.Success;
        switch (input) {
            case 1:
                GameManager.getGame().setScene(Scene.CreateHero);
                break;
            case 2:
                GameManager.getGame().setScene(Scene.SelectHero);
                break;
            default:
                state = State.Continue;
        }
    }


    public void createHero(){
        String type = getType();
        if (type != null){
            try {
                GameManager.getGame().setHero(CharacterFactory.newHero(type, "default"));
                heroDB.addHero(GameManager.getGame().getHero());
                state = State.Success;
            }
            catch (Exception e){
                System.out.println(e.toString() + "createHero");
                try {
                    Thread.sleep(5000);
                }
                catch (Exception er) {}
                state = State.Continue;
            }
        }
        else
            state = State.Continue;
    }

    public void setHeroName(){
        String name = next;
        if (name.length() > 1 && name.length() < 60){
            GameManager.getGame().getHero().setName(name);
            heroDB.updateHeroName(GameManager.getGame().getHero());
            GameManager.getGame().init();
            GameManager.getGame().setScene(Scene.Game);
        }
    }

    public void game(){
        switch (input) {
            case 1:
                state = GameManager.getGame().moveCheck(0, -1);
                break;
            case 2:
                state = GameManager.getGame().moveCheck(1, 0);
                break;
            case 3:
                state = GameManager.getGame().moveCheck(-1, 0);
                break;
            case 4:
                state = GameManager.getGame().moveCheck(0, 1);
                break;
            case 5:
                heroDB.updateHero(GameManager.getGame().getHero());
                state = State.Continue;
                break;
            default:
                state = State.Continue;
                break;
        }
    }

    public void proposalFight(){
        if (input == 1){
            state = GameManager.getGame().fight();
        } else if (input == 2) {
            Random random = new Random();
            if (random.nextBoolean())
                state = State.Run;
            else
                state = GameManager.getGame().fight();
        } else
            state = State.Continue;
    }

    public void proposalItem(){
        if (input == 1){
            GameManager.getGame().setItem();
            state = State.Success;
        } else if (input == 2) {
            state = State.Success;
        } else
            state = State.Continue;
    }

    public void move(){
        GameManager.getGame().moveHero();
    }

    public String getType(){
        if (input == 1)
            return "Elf";
        if (input == 2)
            return "Ork";
        if (input == 3)
            return "Dwarf";
        if (input == 4)
            return "Mage";
        return null;
    }


    public State getState() {
        return state;
    }

    public String getListOfHeroes(){
        try {
            return heroDB.getListOfHeroes();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String[][] getObjectsOfHeroes(){
        try {
            return heroDB.getObjectsOfHeroes();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String gameOver(){
        if (GameManager.getGame().getHero().getHp() != 0)
            return "YOU WIN";
        return "YOU LOSE";
    }

}
