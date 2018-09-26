package com.model.artifacts;

/**
 * Created by alazarev on 8/1/18.
 */
public abstract class Item {
    String  name;


    String  type;

    public String getName() {
        return name;
    }

    public Item(String name, String type){
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    abstract public String getInfo();
}
