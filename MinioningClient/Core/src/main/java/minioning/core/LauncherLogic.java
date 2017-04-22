/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.core;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.UUID;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import minioning.common.data.Events;
import static minioning.common.data.Events.CREATEPLAYER;
import static minioning.common.data.Events.PLAY;
import static minioning.common.data.Lists.putOutput;
import minioning.common.data.LocalData;
import static minioning.common.data.LocalData.setClientID;

/**
 * 
 * This class handles the logic of the launcher. It allows to put login and other server queries
 * into the event list for transmission to the server for handeling.
 */

/**
 *
 * @author Jakob
 * 
 * This class handles the logic of the launcher. It allows to put login and other server queries
 * into the event list for transmission to the server for handeling.
 */
public final class LauncherLogic{





    /**
     * This method takes a String as parameter and checks if it live up to the
     * format specified
     * 
     * @param input A userinput (name) 
     * @return A string that fulfills the format, or null if no such string was made
     */
    
    public String nameCheck(String input) {
        String player = null;
        String tempPlayer = input.trim();
        tempPlayer = tempPlayer.replaceAll(" ", "");

        if (tempPlayer.contains("Æ¸") || tempPlayer.contains("Ø")
                || tempPlayer.contains("Å¦") || tempPlayer.contains(";") || tempPlayer.length() < 1 || tempPlayer.length() > 10) {
            return null;
        } else {
            player = tempPlayer;
        }
        return player;
    }

    /**
     * 
     * This method puts a CreatePlayer query in the list of events for server transmission
     * 
     * @param avatarName User input for a avatar name (String)
     * @throws IOException 
     */
    public final void CreatePlayer(String avatarName){
        String output = LocalData.getClientID() + ";" + CREATEPLAYER + ";" + avatarName;
        putOutput(CREATEPLAYER, output);
    }

    
    /**
     * 
     * This method handles both queries for creating an account and logging in
     * 
     * @param event An event enum specifying what kind of query (CREATEAACOUNR or LOGIN
     * @param username Login username or wished username for an account not yet created
     * @param password Login password or wished password for an account not yet created
     * @param IPAddress
     * @param clientSocket
     * @throws IOException 
     */
    
    public final void accountQuery(Events event, String username, String password){
        String login = ";" + event + ";" + username + ";" + password;
        putOutput(event, login);
        System.out.println("putting event " + event);
    }

    /**
     * This method creates a new dialog box
     * 
     * @param s1 Is used for setting the message of the dialog box
     * @param s2 Is used for setting the title of the dialog box
     */
    
    public final void promt(String s1, String s2) {
        
        // prompt message 
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        Group root2 = new Group();
        Label promptMessage = new Label(s1);
        promptMessage.setMaxSize(promptMessage.getText().length() * 8, 40);

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
    
    
    /**
     * 
     * @param id
     * @param event
     * @param IPAddress
     * @param clientSocket
     * @throws IOException 
     */
    
    public final void play(UUID id , Events event){
        
        String output = id + ";" + event;
        putOutput(PLAY, output);
    }
  
    public void setUUID(String raw) {

        UUID ID = UUID.fromString(raw);
        System.out.println(ID);
        setClientID(ID);
    }
}
