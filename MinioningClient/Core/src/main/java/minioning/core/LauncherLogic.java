/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.core;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Jakob
 */
public class LauncherLogic {
    
    private static final String token = "CREATEPLAYER;";
    
    public String nameCheck(String input){
     
        String player = null;
        String tempPlayer = input.trim();
        tempPlayer = tempPlayer.replaceAll(" ", "");
        
        if (tempPlayer.contains("ø") || tempPlayer.contains("å")
        || tempPlayer.contains("æ") || tempPlayer.contains(";") ||tempPlayer.length() < 1 || tempPlayer.length() > 10){
            return null;
        }else{
        player = token + tempPlayer;
        }
        return player;
    }
    
    public void CreatePlayer (String playerInfo, InetAddress IPAddress, DatagramSocket clientSocket) throws IOException{
        byte[] sendData = new byte[1024];   
        sendData = playerInfo.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
           
                clientSocket.send(sendPacket);
    }
}
