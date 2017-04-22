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
import static minioning.common.data.LocalData.getPlaying;
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
public class Core implements Runnable {

    private Map<UUID, Entity> world = new ConcurrentHashMap<>();
    private final Lookup lookup = Lookup.getDefault();
    private List<IPluginService> gamePlugins = new CopyOnWriteArrayList<>();
    private Lookup.Result<IPluginService> result;

    public Core() {

        Lookup.Result<IPluginService> result = lookup.lookupResult(IPluginService.class);
        result.addLookupListener(lookupListener);
        gamePlugins = new ArrayList<>(result.allInstances());
        result.allItems();


        for (IPluginService plugin : gamePlugins) {
            plugin.start();
        }

        System.out.println(lookup.lookupAll(IProcessingService.class).size() + " processors was found");

    }

    public void updateEntities() {
        for (IProcessingService processorService : getProcessingServices()) {
            for (Entity e : world.values()) {
                processorService.process(world, e);
            }
        }
    }

    public void updateConnection() {
        for (IWorldUpdate worldUpdate : getWorldUpdate()) {
            worldUpdate.update(world);
        }
    }

    // main thread for the graphical application
    @Override
    public void run() {

        boolean initialized = false;
        while (true) {
            float dt = System.nanoTime();
            LocalData.setDt(dt);
            updateConnection();
            if (getPlaying()) {
                updateEntities();
                if (!initialized) {
                    
                    //runs once to install visualisation
                    System.out.println("Looking for IGameInitializer!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    for (IGameInitializer installService : getGameInitializer()) {
                        System.out.println("Found IGameInitializer!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        installService.install();
                    }
                    initialized = true;
                }
            }
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
