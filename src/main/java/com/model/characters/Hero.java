package com.model.characters;

import com.GameManager;
import com.model.artifacts.Armor;
import com.model.artifacts.Helm;
import com.model.artifacts.Weapon;
import com.util.Position;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by alazarev on 8/1/18.
 */

public abstract class Hero extends Character {
    @NotNull
    private Weapon  weapon;

    private Armor   armor;

    private Helm    helm;

    public void setExp(int exp) {
        int lvlBound = lvl * 1000 + (int) Math.pow(lvl - 1, 2) * 450;
        if (exp >= lvlBound) {
            exp -= lvlBound;
            if (lvl < 10)
                lvl++;
            GameManager.getGame().generateMap();
        }
        this.exp = exp;
    }

    private int     exp;

    @NotNull
    @Size(min = 2, max = 60)
    private String  name;

    private int     id;

    public void move(int x, int y){
        pos.setLocation(pos.getX() + x, pos.getY() + y);
    }

    public Hero(String type, String name, int lvl, int dmg, int arm, int hp, Weapon weapon) {
        super(type, lvl, dmg, arm, hp);
        this.weapon = weapon;
        this.name = name;
    }

    public Hero(int id, String type, String name, int lvl, int exp, int dmg, int arm, int hp, Weapon weapon, Armor armor, Helm helm, Position position) {
        super(type, lvl, dmg, arm, hp);
        this.weapon = weapon;
        this.armor = armor;
        this.helm = helm;
        this.pos = new Position();
        this.name = name;
        this.pos = position;
        this.exp = exp;
        this.id = id;
    }

    public boolean attack(Enemy e) {
        e.setHp(e.getHp() - getFullDmg() / e.getArm());
        setHp(hp - e.getDmg() / getFullArm());
        if (e.getHp() == 0 && hp != 0) {
            int lvlBound = lvl * 1000 + (int)Math.pow(lvl - 1, 2) * 450;
            exp += e.lvl * 10;
            if (exp >= lvlBound) {
                exp -= lvlBound;
                lvl++;
            }
            return true;
        }
        return false;
    }

    public int getFullDmg(){
        if (weapon != null)
            return (dmg + weapon.getDmg()) * lvl / 2;
        return dmg;
    }

    public int getFullArm() {
        if (armor != null)
            return (arm + armor.getArm()) * lvl / 2;
        return arm;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public Helm getHelm() {
        return helm;
    }

    public int getExp() {
        return exp;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public void setHelm(Helm helm) {
        if (helm != null)
            hp += helm.getHp();
        this.helm = helm;
    }
}
