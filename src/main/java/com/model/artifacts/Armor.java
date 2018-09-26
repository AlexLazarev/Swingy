package com.model.artifacts;

/**
 * Created by alazarev on 8/1/18.
 */
public class Armor extends Item{
    int     arm;

    public int getArm() {
        return arm;
    }
    public Armor(String name, int arm) {
        super(name, "Armor");
        this.arm = arm;
    }

    @Override
    public String getInfo() {
        return type + " '" + name + "' " +  "(arm: " + arm + ")";
    }
}
