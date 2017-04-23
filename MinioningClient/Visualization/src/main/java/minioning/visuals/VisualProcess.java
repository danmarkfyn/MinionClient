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
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import minioning.common.data.Events;
import static minioning.common.data.Lists.getOutputList;
import minioning.common.data.LocalData;
import static minioning.common.data.LocalData.getWidth;
import static minioning.common.data.LocalData.getHeight;
import static minioning.visuals.State.INGAME;
import static minioning.visuals.State.INMENU;


/**
 *
 * @author Jakob
 */
@ServiceProvider(service = IProcessingService.class)
public class VisualProcess implements IProcessingService, ApplicationListener {

    private static Map<UUID, Entity> world = new ConcurrentHashMap<UUID, Entity>();
    private Render render;
    private State state = INGAME;
    private int p = 1;

    @Override
    public void process(Map<UUID, Entity> world, Entity entity) {

        this.world = world;

    }

    @Override
    public void create() {
        Gdx.graphics.setTitle("The Minioning");

        render = new Render();
        state = INGAME;
        p = 0;
    }

    private String mouseClick(Events event) {

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

        String movement = LocalData.getClientID().toString() + ";" + event + ";" + x + ";" + y;
        return movement;
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void render() {
 render.render((ConcurrentHashMap<UUID, Entity>) world, state);
        if (state == INGAME) {
            if (getOutputList().containsKey(Events.MOVEMENT) == false) {
               
                if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                    String click = mouseClick(Events.MOVEMENT);
                    System.out.println(mouseClick(Events.MOVEMENT));
                    getOutputList().put(Events.MOVEMENT, click);
                }
            } else {
                
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            getOutputList().put(Events.SKILLQ, mouseClick(Events.SKILLQ));
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
        if (Gdx.input.isKeyPressed(Input.Keys.P)) {

            if (p == 0) {
                state = INGAME;
                p = 1;
            } else if (p == 1) {
                state = INMENU;
                p = 0;
            }

        }
    }
//
//        switch (state) {
//            case RUN:
//                Gdx.gl.glClearColor(0, 0, 0, 1);
//                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//                render.render((ConcurrentHashMap<UUID, Entity>) world);
////                System.out.println("Render");
//                break;
//            case PAUSE:
//                Gdx.gl.glClearColor(0, 0, 0, 1);
////                System.out.println("Nothing");
//        }
//    }

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
