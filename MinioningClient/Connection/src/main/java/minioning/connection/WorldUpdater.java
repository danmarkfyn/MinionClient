/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.connection;

import com.badlogic.gdx.math.Vector2;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import minioning.common.data.Entity;
import minioning.common.data.LocalData;

/**
 *
 * @author Jakob
 */
public class WorldUpdater {
    
//    private static Map<UUID, Entity> tempWorld;
    
    public synchronized static void updateWorld(String[] newWorld, Map<UUID, Entity> world){
//        tempWorld = new ConcurrentHashMap<UUID, Entity>();
        world.clear();
        for(int i = 1; i < newWorld.length; i++){
            Entity newEntity = createEntity(newWorld[i]);
            if (newEntity.getOwner().equals(LocalData.getClientID())){
             
              LocalData.setLocation(newEntity.getLocation());
              
            }
                
            UUID ID = newEntity.getID();
//            System.out.println("ID: " + ID);
//            tempWorld.put(ID,newEntity);
            if(!world.containsKey(ID)){
                world.put(ID, newEntity);
//                System.out.println("created: " + ID);
            }else{
                world.replace(ID, newEntity);
//                System.out.println("updated: " + ID);
            }
        }
    }
    
    
    private static Entity createEntity(String entityString){
//        System.out.println("Entity: " + entityString);
        String[] data = entityString.split(";");
////        System.out.println("testing data in createentity");
////        for(String out : data){
////            System.out.println(out);
////        }
        UUID ID = UUID.fromString(data[0]);
        String name = data[1];
        float fx = Float.parseFloat(data[2]);
        float fy = Float.parseFloat(data[3]);
        int x = Math.round(fx);
        int y = Math.round(fy);
        Vector2 velocity = new Vector2(Float.parseFloat(data[4]),Float.parseFloat(data[5]));
        String location = data[7];
        Entity newEntity = new Entity(ID, name, x, y, location);
        newEntity.setVelocity(velocity);
        newEntity.setOwner(UUID.fromString(data[6]));
        return newEntity;
    }
    
}
