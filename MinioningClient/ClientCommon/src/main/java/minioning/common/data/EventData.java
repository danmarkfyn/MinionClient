/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.common.data;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Jakob
 */
public class EventData {

    private static Map<Events, String> eventData;

    public EventData() {
        eventData = new ConcurrentHashMap<>();
    }
    
    public static Map<Events, String> getEventData(){
        if(eventData == null){
            eventData = new ConcurrentHashMap<>();
        }
        return eventData;
    }
     public static void clearEventData(){
         eventData.clear();
     }

     
     
    public static void addEvent(Events event, String s) {
        eventData.put(event, s);
    }
    
    public static String getData(Events event){
        return eventData.get(event);
        
    }
    
    
    
    public static void removeData(Events event){
        eventData.remove(event);
    }
}
