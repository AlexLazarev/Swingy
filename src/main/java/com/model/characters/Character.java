package com.model.characters;

import com.util.Position;

import javax.validation.constraints.NotNull;

/**
 * Created by alazarev on 8/1/18.
 */
public abstract class Character {

    protected int     lvl;
    protected int     dmg;
    protected int     arm;
    protected int     hp;
    @NotNull
    protected String  type;

    protected Position pos;

    public Character(String type, int lvl, int dmg, int arm, int hp) {
        this.type = type;
        this.lvl = lvl;
        this.dmg = dmg;
        this.arm = arm;
        this.hp = hp;
    }

    public String getType() {
        return type;
    }

    public int getLvl() {
        return lvl;
    }

    public int getDmg() {
        return dmg;
    }

    public int getArm() {
        return arm;
    }

    public int getHp() {
        return hp;
    }

    public Position getPos() {
        return pos;
    }

    public void setHp(int hp) {
        if (hp < 0)
            this.hp = 0;
        else
            this.hp = hp;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }
}
