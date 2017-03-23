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
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.concurrent.ConcurrentHashMap;
import minioning.common.data.EventData;
import static minioning.common.data.EventData.addEvent;
import static minioning.common.data.EventData.getEventData;
import minioning.common.data.Events;
import static minioning.common.data.Events.MOVEMENT;
import static minioning.common.data.LocalData.getClientID;

import static minioning.common.data.LocalData.getWidth;
import static minioning.common.data.LocalData.getHeight;

/**
 *
 * @author Jakob
 */
@ServiceProvider(service = IProcessingService.class)
public class VisualProcess implements IProcessingService, ApplicationListener {

    private ShapeRenderer sr;
    private static Map<String, Entity> world = new ConcurrentHashMap<>();
    private Render render = new Render();

    // Override render() i ApplicationListener i stedet for process (køres konstant)
    
    @Override
    public void process(Map<String, Entity> world, Entity entity) {
        

//        System.out.println("Pr");
//        this.world = world;
                
//        render.drawSprites((ConcurrentHashMap<String, Entity>) world);
//        render.render((ConcurrentHashMap<String, Entity>) world);
        this.world = world;
        

// 
//        
////        Render render = new Render((ConcurrentHashMap<String, Entity>) world);
//        System.out.println("1");
//        
//        System.out.println("2");

    }

    @Override
    public void create() {
        sr = new ShapeRenderer();

//        render();
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void render() {
        
//        System.out.println(world.size());
        
        render.render((ConcurrentHashMap<String, Entity>) world);
        
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

            double x = Gdx.input.getX();
            double y = Gdx.input.getY();

            if (x < 0) {
                x = 1;
            } else if (x > getWidth()) {
                x = getWidth();
            }
            if (y < 0) {
                y = 1;
            } else if (y > getHeight()) {
                y = getHeight();
            }

            String movement = x + ";" + y;
//            System.out.println(movement);
            getEventData().put(Events.MOVEMENT, movement);

        }

//        Entity player = new Entity("Player", 500, 200);
//        Entity flayer = new Entity("Flayer", 400, 200);
//        world.put(player.getName(), player);
//        world.put(flayer.getName(), flayer);
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
