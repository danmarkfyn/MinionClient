package minioning.common.data;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Jakob
 */
public class Lists {

    private static ConcurrentHashMap<Events, String> outputList;

    public static ConcurrentHashMap<Events, String> getOutputList() {
        ConcurrentHashMap<Events, String> outputListCopy = getSingletonOutputList();
//        clearOutput();
        return outputListCopy;
    }

    private synchronized static ConcurrentHashMap<Events, String> getSingletonOutputList() {
        if (outputList == null) {
            outputList = new ConcurrentHashMap<Events, String>();
        }
        return outputList;
    }

    /**
     * Puts a output from the client into the list
     *
     * @param event Event type
     * @param data Data associated with the specific event
     */
    public static void putOutput(Events event, String data) {
        getSingletonOutputList().putIfAbsent(event, data);
    }

    /**
     * Clears the list
     */
    private static void clearOutput() {
        getSingletonOutputList().clear();
    }

    /**
     * Removes a output from the client from the list
     * 
     * @param event Event type
     * @param data Data associated with the specific event
     */
    public static void removeEvent(Events event, String data) {
        getSingletonOutputList().remove(event, data);
    }

}
