package minioning.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Map;
import minioning.common.data.Events;
import static minioning.common.data.Lists.*;
import static minioning.common.data.LocalData.getPlaying;
import static minioning.common.data.LocalData.getPort;
import static minioning.common.data.LocalData.setPlaying;
import static minioning.connection.Installer.getDatagramSocket;

/**
 *
 * @author Jakob
 */
public class DataTransmitter implements Runnable {

    private byte[] sendData = new byte[1024];
    private Boolean playing;
    private Map<Events, String> output;

//    public static DatagramSocket getDatagramSocket() throws SocketException {
//        if (cEventSocket == null) {
//            cEventSocket = new DatagramSocket();
//        }
//        return cEventSocket;
//    }
    @Override
    public void run() {
        while (true) {
            playing = getPlaying();
            if (!playing) {
                output = getOutputList();
//            clearOutput();
//            System.out.println("for loop & size: " + output.entrySet().size());
                for (Map.Entry<Events, String> entry : output.entrySet()) {
                    Events eventType = entry.getKey();
                    String data = entry.getValue();
                    System.out.println(eventType);
                    switch (eventType) {
                        case CREATEPLAYER:

                            break;
                        case CREATEACCOUNT:
                            try {
                                sendEvent(data);
                            } catch (Exception e) {
                                System.out.println("Create Account transmit failed: " + e);
                            }
                            removeEvent(eventType, data);
                            System.out.println("event removed");
                            break;
                        case LOGIN:
                            try {
                                sendEvent(data);
                            } catch (Exception e) {
                                System.out.println("LOGIN transmit failed: " + e);
                            }
                            removeEvent(eventType, data);
                            System.out.println("event removed");
                            break;
                        case PLAY:
                            try {
                                sendEvent(data);
                                setPlaying(true);
                            } catch (Exception e) {
                            }
                            removeEvent(eventType, data);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private void sendEvent(String data) throws IOException {
        InetAddress IPAddress = InetAddress.getByName("localhost");

        sendData = data.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, getPort());

        if (sendData != null) {

            getDatagramSocket().send(sendPacket);

            System.out.println("Package Sent: " + data);
        }
    }
}
