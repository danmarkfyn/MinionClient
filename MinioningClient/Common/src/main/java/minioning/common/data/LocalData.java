/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.common.data;

import java.util.UUID;

/**
 *
 * @author Jakob
 */
public class LocalData {
    
     private static UUID ClientID = null;

    public static UUID getClientID() {
        return ClientID;
    }

    public static void setClientID(UUID ClientID) {
        LocalData.ClientID = ClientID;
    }
    
    
}
