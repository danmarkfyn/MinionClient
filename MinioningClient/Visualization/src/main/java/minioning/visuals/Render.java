/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.visuals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import minioning.common.data.Entity;
import minioning.common.data.LocalData;

/**
 *
 * @author Jakob
 */
public class Render {

    private static final String RESOURCE_ROOT = "../../../Core/src/main/resources/";

    private Texture backgroundTexture_1;
    private Texture backgroundTexture_2;
    private static Sprite backgroundSprite;

    //  Get local values
    private float width = LocalData.getWidth();
    private float height = LocalData.getWidth();

    public Render() {

//        try {
//        } catch (Exception e) {
//            System.out.println("Error loading textures: " + e);
//        }
//        this.cam = cam;
//        this.mapRenderer = new OrthogonalTiledMapRenderer((TiledMap) gameData.getMap());
    }

    public void render(ConcurrentHashMap<UUID, Entity> world) {

        // Background is behind entities, foreground is in front of entities
//        int[] bglayers = {0};
//        int[] fglayers = {1, 2};
        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Run render methods
        drawSprites(world);
        drawHud();
    }

// Draws the HeadUp Display
    private void drawHud() {

//        for (Entity entity : world.values()) {
//                if (entity.getOwner().equals(LocalData.getClientID())) {
//          
//        }
        // Set up 
        SpriteBatch batch = new SpriteBatch();
        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2f);

        // Draw
        batch.begin();
        font.draw(batch, "In: " + LocalData.getLocation(), width - 155, LocalData.getHeight(), 150, Align.right, false);
        font.draw(batch, "Logged in as: " + LocalData.getUser(), 100, LocalData.getHeight(), 150, Align.right, false);
        batch.end();

        // Dispose of objects
        batch.dispose();
        font.dispose();
    }

    public void loadTextures() {

        System.out.println("Loading textures");
        try {
            this.backgroundTexture_2 = new Texture(RESOURCE_ROOT + "map/" + "arena.png");
            this.backgroundTexture_1 = new Texture(RESOURCE_ROOT + "map/" + "wilderness.png");
            System.out.println("Textures loaded succesfully");
        } catch (Exception e) {
            System.out.println("Failed to load textures: " + e);
        }

    }

    // Draws sprites
    private void drawSprites(ConcurrentHashMap<UUID, Entity> world) {

        SpriteBatch batch = new SpriteBatch();
        batch.begin();

        for (Entity entity : world.values()) {
            System.out.println(entity.getOwner() + "    " + LocalData.getClientID());
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

                Texture texture;
                if (entity.getSprite() == null) {
                    if (entity.getOwner().equals(LocalData.getClientID())) {

                        texture = new Texture(Gdx.files.local(RESOURCE_ROOT + "graphics/" + "blue.png"));
                    } else {
                        texture = new Texture(Gdx.files.local(RESOURCE_ROOT + "graphics/" + "red.png"));
                    }
                    Sprite sprite = new Sprite(texture, 0, 0, 50, 50);
                    entity.setSprite(sprite);
                    sprite.setPosition(entity.getX() - sprite.getWidth() / 2, entity.getY() - sprite.getHeight() / 2);
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
