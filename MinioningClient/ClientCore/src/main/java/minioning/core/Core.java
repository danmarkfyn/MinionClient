/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.core;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import minioning.common.data.Entity;
import minioning.common.data.LocalData;
import static minioning.common.data.LocalData.getPlaying;
import minioning.common.services.IGameInitializer;
import minioning.common.services.IProcessingService;
import minioning.common.services.IWorldUpdate;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 *
 * @author Jakob & Mads
 */

public class Core implements Runnable {

    private Map<UUID, Entity> world = new ConcurrentHashMap<>();
    private final Lookup lookup = Lookup.getDefault();


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
                    for (IGameInitializer installService : getGameInitializer()) {
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
