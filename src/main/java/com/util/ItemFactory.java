package com.util;

import com.exception.NameNotFoundException;
import com.exception.TypeNotFoundException;
import com.model.artifacts.Armor;
import com.model.artifacts.Helm;
import com.model.artifacts.Item;
import com.model.artifacts.Weapon;

import java.util.Random;

/**
 * Created by alazarev on 8/1/18.
 */
public class ItemFactory {
    static private String[] nameWeapon = {"AXE", "Excalibur", "Magic Staff", "Big BOW"};
    static private String[] nameHelm = {"h3", "Helmer", "Dominator", "Helm of Death"};
    static private String[] nameArmor = {"Blade Mail", "Buckler", "Shiva's Guard", "Assault Cuirass"};
    static public Item newItem(String type, String name) throws TypeNotFoundException, NameNotFoundException {
        if (name == null || type == null)
            return null;
        if (type.equals("Weapon")) {
            if (name.equals("AXE"))
                return new Weapon(name, 10);
            if (name.equals("Excalibur"))
                return new Weapon(name, 30);
            if (name.equals("Magic Staff"))
                return new Weapon(name, 15);
            if (name.equals("Big BOW"))
                return new Weapon(name, 12);
            throw new NameNotFoundException("[Error] - name of item [" + name + "] is invalid!");
        }
        if (type.equals("Helm")) {
            if (name.equals("h3"))
                return new Helm(name, 5);
            if (name.equals("Helmer"))
                return new Helm(name, 20);
            if (name.equals("Dominator"))
                return new Helm(name, 25);
            if (name.equals("Helm of Death"))
                return new Helm(name, 30);
            throw new NameNotFoundException("[Error] - name of item [" + name + "] is invalid!");
        }
        if (type.equals("Armor")) {
            if (name.equals("Blade Mail"))
                return new Armor(name, 5);
            if (name.equals("Buckler"))
                return new Armor(name, 10);
            if (name.equals("Shiva's Guard"))
                return new Armor(name, 20);
            if (name.equals("Assault Cuirass"))
                return new Armor(name, 30);
            throw new NameNotFoundException("[Error] - name of item [" + name + "] is invalid!");
        }
        throw new TypeNotFoundException("[Error] - type of item [" + type + "] is invalid!");
    }

    static public Item newRandomItem(String type) throws TypeNotFoundException, NameNotFoundException {
        Random random = new Random();
        if (type.equals("Weapon"))
            return newItem(type, nameWeapon[random.nextInt(4)]);
        else if (type.equals("Helm"))
            return newItem(type, nameHelm[random.nextInt(4)]);
        else if (type.equals("Armor"))
            return newItem(type, nameArmor[random.nextInt(4)]);
        throw new TypeNotFoundException("[Error] - type of item [" + type + "] is invalid! random");
    }
}