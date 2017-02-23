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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Jakob
 */
public final class LauncherLogic {
    
    private static final String createPlayerToken = "CREATEPLAYER;";
        private static final String loginToken = "LOGIN;";
        
    public String nameCheck(String input){
     
        String player = null;
        String tempPlayer = input.trim();
        tempPlayer = tempPlayer.replaceAll(" ", "");
        
        if (tempPlayer.contains("ø") || tempPlayer.contains("å")
        || tempPlayer.contains("æ") || tempPlayer.contains(";") ||tempPlayer.length() < 1 || tempPlayer.length() > 10){
            return null;
        }else{
        player = createPlayerToken + tempPlayer;
        }
        return player;
    }
    
    public final void CreatePlayer (String playerInfo, InetAddress IPAddress, DatagramSocket clientSocket) throws IOException{
        byte[] sendData = new byte[1024];   
        sendData = playerInfo.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
           
                clientSocket.send(sendPacket);
    }
    
     public final void attemptLogin(String username, String password, InetAddress IPAddress, DatagramSocket clientSocket) throws IOException{
        byte[] sendData = new byte[1024];  
        
        String login = loginToken  +username +";"+password;
        
        sendData =  login.getBytes();
         System.out.println(login);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
           
                clientSocket.send(sendPacket);
    }
    
        public final void promt(String s1, String s2) {

        // prompt message 
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        Group root2 = new Group();
        Label promptMessage = new Label(s1);
        promptMessage.setMaxSize(promptMessage.getText().length()*6, 40);
        
        
        GridPane promptGP = new GridPane();

        VBox promptVbox = new VBox();
        HBox promptHbox = new HBox();
        Font promptFont = Font.font("Serif", 14);

        promptMessage.setFont(promptFont);

        promptVbox.setPrefWidth(10);
        promptHbox.setPrefHeight(80);
       
                
        promptGP.add(promptHbox, 0, 1);
        promptGP.add(promptVbox, 0, 0);
        promptGP.add(promptMessage, 1, 0);

        root2.getChildren().add(promptGP);

        
        Scene prompt = new Scene(root2, promptMessage.getMaxWidth(), 40);
        dialog.setScene(prompt);
        dialog.setTitle(s2);
        dialog.show();
        dialog.isFocused();
        dialog.setResizable(false);
    }
    
}
