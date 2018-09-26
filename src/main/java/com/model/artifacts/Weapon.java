package com.model.artifacts;

/**
 * Created by alazarev on 8/1/18.
 */
public class Weapon extends Item{
    int     dmg;

    public int getDmg() {
        return dmg;
    }

    public Weapon(String name, int dmg){
        super(name, "Weapon");
        this.dmg = dmg;
    }

    @Override
    public String getInfo() {
        return type + " '" + name + "' " +  "(dmg: " + dmg + ")";
    }
}
