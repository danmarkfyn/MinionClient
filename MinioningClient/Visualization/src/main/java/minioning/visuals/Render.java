/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.visuals;

import com.badlogic.gdx.scenes.scene2d.EventListener;
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
import com.badlogic.gdx.scenes.scene2d.Event;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.Input;
import static minioning.visuals.State.INMENU;

/**
 *
 * @author Jakob
 */
public class Render {

    private static final String RESOURCE_ROOT = "../../../Core/src/main/resources/";

    private Stage stage;
    // Textures and Images

    // Background
    private Texture backgroundTexture_1;
    private Texture backgroundTexture_2;

    // Entities
    private Texture playerTexture;
    private Texture enemyTexture;
    private Texture portalTexture;
    private static Sprite backgroundSprite;

    // Shaperender
    private ShapeRenderer sr = new ShapeRenderer();

    //  Get local values
    private float width = LocalData.getWidth();
    private float height = LocalData.getWidth();

    // Allignment values for menu/HUD
    private int widthAlign = 100;

    // Buttons
    Image menuButton;

    private Texture buttonTexture;
    private TextureRegion buttonTextureRegion;
    private TextureRegionDrawable buttonTexRegionDrawable;
    private ImageButton button;

    /**
     * Class constructer
     */
    public Render() {

        try {
            loadTextures();
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
        System.out.println(s);
        drawSprites(world);
        drawHud();
        if (s == INMENU) {
            ShowMenu(s);
        }
        widthAlign = 100;

    }

    /**
     * This method draws the Head-Up Display (HUD)
     */
// Draws the HeadUp Display
    private void drawHud() {

        // Set up 
        SpriteBatch batch = new SpriteBatch();
        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2f);

        // Sets custom cursor
        Cursor customCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal(RESOURCE_ROOT + "graphics/" + "cursor.png")), 16, 16);
        Gdx.graphics.setCursor(customCursor);

        // Draw
        batch.begin();
        font.draw(batch, "In: " + LocalData.getLocation(), width - 155, LocalData.getHeight(), 150, Align.right, false);
        font.draw(batch, "Logged in as: " + LocalData.getUser(), widthAlign, LocalData.getHeight(), 150, Align.right, false);
        batch.end();

        // Dispose of objects
        batch.dispose();
        font.dispose();
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
            this.backgroundTexture_2 = new Texture(RESOURCE_ROOT + "map/" + "arena.png");
            this.backgroundTexture_1 = new Texture(RESOURCE_ROOT + "map/" + "wilderness.png");
            System.out.println("bg textures loaded succesfully");
        } catch (Exception e) {
            System.out.println("Failed to load bg textures: " + e);
        }
        System.out.println("Loading entity textures");

        try {
            playerTexture = new Texture(Gdx.files.local(RESOURCE_ROOT + "graphics/" + "blue.png"));
            enemyTexture = new Texture(Gdx.files.local(RESOURCE_ROOT + "graphics/" + "red.png"));
            portalTexture = new Texture(Gdx.files.local(RESOURCE_ROOT + "graphics/" + "portal.png"));

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

            SpriteBatch batch = new SpriteBatch();
            batch.begin();
            sr.begin(ShapeType.Filled);
            sr.setColor(Color.FIREBRICK);
            sr.rect(0, 0, 200, height);
            sr.end();

            button.setX(100);
            button.setY(height / 2);

            stage = new Stage(new ScreenViewport());
            stage.addActor(button);
            Gdx.input.setInputProcessor(stage);

            button.addListener(new ClickListener() {

                @Override
                public boolean handle(Event event) {
                    if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

                        System.out.println("Button Pressed");

                        return true;
                    }
                    return false;
                }
            });
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();

            batch.end();
            batch.dispose();
        }
    }
    float elapsed = 0;
    float lastTime = elapsed;

    /**
     * Draws sprites to represent entities in world
     *
     * @param world ConcurrentHashMap with entities (game world)
     */
    // Draws sprites
    private void drawSprites(ConcurrentHashMap<UUID, Entity> world) {

        SpriteBatch batch = new SpriteBatch();
        batch.begin();

        for (Entity entity : world.values()) {
//            System.out.println(entity.getOwner() + "    " + LocalData.getClientID());
            if (entity.getOwner().equals(LocalData.getClientID())) {

                String bgString = LocalData.getLocation();

                try {
                    if (bgString.contentEquals("arena")) {
                        backgroundSprite = new Sprite(backgroundTexture_2);
                    } else {
                        backgroundSprite = new Sprite(backgroundTexture_1);
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
                    Sprite sprite;

                    // Loads appropriate sprites
                    if (entity.getOwner().equals(LocalData.getClientID())) {
                        sprite = new Sprite(playerTexture, 0, 0, 50, 50);
                    } else if (entity.getType() == ENEMY) {
                        sprite = new Sprite(enemyTexture, 0, 0, 50, 50);
                    } else if (entity.getType() == PORTAL) {
                        sprite = new Sprite(portalTexture, 0, 0, 50, 50);
                    } else {
                        sprite = new Sprite(playerTexture, 0, 0, 50, 50);
                    }
                    entity.setSprite(sprite);

                    Vector2D ownVelocity = entity.getVelocity();
                    Vector2 velocity = new Vector2(ownVelocity.getX(), ownVelocity.getY());
                    /*
                     den her væk?
                     Vector2 vTarget = new Vector2(entity.getvxg(), entity.getvyg());
                     */
                    Interpolation interpolation = Interpolation.linear;

                    elapsed = LocalData.getDt() - lastTime;
                    lastTime = elapsed;
                    float updateTime = LocalData.getUpdateTime();
                    float progess = Math.min(1f, elapsed / updateTime);
                    float alpha = interpolation.apply(progess);
                    /*
                     den her ændres? vTarget??
                     velocity.interpolate(vTarget, alpha, interpolation);
                     */
//                    float x = entity.getX()*vPos.x;
//                    float y = entity.getY()*vPos.y;
                    float x = entity.getX();
                    float y = entity.getY();

                    sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
                }

                // Set bounds and rotation
                Sprite sprite = entity.getSprite();
//            sprite.setBounds(entity.getX() - width / 2, entity.getY() - height / 2, width, height);
//            sprite.setRotation((float)Math.toDegrees(entity.getRadians()));
                sprite.draw(batch);
            }
        }
        // Dispose of objects
        batch.end();
        batch.dispose();
    }
}
