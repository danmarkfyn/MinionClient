/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.common.data;

import java.util.UUID;

/**
 *
 * @author Jakob
 */
public class Entity {

    private final UUID ID = UUID.randomUUID();
    private String name;
    private float x = 0;
    private float y = 0;

    public Entity(String name, float x, float y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public UUID getID() {
        return ID;
    }

}

