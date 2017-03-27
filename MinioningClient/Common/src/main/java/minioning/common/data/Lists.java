package minioning.common.data;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Jakob
 */
public class Lists {
    
    private static ConcurrentHashMap<Events, String> outputList;
    
    public static ConcurrentHashMap<Events, String> getOutputList(){
        ConcurrentHashMap<Events, String> outputListCopy = getSingletonOutputList();
//        clearOutput();
        return outputListCopy;
    }
    
    private synchronized static ConcurrentHashMap<Events, String> getSingletonOutputList(){
        if(outputList == null){
            outputList = new ConcurrentHashMap<Events, String>();
        }
        return outputList;
    }
    
    public static void putOutput(Events event, String data){
        getSingletonOutputList().putIfAbsent(event, data);
    }
    
    private static void clearOutput(){
        getSingletonOutputList().clear();
    }
    
    public static void removeEvent(Events event, String data){
        getSingletonOutputList().remove(event, data);
    }
    
    
}
