/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.connection;

import java.util.Map;
import java.util.UUID;
import minioning.common.data.Entity;
import minioning.common.data.EntityType;
import minioning.common.data.LocalData;
import minioning.common.data.Vector2D;

/**
 *
 * @author Jakob
 */
public class WorldUpdater {

    public synchronized static void updateWorld(String[] newWorld, Map<UUID, Entity> world){

        world.clear();
        for(int i = 1; i < newWorld.length; i++){
            Entity newEntity = createEntity(newWorld[i]);
            if (newEntity.getOwner().equals(LocalData.getClientID())){
                
              LocalData.setLocation(newEntity.getLocation());
              
            }
                
            UUID ID = newEntity.getID();
            if(!world.containsKey(ID)){
                world.put(ID, newEntity);
            }else{
                world.replace(ID, newEntity);
            }
        }
    }
    
    
    private static Entity createEntity(String entityString){
        String[] data = entityString.split(";");
//        System.out.println("testing data in createentity");
//        for(String out : data){
//            System.out.println(out);
//        }
        UUID ID = UUID.fromString(data[0]);
        String name = data[1];
        float fx = Float.parseFloat(data[2]);
        float fy = Float.parseFloat(data[3]);
        int x = Math.round(fx);
        int y = Math.round(fy);
        String location = data[5];
        Entity newEntity = new Entity(ID, name, x, y, location);
        newEntity.setOwner(UUID.fromString(data[4]));
        newEntity.setType(EntityType.valueOf(data[7]));
        float vxp = Float.parseFloat(data[7]);
        float vyp = Float.parseFloat(data[8]);
        Vector2D vPosition = new Vector2D(vxp, vyp);
        newEntity.setvPosition(vPosition);
        float vxg = Float.parseFloat(data[9]);
        float vyg = Float.parseFloat(data[10]);
        Vector2D vTarget = new Vector2D(vxg, vyg);
        newEntity.setvPosition(vTarget);
        return newEntity;
    }
    
}
