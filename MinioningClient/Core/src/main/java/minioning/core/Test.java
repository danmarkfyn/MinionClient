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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import minioning.common.data.Entity;
import minioning.common.services.IPluginService;
import minioning.common.services.IProcessingService;
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

        Entity player = new Entity("Player", 500, 200);
        world.put(player.getName(), player);

        for (IPluginService plugin : gamePlugins) {
            plugin.start();
        }

        System.out.println(lookup.lookupAll(IProcessingService.class).size() + " entity processors was found");
    }

    
    public void update(){
                for (IProcessingService processorService : getProcessingServices()) {
            for (Entity e : world.values()) {
                processorService.process(world, e);
            }
        }
    }
    
    @Override
    public void run() {
      update();
    }

    private Collection<? extends IProcessingService> getProcessingServices() {
        return lookup.lookupAll(IProcessingService.class);
    }

    private final LookupListener lookupListener = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {

        }
    };
}
