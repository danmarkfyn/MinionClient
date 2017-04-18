package minioning.common.data;

import com.badlogic.gdx.graphics.g2d.Sprite;
import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author Mogensen
 */
public class Entity implements Serializable {

    private final UUID ID;
    private UUID owner;
    private String name;
    private int x = 0;
    private int y = 0;
    private float dx;
    private float dy;
    private float speed = 100;
    private Sprite sprite;
    private String location;
    private EntityType type;

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }
    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public Entity(UUID ID, String name, int x, int y, String location) {
//        this.owner = owner;
        this.name = name;
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.location = location;
    }

    public int getX() {
        return x;
    }

    
    
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public String toString() {

        String EntityString = ID + ";" + owner + ";" + name + ";" + x + ";" + ";" + y + ";" + dx + ";" + dy + ";" + speed + ";";

        return EntityString;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public UUID getID() {
        return ID;
    }

}
