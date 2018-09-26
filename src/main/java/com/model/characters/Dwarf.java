package com.model.characters;

import com.model.artifacts.Armor;
import com.model.artifacts.Helm;
import com.model.artifacts.Weapon;
import com.util.Position;

/**
 * Created by alazarev on 8/1/18.
 */
public class Dwarf extends Hero{

    public Dwarf(int id, String name, int lvl, int exp, int dmg, int arm, int hp, Weapon weapon, Armor armor, Helm helm, Position position) {
        super(id,"Dwarf", name, lvl, exp, dmg, arm, hp, weapon, armor, helm, position);
    }
    public Dwarf(String name){
        super("Dwarf", name, 1, 30, 2, 10, new Weapon("AXE", 10));
    }
}
