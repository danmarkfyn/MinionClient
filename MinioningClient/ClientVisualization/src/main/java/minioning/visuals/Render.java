/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.visuals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Align;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import minioning.common.data.Entity;
import static minioning.common.data.EntityType.ENEMY;
import static minioning.common.data.EntityType.PORTAL;
import minioning.common.data.LocalData;
import minioning.common.data.Vector2D;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import static minioning.common.data.EntityType.DOOR;
import static minioning.common.data.EntityType.HOLYBOLT;
import static minioning.common.data.EntityType.MINION;
import static minioning.common.data.EntityType.PLAYER;
import static minioning.visuals.State.INMENU;

/**
 *
 * @author Jakob
 */
public class Render {

    private static final String RESOURCE_ROOT = "../../../ClientVisualization/src/main/resources/";

    // Textures and Images
    // Background
    private Texture backgroundTexture_1;
    private Texture backgroundTexture_2;
    private Texture backgroundTexture_3;
    private Texture backgroundTexture_4;
    private Texture backgroundTexture_5;
    // Entities
    private Texture playerTexture;
    private Texture enemyTexture;
    private Texture portalTexture;
    private Texture missile1Texture;
    private Texture otherPlayerTexture;
    private Texture minionTexture;

    private Cursor customCursor;
    private Sprite backgroundSprite;

    // textures for entity and bg errors
    private Texture errorTexture;
    private Texture errorBG;

    // Shaperender
    private ShapeRenderer sr;

    //  Get local values
    private float width = LocalData.getWidth();
    private float height = LocalData.getWidth();

    // Allignment values for menu/HUD
    private int widthAlign = 100;
    private int sizeS = 32;
    private int sizeL = 48;

    // Buttons
    Image menuButton;

    private Texture buttonTexture;
    private TextureRegion buttonTextureRegion;
    private TextureRegionDrawable buttonTexRegionDrawable;
    private ImageButton button;

    // Menu Sprites
    private Sprite player;
    private Sprite enemy;
    private Sprite otherP;
    private Sprite minion;

    // Time values
    private float elapsed = 0;
    private float lastTime = elapsed;

    private BitmapFont font;
    private BitmapFont font1;
    private BitmapFont font2;
    private BitmapFont font3;
    private BitmapFont font4;

    private SpriteBatch batch;

    /**
     * Class constructer
     */
    public Render() {

        drawCustomCursor();
        sr = new ShapeRenderer();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2f);

        font1 = new BitmapFont();
        font1.setColor(Color.BLUE);
        font1.getData().setScale(1f);

        font2 = new BitmapFont();
        font2.setColor(Color.RED);
        font2.getData().setScale(1f);

        font3 = new BitmapFont();
        font3.setColor(Color.WHITE);
        font3.getData().setScale(1f);

        font4 = new BitmapFont();
        font4.setColor(Color.BLACK);
        font4.getData().setScale(1f);
        try {

            loadTextures();
            loadSprites();
        } catch (Exception e) {
            System.out.println("Error loading textures: " + e);
        }
    }

    /**
     * This method renders all assets in correct order to ensure no unwanted
     * overlapping
     *
     * @param world ConcurrentHashMap with entities (game world)
     * @param s Current render state
     */
    public void render(ConcurrentHashMap<UUID, Entity> world, State s) {

        batch = new SpriteBatch();

        batch.begin();
        drawSprites(world);
        drawHud();
        batch.end();

        batch.begin();
        drawHP();
        batch.end();

        if (s == INMENU) {
            sr.begin(ShapeType.Filled);
            sr.setColor(Color.DARK_GRAY);
            sr.rect(0, 0, 200, height);
            sr.end();
            batch.begin();
            ShowMenu(s);
            batch.end();
        } else {
            widthAlign = 100;
        }

        batch.dispose();

    }

    /**
     * This method draws the Head-Up Display (HUD)
     */
