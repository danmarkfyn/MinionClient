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
import static minioning.common.data.LocalData.setClientID;

/**
 *
 * @author Jakob
 */
public final class LauncherLogic implements Runnable {

    public static DatagramSocket cSocket;
    private static UUID ClientID;

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

        if (tempPlayer.contains("ø") || tempPlayer.contains("å")
                || tempPlayer.contains("æ") || tempPlayer.contains(";") || tempPlayer.length() < 1 || tempPlayer.length() > 10) {
            return null;
        } else {
            player = tempPlayer;
        }
        return player;
    }

    public final void CreatePlayer(String playerInfo, InetAddress IPAddress, DatagramSocket clientSocket) throws IOException {
        byte[] sendData = new byte[1024];

        String output = CREATEPLAYER + ";" + playerInfo;

        System.out.println(output);

        sendData = output.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);

        clientSocket.send(sendPacket);
    }

    public final void accountQuery(Events event, String username, String password, InetAddress IPAddress, DatagramSocket clientSocket) throws IOException {
        byte[] sendData = new byte[1024];

        String login = ";" + event + ";" + username + ";" + password;

        sendData = login.getBytes();
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

    @Override
    public void run() {

        byte[] sData = null;
        DatagramPacket sPacket = null;

        while (!Thread.currentThread().isInterrupted()) {

            try {
                cSocket = getDatagramSocket();
                sData = new byte[1024];
                sPacket = new DatagramPacket(sData, sData.length);

                sData = new byte[1024];
                sPacket = new DatagramPacket(sData, sData.length);

                System.out.println("virker");
            } catch (SocketException e) {
                System.out.println(e);
            }

            try {
                cSocket.receive(sPacket);
                processPackage(sPacket);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void processPackage(DatagramPacket dp) {
        byte[] getData = new byte[1024];
String modifiedSentence = new String(dp.getData());
        try {
            
            System.out.println("Received:" + modifiedSentence);
            setUUID(modifiedSentence.trim());
        } catch (Exception e) {
        }
//        try{
//            
//            String[] s = modifiedSentence.split(";");
//            
//            System.out.println(s[0]+"og"+s[1]+"og"+s[2]);
//            
//            
//        }catch(Exception e){
//            
//        }
//        try {
//            System.out.println("1");
//            ByteArrayInputStream in = new ByteArrayInputStream(dp.getData());
//            ObjectInputStream ois = new ObjectInputStream(in);
//            System.out.println("2");
//            ConcurrentHashMap<UUID, Entity> world = (ConcurrentHashMap<UUID, Entity>) (Map<UUID, Entity>) ois.readObject();
//ois.close();
//            System.out.println("3");
//        } catch (Exception e) {
//            System.out.println(e);
//        }

    }

    public void setUUID(String raw) {

        UUID ID = UUID.fromString(raw);
        System.out.println(ID);
        setClientID(ID);

    }

}
