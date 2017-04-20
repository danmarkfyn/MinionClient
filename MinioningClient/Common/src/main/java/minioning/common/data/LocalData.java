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
    private static Boolean playing = false;
    private static final int port = 80;
    private static String location;
    private static String user;
<<<<<<< Updated upstream
   private static float dt;
=======
    private static float dt;
    private static float updateTime = 0.1f;
>>>>>>> Stashed changes

    public static float getDt() {
        return dt;
    }

    public static void setDt(float dt) {
        LocalData.dt = dt;
    }
    
    
    public static String getUser() {
        return user;
    }

    public static float getUpdateTime() {
        return updateTime;
    }

    public static float getDt() {
        return dt;
    }

    public static void setDt(float dt) {
        LocalData.dt = dt;
    }

    public static void setUser(String user) {
        LocalData.user = user;
    }

    public static void setLocation(String location) {
        LocalData.location = location;
    }

    public static String getLocation() {
        return location;
    }

    

    public static int getPort() {
        return port;
    }

    public static Boolean getPlaying() {
        return playing;
    }

    public static void setPlaying(Boolean playing) {
        LocalData.playing = playing;
    }

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
