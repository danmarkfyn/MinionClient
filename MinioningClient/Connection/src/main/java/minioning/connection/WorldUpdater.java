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
    
    public synchronized static void updateWorld(String[] newWorld, Map<UUID, Entity> world){
        tempWorld = new ConcurrentHashMap<UUID, Entity>();
        world.clear();
        for(int i = 1; i < newWorld.length; i++){
            Entity newEntity = createEntity(newWorld[i]);
            UUID ID = newEntity.getID();
            tempWorld.put(ID,newEntity);
//            System.out.println("entity created: " + newEntity.getName());
            if(world.containsKey(ID)){
                world.replace(ID, newEntity);
                System.out.println("updated: " + ID);
            }else{
                world.put(ID, newEntity);
                System.out.println("created: " + ID);
            }
        }
    }
    
    
    private static Entity createEntity(String entityString){
        String[] data = entityString.split(";");
        
        UUID ID = UUID.fromString(data[0]);
        System.out.println("data[0]: " + data[0]);
        System.out.println("ID     : " + ID);
        String name = data[1];
        float fx = Float.parseFloat(data[2]);
        float fy = Float.parseFloat(data[3]);
        int x = Math.round(fx);
        int y = Math.round(fy);
        Entity newEntity = new Entity(ID, name, x, y);
        
        return newEntity;
    }
    
}
