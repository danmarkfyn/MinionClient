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
     private static int height = 576;
     private static int width = 1024;

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        LocalData.height = height;
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        LocalData.width = width;
    }
     
    public static UUID getClientID() {
        return ClientID;
    }

    public static void setClientID(UUID ClientID) {
        LocalData.ClientID = ClientID;
    }
    
    
}
