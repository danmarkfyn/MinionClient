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
import com.badlogic.gdx.graphics.GL20;
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
    private Render render;

    @Override
    public void process(Map<UUID, Entity> world, Entity entity) {

        this.world = world;

    }

    @Override
    public void create() {
        Gdx.graphics.setTitle("The Minioning");

        sr = new ShapeRenderer();
        render = new Render();
        render.loadTextures();
    }

    private String mouseClick() {

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
        return movement;
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        render.render((ConcurrentHashMap<UUID, Entity>) world);

        if (getOutputList().containsKey(Events.MOVEMENT) == false) {

            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                String click = mouseClick();
                System.out.println(mouseClick());
                getOutputList().put(Events.MOVEMENT, click);
            }
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
