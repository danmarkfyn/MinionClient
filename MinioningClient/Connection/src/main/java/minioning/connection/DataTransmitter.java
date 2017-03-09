/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Map;
import minioning.common.data.Entity;
import static minioning.common.data.EventData.getData;
import static minioning.common.data.EventData.getEventData;
import static minioning.common.data.EventData.removeData;
import static minioning.common.data.Events.MOVEMENT;
import static minioning.common.data.LocalData.getClientID;
import minioning.common.services.IProcessingService;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Jakob
 */
@ServiceProvider(service = IProcessingService.class)
public class DataTransmitter implements IProcessingService {

    private static DatagramSocket cEventSocket;
    private byte[] sendData = new byte[1024];

    public static DatagramSocket getDatagramSocket() throws SocketException {
        if (cEventSocket == null) {
            cEventSocket = new DatagramSocket();
        }
        return cEventSocket;
    }

    @Override
    public void process(Map<String, Entity> world, Entity entity) {

        if (getEventData().size() > 0) {
            for (int i = 0; i < getEventData().size(); i++) {

                
                
                
                String data = getData(MOVEMENT);
                System.out.println(getEventData().size());
                System.out.println("Current event: " + data);
                
                String allData = getClientID()+";"+MOVEMENT+";" + data;
                
                try {
                    sendEvent(allData, i);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
                removeData(MOVEMENT);
            }
        }
    }

    private void sendEvent(String data, int i) throws IOException {
//        InetAddress IPAddress = InetAddress.getByName("localhost");

        InetAddress IPAddress = InetAddress.getByName("192.168.87.13");
        cEventSocket = getDatagramSocket();

        sendData = data.getBytes();
        System.out.println(sendData);
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);

        if (sendData != null) {

            DataTransmitter.cEventSocket.send(sendPacket);

            System.out.println("Package Sent");
        }

    }
}
