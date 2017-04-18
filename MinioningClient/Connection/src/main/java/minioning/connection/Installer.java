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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import minioning.common.data.LocalData;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;

/**
 *
 * @author Jakob
 */
public class Installer extends ModuleInstall {

    public static DatagramSocket cSocket;
    private static List<String[]> tempData;
 float timeStep = (float)0.1;
    long lastTime = System.nanoTime();
    public static DatagramSocket getDatagramSocket() throws SocketException {
        if (cSocket == null) {
            cSocket = new DatagramSocket();
        }
        return cSocket;
    }

    //returns a copy of tempData and clears the original
    public synchronized static List<String[]> getTempData() {
        List<String[]> tempDataCopy = new ArrayList<>();
        for (String[] data : getActualTempData()) {
            tempDataCopy.add(data);
        }
        return tempDataCopy;
    }

    private synchronized static List<String[]> getActualTempData() {
        if (tempData == null) {
            tempData = Collections.synchronizedList(new ArrayList<String[]>());
        }
        return tempData;
    }

    public synchronized static void putTempData(String[] newData) {
        //check if already there, then add
        List<String[]> tempData = getActualTempData();
        for (int i = 0; i < tempData.size(); i++) {
            if (newData[0].equals(tempData.get(i)[0])) {
                getActualTempData().remove(tempData.get(i));
            }
        }
        getActualTempData().add(newData);
    }

    public static void clearTempData() {
        getActualTempData().clear();
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
            sData = new byte[1500];
            
            while (true) {
//                long currentTime = System.nanoTime();
//                 float elapsedTime = (currentTime - lastTime) / (float)1000000000.0;
//                LocalData.setDt(elapsedTime);
//                try {
//                    TimeUnit.MILLISECONDS.sleep(100);
//                } catch (InterruptedException ex) {
//                    Exceptions.printStackTrace(ex);
//                }

                sPacket = new DatagramPacket(sData, sData.length);

                try {
                    getDatagramSocket().receive(sPacket);
                    processPackage(sPacket);
//                    System.out.println("received something");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

    }

    public void processPackage(DatagramPacket dp) {
        try {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(dp.getData());
            final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            String[] result = (String[]) objectInputStream.readObject();
            objectInputStream.close();
            putTempData(result);
        } catch (Exception e) {
//            System.out.println("processPackage failed: " + e);
//            try {
//                String simpleData = new String(dp.getData());
//                System.out.println("Received: " + simpleData);
//                setUUID(simpleData.trim());
//            } catch (Exception ex) {
//            }
        }
    }
}
