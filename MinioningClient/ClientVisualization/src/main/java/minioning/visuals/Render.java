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

    private static Sprite backgroundSprite;

    // textures for entity and bg errors
    private Texture errorTexture;
    private Texture errorBG;

    // Shaperender
    private ShapeRenderer sr = new ShapeRenderer();
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

    /**
     * Class constructer
     */
    public Render() {
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

        drawSprites(world);
        drawHud();
        if (s == INMENU) {
            ShowMenu(s);
        } else {
            widthAlign = 100;
        }
        drawCustomCursor();

    }

    /**
     * This method draws the Head-Up Display (HUD)
     */
// Draws the HeadUp Display
    private void drawHud() {

        // Set up 
        SpriteBatch batch = new SpriteBatch();

        // Draw
        batch.begin();
        font.draw(batch, "In: " + LocalData.getLocation(), width - 155, LocalData.getHeight(), 150, Align.right, false);
        font.draw(batch, "Logged in as: " + LocalData.getUser(), widthAlign + 60, LocalData.getHeight(), 150, Align.right, false);
        batch.end();

        // Dispose of objects
        batch.dispose();

    }

    private void drawCustomCursor() {
        // Sets custom cursor
        Cursor customCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal(RESOURCE_ROOT + "graphics/" + "cursor.png")), 16, 16);
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

            sr.begin(ShapeType.Filled);
            sr.setColor(Color.DARK_GRAY);
            sr.rect(0, 0, 200, height);
            sr.end();

            SpriteBatch batch = new SpriteBatch();
            batch.begin();

            setMenuIcon(batch, 125, 400, player, "PLAYER: ");
            setMenuIcon(batch, 125, 350, enemy, "ENEMY: ");
            setMenuIcon(batch, 125, 300, otherP, "ALLIES: ");
            setMenuIcon(batch, 125, 250, minion, "MINION: ");

//            ss = setMenuIcon(batch, 50, 200, player, RESOURCE_ROOT);
//
//            button.setX(75);
//            button.setY(height / 2);
//
//            stage = new Stage(new ScreenViewport());
//            stage.addActor(button);
//            Gdx.input.setInputProcessor(stage);
//
//            stage.act(Gdx.graphics.getDeltaTime());
//            stage.draw();
//            ss.draw(batch);
            batch.end();
            batch.dispose();
        }
    }

    private void loadSprites() {
        this.player = new Sprite(playerTexture, 0, 0, sizeL, sizeL);
        this.enemy = new Sprite(enemyTexture, 0, 0, sizeL, sizeL);
        this.otherP = new Sprite(otherPlayerTexture, 0, 0, sizeL, sizeL);
        this.minion = new Sprite(minionTexture, 0, 0, sizeS, sizeS);
    }

    private void setMenuIcon(SpriteBatch batch, int x, int y, Sprite sprite, String text) {

        int newY = (int) sprite.getHeight() / 2;

        sprite.setPosition(x, y);

        font3.getData().setScale(1.3f);

        // Draw
        font3.draw(batch, text, x - 10, y + newY, 0, Align.right, false);

        sprite.draw(batch);

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
     * Draws sprites to represent entities in world
     *
     * @param world ConcurrentHashMap with entities (game world)
     */
    // Draws sprites
    private void drawSprites(ConcurrentHashMap<UUID, Entity> world) {
//        System.out.println(world.size());
        SpriteBatch batch = new SpriteBatch();
        batch.begin();

        for (Entity entity : world.values()) {

            if (entity.getOwner().equals(LocalData.getClientID())) {

                String bgString = LocalData.getLocation();

                try {
                    if (bgString.contentEquals("arena")) {
                        backgroundSprite = new Sprite(backgroundTexture_2);
                    } else if (bgString.contentEquals("wilderness")) {
                        backgroundSprite = new Sprite(backgroundTexture_1);
                    } else if (bgString.contentEquals("wilderness_east")) {
                        backgroundSprite = new Sprite(backgroundTexture_3);
                    } else if (bgString.contentEquals("wilderness_west")) {
                        backgroundSprite = new Sprite(backgroundTexture_4);
                    } else if (bgString.contentEquals("cave")) {
                        backgroundSprite = new Sprite(backgroundTexture_5);
                    } else {
                        backgroundSprite = new Sprite(errorBG);
                    }
                } catch (Exception e) {
                    System.out.println("Error in render: " + e);
                }
                backgroundSprite.draw(batch);
            }
        }

        // Sets sprites for entities
        for (Entity entity : world.values()) {
            if (entity.getLocation().equals(LocalData.getLocation())) {
                if (entity.getSprite() == null) {

                    Sprite sprite = setSprite(entity);

                    Vector2D ownVelocity = entity.getVelocity();
                    Vector2 velocity = new Vector2(ownVelocity.getX(), ownVelocity.getY());
                    /*
                     den her vÃƒÂ¦k?
                     Vector2 vTarget = new Vector2(entity.getvxg(), entity.getvyg());
                     */
                    Interpolation interpolation = Interpolation.linear;

                    elapsed = LocalData.getDt() - lastTime;
                    lastTime = elapsed;
                    float updateTime = LocalData.getUpdateTime();
                    float progess = Math.min(1f, elapsed / updateTime);
                    float alpha = interpolation.apply(progess);
                    /*
                     den her ÃƒÂ¦ndres? vTarget??
                     velocity.interpolate(vTarget, alpha, interpolation);
                     */
//                    float x = entity.getX()*vPos.x;
//                    float y = entity.getY()*vPos.y;
                    float x = entity.getX();
                    float y = entity.getY();

                    sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);

                }

                // Draw sprite
                Sprite sprite = entity.getSprite();
                sprite.draw(batch);

                // Displays Entity Info
                if (entity.getType() == PLAYER || entity.getType() == DOOR || entity.getType() == ENEMY) {
                    font1.draw(batch, entity.getName().toString(), entity.getX(), entity.getY() + entity.getSprite().getHeight() + 20, 0, Align.center, false);
                    font2.draw(batch, entity.getHp() + "", entity.getX(), entity.getY() + entity.getSprite().getHeight(), 0, Align.center, false);

                }

            }
        }
        // Dispose of objects
        batch.end();
        batch.dispose();
    }

}