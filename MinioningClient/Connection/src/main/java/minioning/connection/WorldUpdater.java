/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.connection;

import java.util.Map;
import java.util.UUID;
import minioning.common.data.Entity;
import minioning.common.data.LocalData;

/**
 *
 * @author Jakob & Mads
 */


public class WorldUpdater {

    /**
     * 
     * @param newWorld String array with all entity data send from the server
     * @param world Map with UUID and all the entities on the client which should be processed
     */
    
    public synchronized static void updateWorld(String[] newWorld, Map<UUID, Entity> world) {
        System.out.println(world.size());
        world.clear();
        for (int i = 1; i < newWorld.length; i++) {
            Entity newEntity = createEntity(newWorld[i]);
            if (newEntity.getOwner().equals(LocalData.getClientID())) {
                LocalData.setLocation(newEntity.getLocation());
            }
            UUID ID = newEntity.getID();
            if (!world.containsKey(ID)) {
                world.put(ID, newEntity);
            } else {
                world.replace(ID, newEntity);
            }
        }
    }

    /**
     * 
     * @param entityString String array with data for a single entity, sent from the server. 
     * Used to mirror the entities on the client.
     * @return Returns an instance of the entity class
     */
    
    private static Entity createEntity(String entityString) {
        
        
        String[] data = entityString.split(";");
        UUID ID = UUID.fromString(data[0]);
        String type = data[1];
        String name = data[2];
        int x = Math.round(Float.parseFloat(data[3]));
        int y = Math.round(Float.parseFloat(data[4]));
        float vx = Float.parseFloat(data[5]);
        float vy = Float.parseFloat(data[6]);
        UUID owner = UUID.fromString(data[7]);
        String location = data[8];
        String doorTo = data[9];
        int hp = Integer.parseInt(data[10]);

        Entity newEntity = new Entity(
                ID,
                type,
                name,
                x,
                y,
                vx,
                vy,
                owner,
                location,
                doorTo,
                hp
        );

        return newEntity;
    }
}
