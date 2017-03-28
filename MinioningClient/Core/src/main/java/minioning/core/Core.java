/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import minioning.common.data.Entity;
import minioning.common.services.IPluginService;
import minioning.common.services.IProcessingService;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import java.util.UUID;
import minioning.common.data.EventData;
import static minioning.common.data.EventData.getEventData;
import minioning.common.data.Events;


public class Core implements ApplicationListener {
    
    private static EventData ed;
    private Map<UUID, Entity> world = new ConcurrentHashMap<>();
    private ShapeRenderer sr;
    private final Lookup lookup = Lookup.getDefault();
    private List<IPluginService> gamePlugins = new CopyOnWriteArrayList<>();
    private Lookup.Result<IPluginService> result;
    
    
    
    @Override
    public void create() {
//        sr = new ShapeRenderer();

        Lookup.Result<IPluginService> result = lookup.lookupResult(IPluginService.class);
        result.addLookupListener(lookupListener);
        gamePlugins = new ArrayList<>(result.allInstances());
        result.allItems();

//        Entity player = new Entity(,"d");
//        world.put(player.getName(), player);
       
        for (IPluginService plugin : gamePlugins) {
            plugin.start();
        }
        update();
        System.out.println(lookup.lookupAll(IProcessingService.class).size() + " entity processors was found");
    }

    @Override
    public void render() {
        update();
        
        //input test
          if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
//              System.out.println("X= " + Gdx.input.getX() + " og Y= " + Gdx.input.getY());
              String movement = ";" + Gdx.input.getX() + ";" + Gdx.input.getY();
              getEventData().put(Events.LOGIN, movement);
              System.out.println(movement);
          }
        
//        for (Entity entity : world.values()) {
//
//            sr.setColor(0, 1, 1, 0);
//
//            sr.begin(ShapeRenderer.ShapeType.Line);
//
//            sr.circle(entity.getX(), entity.getY(), 100);
//
//            sr.end();
//        }
    }

    private void update() {
//        //Update
        for (IProcessingService processorService : getProcessingServices()) {
            for (Entity e : world.values()) {
                processorService.process(world, e);
            }
        }
    }

    private void draw() {
    }

    @Override
    public void resize(int width, int height) {
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

    private Collection<? extends IProcessingService> getProcessingServices() {
        return lookup.lookupAll(IProcessingService.class);
    }
    private final LookupListener lookupListener = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {

//            Collection<? extends IPluginService> updated = result.allInstances();
//
//            for (IPluginService us : updated) {
//                // Found modules
//                if (!gamePlugins.contains(us)) {
//                    us.start();
//                    gamePlugins.add(us);
//                }
//            }
//
//            // Uninstall modules
//            for (IPluginService gs : gamePlugins) {
//                if (!updated.contains(gs)) {
//                    gs.stop();
//                    gamePlugins.remove(gs);
//                }
//            }
        }
    };
}
