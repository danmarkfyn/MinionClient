/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import minioning.common.data.Entity;
import minioning.common.data.LocalData;
import static minioning.common.data.LocalData.getClientID;
import minioning.common.services.IGameInitializer;
import minioning.common.services.IPluginService;
import minioning.common.services.IProcessingService;
import minioning.common.services.IWorldUpdate;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 *
 * @author Jakob
 */
public class Test implements Runnable {

    private Map<String, Entity> world = new ConcurrentHashMap<>();
    private final Lookup lookup = Lookup.getDefault();
    private List<IPluginService> gamePlugins = new CopyOnWriteArrayList<>();
    private Lookup.Result<IPluginService> result;

    public Test() {

        Lookup.Result<IPluginService> result = lookup.lookupResult(IPluginService.class);
        result.addLookupListener(lookupListener);
        gamePlugins = new ArrayList<>(result.allInstances());
        result.allItems();

//        Entity player = new Entity(getClientID(),"Player");
//        world.put(player.getName(), player);

        for (IPluginService plugin : gamePlugins) {
            plugin.start();
        }

        System.out.println(lookup.lookupAll(IProcessingService.class).size() + " entity processors was found");

        //runs once to install visualisation
        System.out.println("Looking for IGameInitializer!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        for (IGameInitializer installService : getGameInitializer()) {
            System.out.println("Found IGameInitializer!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            installService.install();
        }
    }

    public void update() {


                
                
        for (IProcessingService processorService : getProcessingServices()) {
            for (Entity e : world.values()) {
                processorService.process(world, e);
            }
        }
        for (IWorldUpdate worldUpdate : getWorldUpdate()) {
          
                worldUpdate.update(world);
            
        }
    }

    
    // main thread for the graphical application
    @Override
    public void run() {
        
        
           Entity player = new Entity(getClientID(),"Player");
           player.setX(200);
           player.setY(LocalData.getHeight() - 150);
           
           UUID id =  UUID.randomUUID();
           
           Entity player2 = new Entity(id,"Player2");
           player2.setX(100);
           player2.setY(LocalData.getHeight() - 100);
           
        world.put(player.getName(), player);
        world.put(player2.getName(), player2);
        while (true) {
            update();
        }
    }

    private Collection<? extends IProcessingService> getProcessingServices() {
        return lookup.lookupAll(IProcessingService.class);
    }

    private Collection<? extends IGameInitializer> getGameInitializer() {
        return lookup.lookupAll(IGameInitializer.class);
    }
        private Collection<? extends IWorldUpdate> getWorldUpdate() {
        return lookup.lookupAll(IWorldUpdate.class);
    }

    private final LookupListener lookupListener = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {

        }
    };
}
