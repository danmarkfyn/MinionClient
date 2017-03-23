/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import minioning.common.data.Entity;
import minioning.common.services.IWorldUpdate;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Jakob
 */
@ServiceProvider(service = IWorldUpdate.class)
public class WorldUpdater implements IWorldUpdate {

    byte[] sData = new byte[1024];
    public static DatagramSocket cSocket;
    DatagramPacket sPacket;
    Thread t;
    int init = 0;
    private Map<String, Entity> tempWorld = new ConcurrentHashMap<String, Entity>();

    public static DatagramSocket getDatagramSocket() throws SocketException {

        if (cSocket == null) {
            cSocket = new DatagramSocket();
        }
        return cSocket;
    }

    @Override
    public void update(Map<String, Entity> world) {
        world = tempWorld;
        if (this.init == 0) {
            System.out.println("in if running");
//            world.put("player", null);
            new Thread(new PlayThread()).start();

            this.init = 1;
        }
    }

    public class PlayThread implements Runnable {

        @Override
        public void run() {
            System.out.println("1");
            try {
                DatagramSocket clientSocket = getDatagramSocket();
                while (true) {
                    System.out.println("2");
                    sPacket = new DatagramPacket(sData, sData.length);
                    clientSocket.receive(sPacket);
                    tempWorld = new ConcurrentHashMap<String, Entity>();

                    String modifiedSentence = new String(sPacket.getData());

                    String[] s = modifiedSentence.split(";");

                    System.out.println(s[0] + "og" + s[1] + "og" + s[2] + " Entity");

                    UUID id = UUID.fromString(s[0]);

                    int x = Integer.parseInt(s[1]);

                    int y = Integer.parseInt(s[2]);

                    Entity e = new Entity(id, "player", 500, 500);

//                            tempWorld.put("player", e);
                }
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

}
