/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.connection;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import org.openide.modules.ModuleInstall;

/**
 *
 * @author Jakob
 */
public class Installer extends ModuleInstall {

    public static DatagramSocket cSocket;
    private static List<String[]> tempData;

    public static DatagramSocket getDatagramSocket() throws SocketException {
        if (cSocket == null) {
            cSocket = new DatagramSocket();
        }
        return cSocket;
    }

    public synchronized static List<String[]> getTempData() {
        if (tempData == null) {
            tempData = new ArrayList<String[]>();
        }
        return tempData;
    }

    public static void putTempData(String[] newData) {
        getTempData().add(newData);
    }

    public static void clearTempData() {
        getTempData().clear();
    }

    @Override
    public void restored() {
        new Thread(new ConnectionThread()).start();
        new Thread(new DataTransmitter()).start();
    }

    public class ConnectionThread implements Runnable {

        @Override
        public void run() {

            byte[] sData = null;
            DatagramPacket sPacket = null;

            while (true) {

                sData = new byte[1024];
                sPacket = new DatagramPacket(sData, sData.length);

                try {
                    getDatagramSocket().receive(sPacket);
                    processPackage(sPacket);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

    }

    public void processPackage(DatagramPacket dp) {
        try {
            ByteArrayInputStream byteArrayInputStreaam = new ByteArrayInputStream(dp.getData());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStreaam);
            String[] result = (String[]) objectInputStream.readObject();
            objectInputStream.close();
            putTempData(result);
            System.out.println("received:");
            for(String resultLine : result){
                System.out.println(resultLine);
            }
        } catch (Exception e) {
            System.out.println("processPackage failed: " + e);
//            try {
//                String simpleData = new String(dp.getData());
//                System.out.println("Received: " + simpleData);
//                setUUID(simpleData.trim());
//            } catch (Exception ex) {
//            }
        }
    }
}
