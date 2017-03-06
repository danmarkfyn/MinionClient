/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.visuals;

import java.util.Map;
import minioning.common.data.Entity;
import minioning.common.services.IProcessingService;
import org.openide.util.lookup.ServiceProvider;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.concurrent.ConcurrentHashMap;
/**
 *
 * @author Jakob
 */


@ServiceProvider (service = IProcessingService.class)
public class VisualProcess implements IProcessingService, ApplicationListener{
private ShapeRenderer sr;
 private Map<String, Entity> world = new ConcurrentHashMap<>();
 
 
    public void process(Map<String, Entity> world, Entity entity) {
                Entity player = new Entity("Player", 500, 200);
        world.put(player.getName(), player);
        
//        render();
        
    
        
    }

    @Override
    public void create() {
         sr = new ShapeRenderer();
        render();
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void render() {
          Entity player = new Entity("Player", 500, 200);
           Entity flayer = new Entity("Flayer", 400, 200);
        world.put(player.getName(), player);
        world.put(flayer.getName(), flayer);
//        System.out.println(world.values().size());
            for (Entity entity : world.values()) {

            sr.setColor(0, 1, 1, 0);

            sr.begin(ShapeRenderer.ShapeType.Line);

            sr.circle(entity.getX(), entity.getY(), 100);

            sr.end();
            }
        
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
