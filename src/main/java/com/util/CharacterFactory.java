package com.util;

import com.exception.TypeNotFoundException;
import com.model.artifacts.Armor;
import com.model.artifacts.Helm;
import com.model.artifacts.Weapon;
import com.model.characters.*;

/**
 * Created by alazarev on 8/1/18.
 */
public class CharacterFactory {

    static public Hero newHero(int id, String type, String name, int lvl, int exp, int dmg, int arm, int hp,
                               Weapon weapon, Armor armor, Helm helm, Position position) throws TypeNotFoundException{
        if (type.equals("Elf"))
            return new Elf(id, name, lvl, exp, dmg, arm, hp, weapon, armor, helm, position);
        if (type.equals("Dwarf"))
            return new Dwarf(id, name, lvl, exp, dmg, arm, hp, weapon, armor, helm, position);
        if (type.equals("Ork"))
            return new Ork(id, name, lvl, exp, dmg, arm, hp, weapon, armor, helm, position);
        if (type.equals("Mage"))
            return new Mage(id, name, lvl, exp, dmg, arm, hp, weapon, armor, helm, position);
        throw new TypeNotFoundException("[Error] - type of character [" + type + "] is invalid!");
    }

    static public Hero newHero(String type, String name) throws TypeNotFoundException {
        if (type.equals("Elf"))
            return new Elf(name);
        if (type.equals("Dwarf"))
            return new Dwarf(name);
        if (type.equals("Ork"))
            return new Ork(name);
        if (type.equals("Mage"))
            return new Mage(name);
        throw new TypeNotFoundException("[Error] - type of character [" + type + "] is invalid!");
    }

    static public Enemy newEnemy(String type, Position pos) throws TypeNotFoundException {
        if (type.equals("Spider"))
            return new Spider(pos);
        throw new TypeNotFoundException("[Error] - type of character [" + type + "] is invalid!");
    }
}
