package com.model.characters;

import com.util.Position;

import java.util.Random;

/**
 * Created by alazarev on 8/3/18.
 */
public class Spider extends Enemy {
    static Random random = new Random();
    public Spider(Position position) {
        super("Spider", 1, random.nextInt(5) + 1, random.nextInt(3) + 2, random.nextInt(20) + 5, position);
    }
}
