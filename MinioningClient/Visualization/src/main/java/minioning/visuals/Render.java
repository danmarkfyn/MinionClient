/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.visuals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import java.util.concurrent.ConcurrentHashMap;
import minioning.common.data.Entity;
/**
 *
 * @author Jakob
 */
public class Render {
    
    private static final String RESOURCE_ROOT = "../../../Core/src/main/resources/";

    
//    private final ConcurrentHashMap<String, Entity> world;
//    private final OrthographicCamera cam;
    
//    private final TiledMapRenderer mapRenderer;
//    public Render(ConcurrentHashMap<String, Entity> world, OrthographicCamera cam) {
    public Render() {

//        this.cam = cam;
//        this.mapRenderer = new OrthogonalTiledMapRenderer((TiledMap) gameData.getMap());
    }
    
    public void render(ConcurrentHashMap<String, Entity> world) {
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
    
    public void drawSprites(ConcurrentHashMap<String, Entity> world) {
        SpriteBatch batch = new SpriteBatch();
        batch.begin();
        for (Entity entity : world.values()) {
//            if (entity.getSpriteName() == null) continue;
//            int width = entity.getWidth();
//            int height = entity.getHeight();
//            
//            // Set entity sprite as texture from graphics folder with specified file name
//            if(entity.getSprite() == null) {
                Texture texture = new Texture(Gdx.files.local(RESOURCE_ROOT + "graphics/" + "player.png"));
                Sprite sprite = new Sprite(texture, 0, 0, 50, 50);
                entity.setSprite(sprite);
                sprite.setPosition(entity.getX()- sprite.getWidth()/2, entity.getY()-sprite.getHeight()/2);
//            }

            // Set bounds and rotation
//            Sprite sprite = entity.getSprite();
//            sprite.setBounds(entity.getX() - width / 2, entity.getY() - height / 2, width, height);
//            sprite.setRotation((float)Math.toDegrees(entity.getRadians()));
                sprite.draw(batch);
        
        }
        batch.end();
        batch.dispose();
    }
}
