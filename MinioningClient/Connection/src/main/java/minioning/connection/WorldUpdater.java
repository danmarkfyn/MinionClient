/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.connection;

import java.net.DatagramPacket;
import java.util.Map;
import minioning.common.data.Entity;
import minioning.common.services.IWorldUpdate;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Jakob
 */


@ServiceProvider(service = IWorldUpdate.class)
public class WorldUpdater implements IWorldUpdate{
 byte[] sData = null;
        DatagramPacket sPacket = null;

    @Override
    public void update(Map<String, Entity> world) {
       
//            String[] s = modifiedSentence.split(";");
            
//            System.out.println(s[0]+"og"+s[1]+"og"+s[2]);
            
    }
    
}
