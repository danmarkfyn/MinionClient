/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.visuals;

import java.util.Map;
import java.util.UUID;
import minioning.common.data.Entity;
import minioning.common.services.IPluginService;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Jakob
 */


@ServiceProvider(service = IPluginService.class)
public class VisualPluginHandler implements IPluginService{

    @Override
    public void start(Map<UUID, Entity> world) {
        System.out.println("Starting Visuals");
    }

    @Override
    public void stop(Map<UUID, Entity> world) {
        System.out.println("Stopping Visuals");
    }
    
}
