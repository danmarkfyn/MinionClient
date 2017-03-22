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

    public static DatagramSocket getDatagramSocket() throws SocketException {

        if (cSocket == null) {
            cSocket = new DatagramSocket();
        }
        return cSocket;
    }

    @Override
    public void update(Map<String, Entity> world) {
        System.out.println("jojojoj");
        sPacket = new DatagramPacket(sData, sData.length);
        try {
            getDatagramSocket().receive(sPacket);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        String modifiedSentence = new String(sPacket.getData());

        String[] s = modifiedSentence.split(";");

        System.out.println(s[0] + "og" + s[1] + "og" + s[2] + "jajaj");

    }
}
