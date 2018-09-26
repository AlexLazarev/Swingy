package com.model.characters;

import com.util.Position;

/**
 * Created by alazarev on 8/3/18.
 */
public class Enemy extends Character {

    public Enemy(String name, int lvl, int dmg, int arm, int hp, Position pos) {
        super(name, lvl, dmg, arm, hp);
        this.pos = pos;
    }

}
