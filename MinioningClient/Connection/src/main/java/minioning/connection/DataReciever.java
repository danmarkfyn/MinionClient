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
import org.openide.util.Exceptions;

/**
 *
 * @author Jakob
 */
public class DataReciever  {

//    public static DatagramSocket cSocket;
//    byte[] getData = new byte[1024];
//
//    DatagramPacket dp;

//    public static DatagramSocket getDatagramSocket() throws SocketException {
//        if (cSocket == null) {
//            try {
//                cSocket = new DatagramSocket();
//            } catch (Exception e) {
//            }
//
//        }
//        return cSocket;
//    }
//
//    @Override
//    public void run() {
//        try {
//            DatagramSocket sSocket = getDatagramSocket();
//            
//            while(true){
//            DatagramPacket sPacket = new DatagramPacket(getData, getData.length);
//            
//            sSocket.receive(sPacket);
//            String data = new String(sPacket.getData());
//            
//            
//            
//            }
////        while (!Thread.currentThread().isInterrupted()) {
////
////
////            try {
////                cSocket = getDatagramSocket();
////            } catch (SocketException ex) {
////                Exceptions.printStackTrace(ex);
////            }
////                sData = new byte[1024];
////                sPacket = new DatagramPacket(sData, sData.length);
////
////            try {
////                cSocket.receive(sPacket);
//
//        } catch (Exception e) {
//
//        }
//
////            
////            String[] s = modifiedSentence.split(";");
////
////    System.out.println (s
////
////[0]+"og"+s[1]+"og"+s[2]);
//    }
}
