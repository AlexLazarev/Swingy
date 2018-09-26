package com.model.characters;

import com.model.artifacts.Armor;
import com.model.artifacts.Helm;
import com.model.artifacts.Weapon;
import com.util.Position;

/**
 * Created by alazarev on 8/1/18.
 */
public class Elf extends Hero {

    public Elf(int id, String name, int lvl, int exp, int dmg, int arm, int hp, Weapon weapon, Armor armor, Helm helm, Position position) {
        super(id, "Elf", name, lvl, exp, dmg, arm, hp, weapon, armor, helm, position);
    }
    public Elf(String name){
        super("Elf", name, 1, 30, 2, 10, new Weapon("Big BOW", 10));
    }
}
