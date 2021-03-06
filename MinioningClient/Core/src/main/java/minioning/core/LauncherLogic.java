/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import minioning.common.data.Entity;
import minioning.common.data.Events;
import static minioning.common.data.Events.CREATEPLAYER;
import static minioning.common.data.Events.LOGIN;
import static minioning.common.data.Events.PLAY;
import static minioning.common.data.Lists.putOutput;
import minioning.common.data.LocalData;
import static minioning.common.data.LocalData.setClientID;

/**
 *
 * @author Jakob
 */
public final class LauncherLogic{

    public static DatagramSocket cSocket;
//    private static UUID ClientID;
//    private static boolean running = true;

    // implements singleton
    public static DatagramSocket getDatagramSocket() throws SocketException {
        if (cSocket == null) {
            cSocket = new DatagramSocket();
        }
        return cSocket;
    }

    public String nameCheck(String input) {
        String player = null;
        String tempPlayer = input.trim();
        tempPlayer = tempPlayer.replaceAll(" ", "");

        if (tempPlayer.contains("Ã¸") || tempPlayer.contains("Ã¥")
                || tempPlayer.contains("Ã¦") || tempPlayer.contains(";") || tempPlayer.length() < 1 || tempPlayer.length() > 10) {
            return null;
        } else {
            player = tempPlayer;
        }
        return player;
    }

    public final void CreatePlayer(String playerInfo) throws IOException {
        String output = LocalData.getClientID() + ";" + CREATEPLAYER + ";" + playerInfo;
        putOutput(CREATEPLAYER, output);
    }

    public final void accountQuery(Events event, String username, String password, InetAddress IPAddress, DatagramSocket clientSocket) throws IOException {
        String login = ";" + event + ";" + username + ";" + password;
        putOutput(event, login);
        System.out.println("putting event " + event);
    }

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
    
    
    public final void play(UUID id , Events event, InetAddress IPAddress, DatagramSocket clientSocket)throws IOException{
        
        String output = id + ";" + event;
        putOutput(PLAY, output);
    }
  
    public void setUUID(String raw) {

        UUID ID = UUID.fromString(raw);
        System.out.println(ID);
        setClientID(ID);
    }
}