// Draws the HeadUp Display
    private void drawHud() {

        // Draw
        font.draw(batch, "In: " + LocalData.getLocation(), width - 155, LocalData.getHeight(), 150, Align.right, false);
        font.draw(batch, "Logged in as: " + LocalData.getUser(), widthAlign + 60, LocalData.getHeight(), 150, Align.right, false);

    }

    private void drawHP() {

        sr.begin(ShapeType.Filled);
        sr.setColor(Color.BLACK);
        sr.rect(LocalData.getWidth() - 290, 15, 205, 55);
        sr.end();

        sr.begin(ShapeType.Filled);
        sr.setColor(Color.RED);
        sr.rect(LocalData.getWidth() - 300, 25, 200, 50);
        sr.end();

        sr.begin(ShapeType.Filled);
        sr.setColor(Color.GREEN);
        sr.rect(LocalData.getWidth() - 300, 25, LocalData.getHp() * 2, 50);
        sr.end();

        font4.draw(batch, "HP: " + LocalData.getHp(), 100, 100, 100, Align.right, false);

    }

    private void drawCustomCursor() {
        // Sets custom cursor
        customCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal(RESOURCE_ROOT + "graphics/" + "cursor.png")), 16, 16);
        Gdx.graphics.setCursor(customCursor);
        customCursor.dispose();
    }

    /**
     * This method loads texture resources from their local path for use as
     * sprites
     */
    // Loads textures and Images
    private void loadTextures() {

        System.out.println("Loading bg textures");
        try {
            this.backgroundTexture_5 = new Texture(RESOURCE_ROOT + "map/" + "cave.png");
            this.backgroundTexture_4 = new Texture(RESOURCE_ROOT + "map/" + "wilderness_west.png");
            this.backgroundTexture_3 = new Texture(RESOURCE_ROOT + "map/" + "wilderness_east.png");
            this.backgroundTexture_2 = new Texture(RESOURCE_ROOT + "map/" + "arena.png");
            this.backgroundTexture_1 = new Texture(RESOURCE_ROOT + "map/" + "wilderness.png");
            this.errorBG = new Texture(RESOURCE_ROOT + "map/" + "worldNull.png");
            System.out.println("bg textures loaded succesfully");
        } catch (Exception e) {
            System.out.println("Failed to load bg textures: " + e);
        }
        System.out.println("Loading entity textures");

        try {
            playerTexture = new Texture(Gdx.files.local(RESOURCE_ROOT + "graphics/" + "blue.png"));
            enemyTexture = new Texture(Gdx.files.local(RESOURCE_ROOT + "graphics/" + "red.png"));
            portalTexture = new Texture(Gdx.files.local(RESOURCE_ROOT + "graphics/" + "portal.png"));
            missile1Texture = new Texture(Gdx.files.local(RESOURCE_ROOT + "graphics/" + "holybolt.png"));
            otherPlayerTexture = new Texture(Gdx.files.local(RESOURCE_ROOT + "graphics/" + "green.png"));
            minionTexture = new Texture(Gdx.files.local(RESOURCE_ROOT + "graphics/" + "minion.png"));
            errorTexture = new Texture(Gdx.files.local(RESOURCE_ROOT + "graphics/" + "error.png"));

            try {
                buttonTexture = new Texture(Gdx.files.local(RESOURCE_ROOT + "graphics/" + "button1.png"));

                buttonTextureRegion = new TextureRegion(buttonTexture);
                buttonTexRegionDrawable = new TextureRegionDrawable(buttonTextureRegion);

                button = new ImageButton(buttonTexRegionDrawable);

            } catch (Exception e) {
            }

            menuButton = new Image(portalTexture);
            System.out.println("entity textures loaded succesfully");
        } catch (Exception e) {
            System.out.println("Failed loading entity textures: " + e);
        }
    }

    /**
     * This method renders the ingame menu
     */
    // Draws ingame menu
    private void ShowMenu(final State s) {
        if (s.equals(INMENU)) {

            widthAlign = 300;

            font3.draw(batch, "Legend", 140, 500, 0, Align.right, false);
            

            setMenuIcon(125, 400, player, "PLAYER: ");
            setMenuIcon(125, 350, enemy, "ENEMY: ");
            setMenuIcon(125, 300, otherP, "ALLIES: ");
            setMenuIcon(125, 250, minion, "MINION: ");
        }
    }

    private void setMenuIcon(int x, int y, Sprite sprite, String text) {

        int newY = (int) sprite.getHeight() / 2;

        sprite.setPosition(x, y);
        font3.getData().setScale(1.3f);

        // Draw
        font3.draw(batch, text, x - 10, y + newY, 0, Align.right, false);
        sprite.draw(batch);
    }

    /**
     * Loads Sprites for entity types
     */
    private void loadSprites() {
        this.player = new Sprite(playerTexture, 0, 0, sizeL, sizeL);
        this.enemy = new Sprite(enemyTexture, 0, 0, sizeL, sizeL);
        this.otherP = new Sprite(otherPlayerTexture, 0, 0, sizeL, sizeL);
        this.minion = new Sprite(minionTexture, 0, 0, sizeS, sizeS);
    }

    private Sprite setSprite(Entity entity) {
        Sprite sprite;

        // Loads appropriate sprites
        if (entity.getOwner().equals(LocalData.getClientID())) {
            sprite = new Sprite(playerTexture, 0, 0, sizeL, sizeL);
        } else if (entity.getType() == ENEMY) {
            sprite = new Sprite(enemyTexture, 0, 0, sizeL, sizeL);
        } else if (entity.getType() == PORTAL) {
            sprite = new Sprite(portalTexture, 0, 0, sizeL, sizeL);
        } else if (entity.getType() == HOLYBOLT) {
            sprite = new Sprite(missile1Texture, 0, 0, sizeS, sizeS);
        } else if (entity.getType() == PLAYER && entity.getID() != LocalData.getClientID()) {
            sprite = new Sprite(otherPlayerTexture, 0, 0, sizeL, sizeL);
        } else if (entity.getType() == MINION && entity.getID() != LocalData.getClientID()) {
            sprite = new Sprite(minionTexture, 0, 0, sizeS, sizeS);
        } else {
            sprite = new Sprite(errorTexture, 0, 0, sizeL, sizeL);
        }
        entity.setSprite(sprite);

        return sprite;
    }

    /**
     * This method draws the background image
     *
     * @param entity an entity from world
     */
    private void drawBG() {

        try {
            if (LocalData.getLocation().contentEquals("arena")) {
                backgroundSprite = new Sprite(backgroundTexture_2);
            } else if (LocalData.getLocation().contentEquals("wilderness")) {
                backgroundSprite = new Sprite(backgroundTexture_1);
            } else if (LocalData.getLocation().contentEquals("wilderness_east")) {
                backgroundSprite = new Sprite(backgroundTexture_3);
            } else if (LocalData.getLocation().contentEquals("wilderness_west")) {
                backgroundSprite = new Sprite(backgroundTexture_4);
            } else if (LocalData.getLocation().contentEquals("cave")) {
                backgroundSprite = new Sprite(backgroundTexture_5);
            } else {
                backgroundSprite = new Sprite(errorBG);
            }
        } catch (Exception e) {
            System.out.println("Error in render: " + e);
        }
        backgroundSprite.draw(batch);

    }

    /**
     * This method draws entities
     *
     * @param entity an entity from world
     */
    private void drawEntity(Entity entity) {
        if (entity.getSprite() == null) {

            Sprite sprite = setSprite(entity);

            Vector2D ownVelocity = entity.getVelocity();
            Vector2 velocity = new Vector2(ownVelocity.getX(), ownVelocity.getY());
            /*
             den her vÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¦k?
             Vector2 vTarget = new Vector2(entity.getvxg(), entity.getvyg());
             */
            Interpolation interpolation = Interpolation.linear;

            elapsed = LocalData.getDt() - lastTime;
            lastTime = elapsed;
            float updateTime = LocalData.getUpdateTime();
            float progess = Math.min(1f, elapsed / updateTime);
            float alpha = interpolation.apply(progess);
            /*
             den her ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¦ndres? vTarget??
             velocity.interpolate(vTarget, alpha, interpolation);
             */
//                    float x = entity.getX()*vPos.x;
//                    float y = entity.getY()*vPos.y;
            float x = entity.getX();
            float y = entity.getY();

            sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);

        }

        // Displays Entity Info
        if (entity.getType() == PLAYER || entity.getType() == DOOR || entity.getType() == ENEMY) {
            font1.draw(batch, entity.getName().toString(), entity.getX(), entity.getY() + entity.getSprite().getHeight() + 20, 0, Align.center, false);
            font2.draw(batch, entity.getHp() + "", entity.getX(), entity.getY() + entity.getSprite().getHeight(), 0, Align.center, false);

//        
        }
        // Draw sprite
        entity.getSprite().draw(batch);
    }

    /**
     * Draws sprites to represent entities in world
     *
     * @param world ConcurrentHashMap with entities (game world)
     */
    // Draws sprites
    private void drawSprites(ConcurrentHashMap<UUID, Entity> world) {

        drawBG();

        // Sets sprites for entities
        for (Entity entity : world.values()) {
            drawEntity(entity);
        }
    }
}
