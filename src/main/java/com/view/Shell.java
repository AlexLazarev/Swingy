package com.view;

import com.GameManager;
import com.controller.Command;
import com.util.Scene;
import com.util.State;
import java.util.Scanner;

/**
 * Created by alazarev on 8/1/18.
 */

public class Shell implements WindowManager {
    private Scanner in;
    private Command command;

    public Shell(){
        in = new Scanner(System.in);
        command = new Command();
    }

    public void game(){
        do {
            System.out.print("\033[H\033[2J");
            System.out.println(GameManager.getGame().getStrInfo());
            System.out.println(GameManager.getGame().getStrMap());
            System.out.println("MOVE");
            System.out.println("[1] North");
            System.out.println("[2] East");
            System.out.println("[3] West");
            System.out.println("[4] South");
            System.out.println();
            System.out.println("[5] save");
            command.take();
            if (command.getState() == State.Success)
                command.game();
            if (command.getState() == State.ProposalFight) {
                System.out.println("Do you want to fight with this spider??(run, Forest, run)");
                System.out.println("[1] Fight");
                System.out.println("[2] Run (50% success)");
                command.take();
                if (command.getState() == State.Success)
                    command.proposalFight();
            }
            if (command.getState() == State.ProposalItem) {
                System.out.println("Do you want to take [" + GameManager.getGame().getItemInfo() + "]");
                System.out.println("[1] Keep");
                System.out.println("[2] Leave");
                command.take();
                if (command.getState() == State.Success)
                    command.proposalItem();
            }
            if (command.getState() == State.Success)
                    command.move();
        } while (command.getState() == State.Continue);
    }

    @Override
    public void gameOver() {
        do {
            System.out.print("\033[H\033[2J");
            System.out.println(command.gameOver());
            command.take();
        } while (command.getState() == State.Continue);
    }

    public void menu(){
        do {
            System.out.print("\033[H\033[2J");
            System.out.println("[1] Create new hero");
            System.out.println("[2] Select your hero");
            command.take();
            if (command.getState() == State.Success)
                command.menu();
        } while (command.getState() == State.Continue);
    }

    public void createHero() {
        if (GameManager.getGame().getHero() == null) {
            do {
                System.out.print("\033[H\033[2J");
                System.out.println("Choose class!");
                System.out.println("[1] Elf Assassin");
                System.out.println("[2] Ork Barbarian");
                System.out.println("[3] Dwarf Warrior");
                System.out.println("[4] MageDimon");
                command.take();
                if (command.getState() == State.Success)
                    command.createHero();
            } while (command.getState() == State.Continue);
        }
        if (command.getState() == State.Success)
        {
            do {
                System.out.print("\033[H\033[2J");
                System.out.println("Choose your name");
                command.take(true);
                command.setHeroName();
            } while (command.getState() == State.Continue);
        }
    }

    public void selectHero() {
        do {
            System.out.print("\033[H\033[2J");
            System.out.println("Choose hero");
            System.out.print(command.getListOfHeroes());
            command.take();
            if (command.getState() == State.Success)
                command.selectHero();
        } while (command.getState() == State.Continue);
    }

}
