/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.visuals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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


    public Render() {

//        try {
//        } catch (Exception e) {
//            System.out.println("Error loading textures: " + e);
//        }
//        this.cam = cam;
//        this.mapRenderer = new OrthogonalTiledMapRenderer((TiledMap) gameData.getMap());
    }

    public void render(ConcurrentHashMap<UUID, Entity> world) {
        // Process message timer and get next message if available
//        if(activeMessage != null && messageTimer > 0) {
//            messageTimer -= gameData.getDelta();
//        } else {
//            activeMessage = gameData.pollMessage();
//            if(activeMessage != null)
//                messageTimer = 0.3f + MESSAGE_TIMEOUT_PER_CHAR * activeMessage.length();
//        }

        // Background is behind entities, foreground is in front of entities
        int[] bglayers = {0};
        int[] fglayers = {1, 2};

        // Render everything
//        mapRenderer.setView(cam);
//        mapRenderer.render(bglayers);  // Background
        this.drawSprites(world);            // Sprites
//        mapRenderer.render(fglayers);  // Foreground

    }
    /*
     LOAD ALLE TEXTURES HER!
     */

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

    public void drawSprites(ConcurrentHashMap<UUID, Entity> world) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpriteBatch batch = new SpriteBatch();
        batch.begin();

        for (Entity entity : world.values()) {
            System.out.println(entity.getOwner() + "    " + LocalData.getClientID());
            if (entity.getOwner().equals(LocalData.getClientID())) {
                
                String bgString = LocalData.getLocation();
                
                String path = RESOURCE_ROOT + "map/" + entity.getLocation() + ".png";
                System.out.println(path);

                try {
                    if (bgString.contentEquals("arena")) {
                        System.out.println("In Arena");
                        
                        backgroundSprite = new Sprite(backgroundTexture_2);
                    } else {
                        System.out.println("In Wilderness");
                        backgroundSprite = new Sprite(backgroundTexture_1);
                    }
                } catch (Exception e) {
                    System.out.println("Error in render: " + e);
                }

//                backgroundTexture = new Texture(RESOURCE_ROOT + "map/" + entity.getLocation() + ".png");
                backgroundSprite.draw(batch);
            }
        }
        //            // Set entity sprite as texture from graphics folder with specified file name
        for (Entity entity : world.values()) {
       

            
            if (entity.getLocation().equals(LocalData.getLocation())) {
            
            if (entity.getSprite() == null) {
                Texture texture = new Texture(Gdx.files.local(RESOURCE_ROOT + "graphics/" + "player.png"));
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
        batch.end();
        batch.dispose();
    }
    
}
