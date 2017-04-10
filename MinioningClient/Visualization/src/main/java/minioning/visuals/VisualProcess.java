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
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import minioning.common.data.Events;
import static minioning.common.data.Lists.getOutputList;
import minioning.common.data.LocalData;

import static minioning.common.data.LocalData.getWidth;
import static minioning.common.data.LocalData.getHeight;

/**
 *
 * @author Jakob
 */
@ServiceProvider(service = IProcessingService.class)
public class VisualProcess implements IProcessingService, ApplicationListener {

    private ShapeRenderer sr;
    private static Map<UUID, Entity> world = new ConcurrentHashMap<UUID, Entity>();
    private Render render = new Render();

    // Override render() i ApplicationListener i stedet for process (kÃ¸res konstant)
    @Override
    public void process(Map<UUID, Entity> world, Entity entity) {

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

    private String mouseClick() {

//        if (Gdx.input.isKeyJustPressed(Input.Buttons.LEFT)) {  
        int x = Gdx.input.getX();
        int y = Gdx.input.getY();
        y = getHeight() - y;

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

        String movement = LocalData.getClientID().toString() + ";" + Events.MOVEMENT + ";" + x + ";" + y;
//            System.out.println(movement);
        return movement;

    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void render() {

//        System.out.println(world.size());
        render.render((ConcurrentHashMap<UUID, Entity>) world);

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            String click = mouseClick();
            getOutputList().put(Events.MOVEMENT, click);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            getOutputList().put(Events.SKILLQ, mouseClick());
            System.out.println("Q is pressed");
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            getOutputList().put(Events.SKILLW, "");
            System.out.println("W is pressed");
        }

        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            getOutputList().put(Events.SKILLE, "");
            System.out.println("E is pressed");
        }

        for (Entity entity
                : world.values()) {

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
