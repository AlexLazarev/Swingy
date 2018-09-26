package com.model.characters;

import com.model.artifacts.Armor;
import com.model.artifacts.Helm;
import com.model.artifacts.Weapon;
import com.util.Position;

/**
 * Created by alazarev on 8/1/18.
 */
public class Mage extends Hero {
    public Mage(int id, String name, int lvl, int exp, int dmg, int arm, int hp, Weapon weapon, Armor armor, Helm helm, Position position) {
        super(id, "Mage", name, lvl, exp, dmg, arm, hp, weapon, armor, helm, position);
    }
    public Mage(String name){
        super("Mage", name, 1, 300, 2, 10, new Weapon("Magic Staff", 15));
    }
}
