/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.connection;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Map;
import minioning.common.data.Entity;
import minioning.common.services.IProcessingService;
import java.util.Map;
import minioning.common.data.Entity;
import minioning.common.services.IProcessingService;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Jakob
 */

    @ServiceProvider(service = IProcessingService.class)
public class DataTransmitter  implements IProcessingService{

    @Override
    public void process(Map<String, Entity> world, Entity entity) {
       
        
        
//        public static DatagramSocket cSocket;
//        byte[] sData = null;
//        DatagramPacket sPacket = null;
//
// 
//            try {
//                cSocket = getDatagramSocket();
//                sData = new byte[1024];
//                sPacket = new DatagramPacket(sData, sData.length);
//
//                sData = new byte[1024];
//                sPacket = new DatagramPacket(sData, sData.length);
//
//                System.out.println("virker");
//            } catch (SocketException e) {
//                System.out.println(e);
//            }
//
//            try {
//                cSocket.receive(sPacket);
//                processPackage(sPacket);
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//        }
    }
}
