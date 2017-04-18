/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.visuals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import minioning.common.data.Entity;
import minioning.common.data.LocalData;

/**
 *
 * @author Jakob
 */
public class Render {

    public static Texture backgroundTexture;
    public static Sprite backgroundSprite;
    private static final String RESOURCE_ROOT = "../../../Core/src/main/resources/";

//    private final ConcurrentHashMap<String, Entity> world;
//    private final OrthographicCamera cam;
//    private final TiledMapRenderer mapRenderer;
//    public Render(ConcurrentHashMap<String, Entity> world, OrthographicCamera cam) {
    public Render() {

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
    public void drawSprites(ConcurrentHashMap<UUID, Entity> world) {

        SpriteBatch batch = new SpriteBatch();
        batch.begin();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (Entity entity : world.values()) {
//            System.out.println(entity.getOwner() + "    " + LocalData.getClientID());
            if (entity.getOwner().equals(LocalData.getClientID())) {

//              backgroundTexture = new Texture(RESOURCE_ROOT + "map/grassland.png");
                String path = RESOURCE_ROOT + "map/" + entity.getLocation() + ".png";
//                System.out.println(path);
                backgroundTexture = new Texture(RESOURCE_ROOT + "map/" + entity.getLocation() + ".png");
                backgroundSprite = new Sprite(backgroundTexture);

                backgroundSprite.draw(batch);
            }
        }
        for (Entity entity : world.values()) {
//            if (entity.getSpriteName() == null) continue;
//            int width = entity.getWidth();
//            int height = entity.getHeight();
//            
//            // Set entity sprite as texture from graphics folder with specified file name

            if (entity.getSprite() == null) {
                Texture texture = new Texture(Gdx.files.local(RESOURCE_ROOT + "graphics/" + "player.png"));
                Sprite sprite = new Sprite(texture, 0, 0, 50, 50);
                entity.setSprite(sprite);
                int x = entity.getX();
                int y = entity.getY();
                Interpolation interpolation = Interpolation.linear;
                float dt = LocalData.getDt();
                float progress = Math.min(1f, dt);

//                if (entity.getVelocity() != null) {
//                    System.out.println("-----------------------------------------------------------------------");
//                    System.out.println("entity vel before inter:");
//                    System.out.println(entity.getVelocity().toString());
////                System.out.println(entity.getVelocity().x + "; " + entity.getVelocity().y);
//                    System.out.println("entity pos before inter:");
//                    System.out.println(entity.getX() + "; " + entity.getY());
//
//                    interpolation.apply(dt);
//                    entity.getVelocity().interpolate(Vector2.Zero, dt, interpolation);
//
//                    System.out.println("entity vel after inter:");
//                    System.out.println(entity.getVelocity().toString());
////                System.out.println(entity.getVelocity().x + "; " + entity.getVelocity().y);
//                    System.out.println("entity pos after inter:");
//                    System.out.println(entity.getX() + "; " + entity.getY());
//                    System.out.println("------------------------------------------------------------------------");
//                }

                sprite.setPosition(entity.getX(), entity.getY());
//                sprite.setPosition(entity.getX() - sprite.getWidth() / 2, entity.getY() - sprite.getHeight() / 2);

            }
            // Set bounds and rotation
            Sprite sprite = entity.getSprite();
//            sprite.setBounds(entity.getX() - width / 2, entity.getY() - height / 2, width, height);
//            sprite.setRotation((float)Math.toDegrees(entity.getRadians()));
            sprite.draw(batch);
        }
        batch.end();
        batch.dispose();
    }
}
