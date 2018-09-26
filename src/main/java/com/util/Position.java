package com.util;

import javafx.geometry.Pos;

/**
 * Created by alazarev on 8/2/18.
 */
public class Position {
    private int x;
    private int y;

    public Position(){

    }
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public boolean equal(int x, int y){
        if (this.x == x && this.y == y)
            return true;
        return false;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setLocation(int x, int y){
        this.x = x;
        this.y = y;
    }

}
