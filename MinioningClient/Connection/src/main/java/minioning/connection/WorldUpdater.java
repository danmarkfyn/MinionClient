/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.connection;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import minioning.common.data.Entity;

/**
 *
 * @author Jakob
 */
public class WorldUpdater {
    
    private static Map<UUID, Entity> tempWorld;
    
    public static void updateWorld(String[] newWorld, Map<UUID, Entity> world){
        tempWorld = new ConcurrentHashMap<UUID, Entity>();
        for(int i = 0; i < newWorld.length; i++){
            Entity newEntity = createEntity(newWorld[i]);
            UUID ID = newEntity.getID();
            tempWorld.put(ID,newEntity);
        }
        world = tempWorld;
    }
    
    
    private static Entity createEntity(String entityString){
        String[] data = entityString.split(";");
        
        UUID ID = UUID.fromString(data[0]);
        String name = data[1];
        int x = Integer.parseInt(data[2]);
        int y = Integer.parseInt(data[3]);
        Entity newEntity = new Entity(ID, name, x, y);
        
        return newEntity;
    }
    
}
