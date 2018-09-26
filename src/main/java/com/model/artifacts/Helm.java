package com.model.artifacts;

/**
 * Created by alazarev on 8/1/18.
 */
public class Helm extends Item {
    int hp;

    public int getHp() {
        return hp;
    }

    public Helm(String name, int hp) {
        super(name, "Helm");
        this.hp = hp;
    }

    @Override
    public String getInfo() {
        return type + " '" + name + "' " +  "(hp: " + hp + ")";
    }
}
