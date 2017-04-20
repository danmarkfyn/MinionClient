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
    private Vector2D vPosition;
    private Vector2D vTarget;

    public EntityType getType() {
        return type;
    }

    public Vector2D getvTarget() {
        return vTarget;
    }

    public float getvxp() {
        return this.vPosition.getX();
    }

    public float getvyp() {
        return this.vPosition.getY();
    }

    public float getvxg() {
        if(this.vTarget == null){
            return this.vPosition.getX();
        }
        return this.vTarget.getX();
    }

    public float getvyg() {
        if(this.vTarget == null){
            return this.vPosition.getY();
        }
        return this.vTarget.getY();
    }

    public void setvTarget(Vector2D vTarget) {
        this.vTarget = vTarget;
    }

    public Vector2D getvPosition() {
        return vPosition;
    }

    public void setvPosition(Vector2D vPosition) {
        this.vPosition = vPosition;
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
