package com;

import com.controller.Command;
import com.model.characters.Elf;
import com.model.characters.Hero;
import com.util.Scene;
import com.view.GUI;
import com.view.Shell;

import java.awt.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        if (args.length == 1) {
            if (args[0].equals("console") || args[0].equals("gui")) {
                if (args[0].equals("console"))
                    GameManager.getGame().setGUI(false);
                else if (args[0].equals("gui"))
                    GameManager.getGame().setGUI(true);
                Game();
            }
            else
                System.out.println("[console] | [gui]");
        }
        else
            System.out.println("[console] | [gui]");
    }

    public static void Game(){
        while (GameManager.getGame().getScene() != Scene.Exit) {
            switch (GameManager.getGame().getScene()) {
                case Menu:
                    GameManager.getGame().getUI().menu();
                    break;
                case CreateHero:
                    GameManager.getGame().getUI().createHero();
                    break;
                case SelectHero:
                    GameManager.getGame().getUI().selectHero();
                    break;
                case Game:
                    GameManager.getGame().getUI().game();
                    break;
                case GameOver:
                    GameManager.getGame().getUI().gameOver();
                    break;
            }
        }
    }
}
